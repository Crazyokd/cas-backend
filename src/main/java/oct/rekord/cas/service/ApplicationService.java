package oct.rekord.cas.service;

import oct.rekord.cas.bean.Application;
import oct.rekord.cas.common.ReturnData;

public interface ApplicationService {
    void insertApplication(Application application) throws Exception;

    ReturnData addApplication(Application application);

    ReturnData getAllApplication(Integer userId);

    ReturnData getNewApplication(Integer userId);

    ReturnData getMyApplication(Integer userId);

    ReturnData processApplication(Application application);
}
