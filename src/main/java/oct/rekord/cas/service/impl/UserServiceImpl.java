package oct.rekord.cas.service.impl;


import lombok.extern.slf4j.Slf4j;
import oct.rekord.cas.bean.AuthorityRecord;
import oct.rekord.cas.bean.User;
import oct.rekord.cas.common.Message;
import oct.rekord.cas.common.ReturnData;
import oct.rekord.cas.dao.UserInfoDAO;
import oct.rekord.cas.service.UserService;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserInfoDAO userInfoDAO;

    @Override
    public ReturnData register(HttpServletRequest request, String username, String password, String phone) {
        try {
            userInfoDAO.insertUser(new User(username, password, phone));
            return ReturnData.success("注册成功");
        } catch (Exception e) {
            return ReturnData.fail(502, "该用户名已被注册使用");
        }

    }

    @Override
    public ReturnData login(HttpServletRequest request, String username, String password, String agent) {
        Map<String, Object> data = new LinkedHashMap<>();

        try {
            User user = userInfoDAO.selectUserInfoByUsernameAndPassword(username, password);
            data.put("账号ID", user.getUserId());
//        data.put("用户名", user.getUsername());
            data.put("级别", user.getLevel());
            data.put("邮箱", user.getEmail());
            data.put("头像", user.getHeadImgPath());
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
        String newLevel = ("1".equals(authorityRecord.getAction()) ? "2" : "3");
        try {
            // 更新用户等级
            userInfoDAO.updateUserLevel(authorityRecord.getToUserId(), newLevel);
            if ("1".equals(authorityRecord.getAction())) {
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
}
