package oct.rekord.cas.service.impl;

import lombok.extern.slf4j.Slf4j;
import oct.rekord.cas.bean.AwardCertificate;
import oct.rekord.cas.common.ReturnData;
import oct.rekord.cas.dao.AwardCertificateDAO;
import oct.rekord.cas.service.AwardCertificateService;
import oct.rekord.cas.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("acService")
public class AwardCertificateServiceImpl implements AwardCertificateService {

    private String acImgDir;

    @Autowired
    public AwardCertificateServiceImpl(@Value("${my.image.ac-image-dir}") String acImgDir) {
        this.acImgDir = acImgDir;

        //创建用户设置头像的文件夹
        File headImgDirectory = new File(acImgDir);
        if (!headImgDirectory.exists()) {
            headImgDirectory.mkdirs();
        }
    }

    @Autowired
    AwardCertificateDAO awardCertificateDAO;

    public ReturnData getAllAC(Integer userId) {
        List<AwardCertificate> awardCertificateList = awardCertificateDAO.selectAwardCertificateByUserID(userId);
        return ReturnData.success(awardCertificateList);
    }

    @Override
    public ReturnData uploadAwardCertificateByUserId(Integer userId, String name, String isValid, String category, String explanation, String comment, MultipartFile file) {
        String fileName = FileUtil.fileTransfer(file, this.acImgDir, IMG_SUFFIX, 0, MAX_IMG_SIZE);

        log.error("file path:" + this.acImgDir+fileName);
        AwardCertificate awardCertificate = new AwardCertificate(userId, name, isValid, category, explanation, comment, this.acImgDir+fileName);
        try {
            awardCertificateDAO.updateAwardCertificate(awardCertificate);
        } catch (Exception e) {
            return ReturnData.fail(502, "证书名重复");
        }
        return ReturnData.success("证书上传成功");
    }


}
