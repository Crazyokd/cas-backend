package oct.rekord.cas.service;

import oct.rekord.cas.common.ReturnData;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

public interface HeadImgService {

    ReturnData setHeadImg(HttpServletRequest request, String userId, MultipartFile file);

    ReturnData getHeadImg(Integer userId);

}
