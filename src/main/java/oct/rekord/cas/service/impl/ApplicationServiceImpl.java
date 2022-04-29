package oct.rekord.cas.service.impl;

import oct.rekord.cas.bean.Application;
import oct.rekord.cas.common.ReturnData;
import oct.rekord.cas.dao.ApplicationDAO;
import oct.rekord.cas.dao.AwardCertificateDAO;
import oct.rekord.cas.dao.UserInfoDAO;
import oct.rekord.cas.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    private ApplicationDAO applicationDAO;
    @Autowired
    private UserInfoDAO userInfoDAO;
    @Autowired
    private AwardCertificateDAO awardCertificateDAO;

    /**
     * 插入申请
     * @param application
     * @throws Exception
     */
    public void insertApplication(Application application) throws Exception {
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
    }

    @Override
    public ReturnData addApplication(Application application) {
        try {
            insertApplication(application);
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
        Date now = new Date();
        application.setReplyTime(now);
        try {
            applicationDAO.updateApplication(application);
            if ("1".equals(application.getStatus())) {
                applicationDAO.updateApplication(application);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnData.fail(502, "处理失败");
        }
        return ReturnData.success("处理成功");
    }
}
