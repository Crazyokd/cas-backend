package oct.rekord.cas.service.impl;

import lombok.extern.slf4j.Slf4j;
import oct.rekord.cas.bean.Application;
import oct.rekord.cas.bean.AwardCertificate;
import oct.rekord.cas.common.ApplicationCategoryEnum;
import oct.rekord.cas.common.Image;
import oct.rekord.cas.common.ReturnData;
import oct.rekord.cas.dao.AwardCertificateDAO;
import oct.rekord.cas.exception.BaseException;
import oct.rekord.cas.service.ApplicationService;
import oct.rekord.cas.service.AwardCertificateService;
import oct.rekord.cas.service.SemesterService;
import oct.rekord.cas.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("acService")
public class AwardCertificateServiceImpl implements AwardCertificateService {

    private String acImgDir;
    private String acImgDefaultDir;


    @Autowired
    public AwardCertificateServiceImpl(@Value("${my.image.ac-image-dir}") String acImgDir, @Value("${my.image.ac-image-default-dir}") String acImgDefaultDir) {
        this.acImgDir = acImgDir;
        this.acImgDefaultDir = acImgDefaultDir;

        // 创建用户设置的默认文件夹
        File acImgDefaultDirectory = new File(acImgDefaultDir);
        if (!acImgDefaultDirectory.exists()) {
            acImgDefaultDirectory.mkdirs();
        }
        // 创建用户设置的文件夹
        File acImgDirectory = new File(acImgDir);
        if (!acImgDirectory.exists()) {
            acImgDirectory.mkdirs();
        }
    }

    @Autowired
    AwardCertificateDAO awardCertificateDAO;
    @Autowired
    SemesterService semesterService;
    @Autowired
    ApplicationService applicationService;

    public String getImgByPath(String imgPath) throws Exception {
        byte[] bytes;
        File file = new File(imgPath);

        if (file.exists()) {
            bytes = FileUtil.fileToBytes(file);
        } else {
            throw new BaseException(502, "默认图片不存在");
        }

        return Base64.getEncoder().encodeToString(bytes);
    }

    public ReturnData getAllAC(HttpServletRequest request) {
        List<AwardCertificate> awardCertificateList = awardCertificateDAO.selectAwardCertificateByUserID(Integer.valueOf(request.getAttribute("userId").toString()));

        // 将所有证书路径转化为证书
        for (AwardCertificate ac : awardCertificateList) {
            String img = null;
            try {
                img = getImgByPath(ac.getImgPath());
            } catch (Exception e) {
                img = null;
            } finally {
                ac.setImgPath(img);
            }
        }
        return ReturnData.success(awardCertificateList);
    }

    @Override
    public ReturnData uploadAwardCertificateByUserId(HttpServletRequest request, String name, String isValid, String category, String explanation, String comment, String semesterName, MultipartFile file) {
        Integer userId = Integer.valueOf(request.getAttribute("userId").toString());
        String fileName = FileUtil.fileTransfer(file, this.acImgDir, Image.IMG_SUFFIX, 0, Image.MAX_IMG_SIZE);

        // 通过 semesterName 查询 semesterId
        Integer semesterId = semesterService.getSemesterIdBySemesterName(semesterName);
        if (semesterId == null) {
            return ReturnData.fail(502, "学年有误");
        }

        // 若图片名为 default.jpg ，则使用默认路径
        String imgPath = (fileName.equals("default.jpg") ? this.acImgDefaultDir : this.acImgDir) + fileName;
        AwardCertificate awardCertificate = new AwardCertificate(userId, name, 0, category, explanation, comment, imgPath, semesterId);
        try {
            awardCertificateDAO.uploadAwardCertificate(awardCertificate);
            if (isValid.equals("1")) {
                Integer linkId = awardCertificate.getAcId();
                applicationService.insertApplication(new Application(userId, ApplicationCategoryEnum.CERTIFICATE_APPLICATION.getCategory(), linkId, comment));
            }
        } catch (Exception e) {
            return ReturnData.fail(502, "证书名重复");
        }
        return ReturnData.success("证书上传成功");
    }

    @Override
    public ReturnData getAC(Integer acId) {
        AwardCertificate awardCertificate;
        try {
            awardCertificate = awardCertificateDAO.selectAwardCertificateByACId(acId);
            String img = getImgByPath(awardCertificate.getImgPath());
            awardCertificate.setImgPath(img);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnData.fail(502, "获取失败");
        }
        return ReturnData.success(awardCertificate);
    }
}
