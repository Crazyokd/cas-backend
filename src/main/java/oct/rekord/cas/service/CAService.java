package oct.rekord.cas.service;

import oct.rekord.cas.common.ReturnData;

import javax.servlet.http.HttpServletRequest;

public interface CAService {
    ReturnData getCAResult(String userId, String semesterName);
}
