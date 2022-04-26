package oct.rekord.cas.controller;

import oct.rekord.cas.common.ReturnData;
import oct.rekord.cas.service.ActivityService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @PostMapping("/getRunningActivity")
    public ReturnData getRunningActivity(HttpServletRequest request, @RequestParam("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize) {
        return activityService.getRunningActivity(request, pageNum, pageSize);
    }

    @PostMapping("/getClosedActivity")
    public ReturnData getClosedActivity(HttpServletRequest request, @RequestParam("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize) {
        return activityService.getClosedActivity(request, pageNum, pageSize);
    }

    @PostMapping("/getParticipateActivity")
    public ReturnData getParticipateActivity(HttpServletRequest request, @Param("userId") String userId) {
        return activityService.getParticipateActivity(request, userId);
    }

    @PostMapping("/getTodayActivity")
    public ReturnData getTodayActivity(HttpServletRequest request, @Param("userId") String userId) {
        return activityService.getTodayActivity(request, userId);
    }
}
