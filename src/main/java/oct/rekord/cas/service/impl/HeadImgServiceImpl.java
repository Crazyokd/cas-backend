package oct.rekord.cas.service.impl;


import oct.rekord.cas.common.ReturnData;
import oct.rekord.cas.dao.UserInfoDAO;
import oct.rekord.cas.service.HeadImgService;
import oct.rekord.cas.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Base64;

@Service("headImgService")
public class HeadImgServiceImpl implements HeadImgService {


    @Autowired
    UserInfoDAO userInfoDAO;

    private String headImageDefaultDir;

    private String headImageDir;

    @Autowired
    public HeadImgServiceImpl(@Value("${my.image.head-image-dir}") String headImageDir, @Value("${my.image.head-image-default-dir}") String headImageDefaultDir) {
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
    public ReturnData setHeadImg(HttpServletRequest request, String userId, MultipartFile file) {
        Integer userIdInteger = Integer.valueOf(userId);
        String fileName = FileUtil.fileTransfer(file, this.headImageDir, IMG_SUFFIX, 0, MAX_IMG_SIZE);
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
