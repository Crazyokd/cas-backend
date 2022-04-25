package oct.rekord.cas.service.impl;


import oct.rekord.cas.common.ReturnData;
import oct.rekord.cas.dao.HeadImgDAO;
import oct.rekord.cas.dao.UserInfoDAO;
import oct.rekord.cas.service.HeadImgService;
import oct.rekord.cas.util.FileUtil;
import org.jsoup.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
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
    public ReturnData setHeadImg(HttpServletRequest request, String userid, MultipartFile file) {
        Integer userId = Integer.valueOf(userid);
        String fileName = FileUtil.fileTransfer(file, this.headImageDir, IMG_SUFFIX, 0, MAX_IMG_SIZE);
        String imgPath = userInfoDAO.selectHeadImgPathByUserId(userId);
        userInfoDAO.updateHeadImgPath(userId, this.headImageDir + fileName);

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
    public ReturnData getHeadImg(HttpServletRequest request, String userid) throws Exception {
        Integer userId = Integer.valueOf(userid);
        String imgPath = userInfoDAO.selectHeadImgPathByUserId(userId);
        byte[] bytes;
        File file = new File(imgPath);
        if (file.exists()) {
            //本地头像存在
            bytes = FileUtil.fileToBytes(file);
        } else {
            return ReturnData.fail(502, "图片不存在");
        }

        return ReturnData.success(Base64.getEncoder().encodeToString(bytes));
    }

}
