package oct.rekord.cas.service;

import oct.rekord.cas.bean.Application;
import oct.rekord.cas.common.ReturnData;

import javax.servlet.http.HttpServletRequest;

public interface ApplicationService {
    void insertApplication(Application application) throws Exception;

    ReturnData addApplication(HttpServletRequest request, Application application);

    ReturnData getAllApplication(HttpServletRequest request);

    ReturnData getNewApplication(HttpServletRequest request);

    ReturnData getMyApplication(HttpServletRequest request);

    ReturnData processApplication(Application application);
}
