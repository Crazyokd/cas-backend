package oct.rekord.cas.service;

import oct.rekord.cas.bean.AuthorityRecord;
import oct.rekord.cas.common.ReturnData;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    ReturnData register(HttpServletRequest request, String username, String password, String phone);

    ReturnData login(HttpServletRequest request, String username, String password, String agent);

    ReturnData getCode(String phone, Integer bits);

    ReturnData authorize(AuthorityRecord authorityRecord);

    ReturnData setHeadImg(HttpServletRequest request, String userId, MultipartFile file);

    ReturnData getHeadImg(Integer userId);
}
