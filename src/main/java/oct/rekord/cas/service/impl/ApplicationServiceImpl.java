package oct.rekord.cas.service.impl;

import oct.rekord.cas.bean.Application;
import oct.rekord.cas.common.ReturnData;
import oct.rekord.cas.dao.ApplicationDAO;
import oct.rekord.cas.dao.UserInfoDAO;
import oct.rekord.cas.service.ApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    private static final Logger log = LoggerFactory.getLogger(ApplicationServiceImpl.class);
    @Autowired
    private ApplicationDAO applicationDAO;
    @Autowired
    private UserInfoDAO userInfoDAO;

    @Override
    public ReturnData addApplication(Application application) {
        try {
            int level = Integer.parseInt(userInfoDAO.selectLevelByUserId(application.getApplicationFromId())) - 1;
            List<Integer> manager = null;
            if (level == 1) {
                manager = applicationDAO.selectManager1();
            } else if (level == 2) {
                manager = applicationDAO.selectManager2();
            }
            assert manager != null;
            application.setApplicationToId(manager.get(application.getApplicationFromId() % manager.size()));
            applicationDAO.insertApplication(application);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnData.fail(502, "申请失败");
        }
        return ReturnData.success("申请成功");
    }

    @Override
    public ReturnData getAllApplication(Integer userId) {
        List<Application> applications;
        try {
            applications = applicationDAO.selectApplicationByToId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnData.fail(502, "获取失败");
        }
        return ReturnData.success(applications);
    }

    @Override
    public ReturnData processApplication(Application application) {
        try {
            applicationDAO.updateApplication(application);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnData.fail(502, "处理失败");
        }
        return ReturnData.success("处理成功");
    }
}
