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

    private String defaultHeadImageDirect;

    private String headImgDirect;

    @Autowired
    public HeadImgServiceImpl(@Value("${my.image.head-image-dir}") String headImgDir, @Value("${my.image.default-head-dir}") String defaultHeadImageDir) {
        this.headImgDirect = headImgDir;
        this.defaultHeadImageDirect = defaultHeadImageDir;

        //创建默认头像的文件夹
        File defaultHeadImageDirectory = new File(defaultHeadImageDirect);
        if (!defaultHeadImageDirectory.exists()) {
            defaultHeadImageDirectory.mkdirs();
        }
        //创建用户设置头像的文件夹
        File headImgDirectory = new File(headImgDirect);
        if (!headImgDirectory.exists()) {
            headImgDirectory.mkdirs();
        }

    }


    @Override
    public ReturnData setHeadImg(HttpServletRequest request, String userid, MultipartFile file) {
        Integer userId = Integer.valueOf(userid);
//        Integer userId = Integer.valueOf(request.getAttribute("userId").toString());
        String fileName = FileUtil.fileTransfer(file, this.headImgDirect, IMG_SUFFIX, 0, MAX_IMG_SIZE);
        String imgPath = userInfoDAO.selectHeadImgPathByUserId(userId);
        userInfoDAO.updateHeadImgPath(userId, this.headImgDirect + fileName);
        if(imgPath != null){
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
//        Integer userId = Integer.valueOf(request.getAttribute("userId").toString());
        String imgPath = userInfoDAO.selectHeadImgPathByUserId(userId);
        byte[] bytes;
        File file = null;

        if (imgPath != null) {
            //如果设置过头像
            file = new File(imgPath);
            if (file.exists()) {
                //本地头像存在
                bytes = FileUtil.fileToBytes(file);
            } else {
                File defaultFile = new File(this.defaultHeadImageDirect + "default.jpg");
                if (defaultFile.exists()) {
                    //从本地获取默认头像
                    bytes = FileUtil.fileToBytes(defaultFile);
                } else {
                    return ReturnData.fail(502, "默认图片不存在");
                }
            }
        } else {
            // 如果没设置过头像则使用默认头像
            file = new File(defaultHeadImageDirect + "default.jpg");
            if (file.exists()) {
                //从本地获取默认头像
                bytes = FileUtil.fileToBytes(file);
            } else {
                return ReturnData.fail(502, "默认图片不存在");
            }
        }
        return ReturnData.success(Base64.getEncoder().encodeToString(bytes));
    }

}
