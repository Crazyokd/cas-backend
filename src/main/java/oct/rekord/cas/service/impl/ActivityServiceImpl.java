package oct.rekord.cas.service.impl;

import oct.rekord.cas.bean.Activity;
import oct.rekord.cas.common.ReturnData;
import oct.rekord.cas.dao.ActivityDAO;
import oct.rekord.cas.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service("activityService")
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityDAO activityDAO;

    @Override
    public ReturnData getRunningActivity(HttpServletRequest request) {
        List<Activity> activityList = activityDAO.selectRunningActivity();
        return ReturnData.success(activityList);
    }

    @Override
    public ReturnData getClosedActivity(HttpServletRequest request) {
        List<Activity> activityList = activityDAO.selectClosedActivity();
        return ReturnData.success(activityList);
    }

    @Override
    public ReturnData getParticipateActivity(HttpServletRequest request, String userId) {
        List<Activity> activityList = activityDAO.selectParticipateActivityByUserId(Integer.valueOf(userId));
        return ReturnData.success(activityList);
    }

    @Override
    public ReturnData getTodayActivity(HttpServletRequest request, String userId) {
        List<Activity> activityList = activityDAO.selectTodayActivityByUserId(Integer.valueOf(userId));
        return ReturnData.success(activityList);
    }
}
