package oct.rekord.cas.service.impl;

import oct.rekord.cas.bean.Application;
import oct.rekord.cas.common.ApplicationCategoryEnum;
import oct.rekord.cas.common.ReturnData;
import oct.rekord.cas.dao.ActivityDAO;
import oct.rekord.cas.dao.ApplicationDAO;
import oct.rekord.cas.dao.AwardCertificateDAO;
import oct.rekord.cas.dao.UserInfoDAO;
import oct.rekord.cas.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private ActivityDAO activityDAO;

    /**
     * 插入申请
     * @param application
     * @throws Exception
     */
    public void insertApplication(Application application) throws Exception {
        int level = userInfoDAO.selectLevelByUserId(application.getApplicationFromId()) - 1;
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
    public ReturnData addApplication(HttpServletRequest request, Application application) {
        try {
            application.setApplicationFromId(Integer.valueOf(request.getAttribute("userId").toString()));
            insertApplication(application);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnData.fail(502, "申请失败");
        }
        return ReturnData.success("申请成功");
    }

    @Override
    public ReturnData getAllApplication(HttpServletRequest request) {
        List<Application> applications;
        try {
            applications = applicationDAO.selectApplicationByToId(Integer.valueOf(request.getAttribute("userId").toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnData.fail(502, "获取失败");
        }
        return ReturnData.success(applications);
    }

    @Override
    public ReturnData getNewApplication(HttpServletRequest request) {
        List<Application> applications;
        try {
            applications = applicationDAO.selectNewApplicationByToId(Integer.valueOf(request.getAttribute("userId").toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnData.fail(502, "获取失败");
        }
        return ReturnData.success(applications);
    }

    @Override
    public ReturnData getMyApplication(HttpServletRequest request) {
        List<Application> applications;
        try {
            applications = applicationDAO.selectMyApplicationByFromId(Integer.valueOf(request.getAttribute("userId").toString()));
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
            application = applicationDAO.selectApplicationById(application.getApplicationId());
            String category = application.getCategory();
            Integer status = application.getStatus();
            if (status == 1) {
                // 证书申请
                if (category.equals(ApplicationCategoryEnum.CERTIFICATE_APPLICATION.getCategory())) {
                    awardCertificateDAO.updateValidation(application.getLinkId());
                } else {
                    activityDAO.updateValidation(application.getLinkId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnData.fail(502, "处理失败");
        }
        return ReturnData.success("处理成功");
    }
}
