package oct.rekord.cas.service;

import oct.rekord.cas.bean.Application;
import oct.rekord.cas.common.ReturnData;

public interface ApplicationService {
    ReturnData addApplication(Application application);

    ReturnData getAllApplication(Integer userId);

    ReturnData processApplication(Application application);
}
