package oct.rekord.cas.service;

import oct.rekord.cas.common.ReturnData;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public interface AwardCertificateService {
    /**
     * 图片后缀
     */
    List<String> IMG_SUFFIX = Arrays.asList(".jpg","jpeg",".png",".gif");

    /**
     * 单个图片的最大大小
     */
    long MAX_IMG_SIZE = 1024 * 1024 * 8;

    ReturnData getAllAC(Integer userId);

    ReturnData uploadAwardCertificateByUserId(Integer userId, String name, String isValid, String category, String explanation, String comment, MultipartFile file);
}
