package oct.rekord.cas.service;

import oct.rekord.cas.common.ReturnData;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public interface AwardCertificateService {

    ReturnData getAllAC(HttpServletRequest request);

    ReturnData uploadAwardCertificateByUserId(HttpServletRequest request, String name, String isValid, String category, String explanation, String comment, String semesterName, MultipartFile file);

    ReturnData getAC(Integer acId);
}
