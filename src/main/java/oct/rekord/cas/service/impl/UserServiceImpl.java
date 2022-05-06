package oct.rekord.cas.service.impl;


import oct.rekord.cas.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import oct.rekord.cas.bean.AuthorityRecord;
import oct.rekord.cas.bean.User;
import oct.rekord.cas.common.Image;
import oct.rekord.cas.common.Message;
import oct.rekord.cas.common.ReturnData;
import oct.rekord.cas.common.Token;
import oct.rekord.cas.dao.UserInfoDAO;
import oct.rekord.cas.service.UserService;;
import oct.rekord.cas.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserInfoDAO userInfoDAO;

    @Autowired
    RedisUtil redisUtil;


    private String headImageDefaultDir;

    private String headImageDir;

    @Autowired
    public UserServiceImpl(@Value("${my.image.head-image-dir}") String headImageDir, @Value("${my.image.head-image-default-dir}") String headImageDefaultDir) {
        this.headImageDir = headImageDir;
        this.headImageDefaultDir = headImageDefaultDir;

        //创建默认头像的文件夹
        File headImageDefaultDirectory = new File(headImageDefaultDir);
        if (!headImageDefaultDirectory.exists()) {
            headImageDefaultDirectory.mkdirs();
        }

        //创建用户设置头像的文件夹
        File headImageDirectory = new File(headImageDir);
        if (!headImageDirectory.exists()) {
            headImageDirectory.mkdirs();
        }

    }

    @Override
    public ReturnData register(HttpServletRequest request, User user) {
        try {
            userInfoDAO.insertUser(user);
            return ReturnData.success("注册成功");
        } catch (Exception e) {
            return ReturnData.fail(502, "该用户名已被注册使用");
        }

    }

    @Override
    public ReturnData login(String username, String password, String version) {
        log.error(version);
        Map<String, Object> data = new LinkedHashMap<>();

        try {
            User user = userInfoDAO.selectUserInfoByUsernameAndPassword(username, password);
            String token = Token.createToken();
            Integer userId = user.getUserId();
            redisUtil.set(Token.USER_TOKEN_PREFIX + token, userId.toString(), Token.TOKEN_EXPIRE);
            redisUtil.set(Token.USER_ID_PREFIX + userId, token, Token.TOKEN_EXPIRE);

            data.put("token", token);
            data.put("用户名", user.getUsername());
            data.put("级别", user.getLevel());
            data.put("邮箱", user.getEmail());
//            data.put("头像", user.getHeadImgPath());
            data.put("手机号", user.getPhone());
            data.put("学号", user.getSn());
            data.put("学号密码", user.getSnPassword());

            return ReturnData.success(data);
        } catch (Exception e) {
            return ReturnData.fail(502, "登录失败");
        }

    }

    @Override
    public ReturnData getCode(String phone, Integer bits) {
        try {
            return ReturnData.success(Message.sendMessage(phone, bits));
        } catch (Exception e) {
            return ReturnData.fail(502, "发送短信验证码失败");
        }
    }

    @Override
    public ReturnData authorize(AuthorityRecord authorityRecord) {
        Integer newLevel = authorityRecord.getAction() == 1 ? 2 : 3;
        try {
            // 更新用户等级
            userInfoDAO.updateUserLevel(authorityRecord.getToUserId(), newLevel);
            if (1 == authorityRecord.getAction()) {
                int managerId = userInfoDAO.selectManagerIdByUserId(authorityRecord.getFromUserId());
                userInfoDAO.insertManager2(authorityRecord.getToUserId(), managerId);
            } else {
                userInfoDAO.removeByUserId(authorityRecord.getToUserId());
            }
            // 生成一条授权记录
            userInfoDAO.insertAuthorityRecord(authorityRecord);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnData.fail(502, "权限操作失败");
        }
        return ReturnData.success("权限操作成功");
    }

    @Override
    public ReturnData setHeadImg(HttpServletRequest request, String userId, MultipartFile file) {
        Integer userIdInteger = Integer.valueOf(userId);
        String fileName = FileUtil.fileTransfer(file, this.headImageDir, Image.IMG_SUFFIX, 0, Image.MAX_IMG_SIZE);
        String imgPath = userInfoDAO.selectHeadImgPathByUserId(userIdInteger);
        userInfoDAO.updateHeadImgPath(userIdInteger, this.headImageDir + fileName);

        // 如果是默认图片则不进行删除
        if(imgPath != null && !imgPath.contains(this.headImageDefaultDir)){
            File fileFromDB = new File(imgPath);
            if (fileFromDB.exists()) {
                fileFromDB.delete();
            }
        }
        return ReturnData.success();
    }


    @Override
    public ReturnData getHeadImg(Integer userId) {
        String fileName = userInfoDAO.selectHeadImgPathByUserId(userId);
        try {
            return ReturnData.success(Base64.getEncoder().encodeToString(FileUtil.fileNameToBytes(fileName)));
        } catch (Exception e) {
            if ("文件不存在".equals(e.getMessage())) {
                File defaultHeadImg = new File(this.headImageDefaultDir + "default.jpg");
                if (!defaultHeadImg.exists()) {
                    return ReturnData.fail(502, "默认文件不存在，请上传默认图片");
                } else {
                    try {
                        return ReturnData.success(Base64.getEncoder().encodeToString(FileUtil.fileToBytes(defaultHeadImg)));
                    } catch (Exception ex) {
                        return ReturnData.fail(502, ex.getMessage());
                    }
                }
            }
            return ReturnData.fail(502, e.getMessage());
        }
    }

}
