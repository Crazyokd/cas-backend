package oct.rekord.cas.service;

import oct.rekord.cas.common.ReturnData;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    ReturnData register(HttpServletRequest request, String username, String password, String phone);

    ReturnData login(HttpServletRequest request, String username, String password, String agent);

    ReturnData getCode(String phone, Integer bits);

    ReturnData authorize(Integer childId, Integer parentId);
}
