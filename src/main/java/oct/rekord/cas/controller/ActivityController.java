package oct.rekord.cas.controller;

import oct.rekord.cas.common.ReturnData;
import oct.rekord.cas.service.ActivityService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("activity")
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @PostMapping("/get-running")
    public ReturnData getRunningActivity(HttpServletRequest request, @RequestParam("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize) {
        return activityService.getRunningActivity(request, pageNum, pageSize);
    }

    @PostMapping("/get-closed")
    public ReturnData getClosedActivity(HttpServletRequest request, @RequestParam("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize) {
        return activityService.getClosedActivity(request, pageNum, pageSize);
    }

    @PostMapping("/get-participate")
    public ReturnData getParticipateActivity(HttpServletRequest request, @RequestParam("userId") String userId) {
        return activityService.getParticipateActivity(request, userId);
    }

    @PostMapping("/get-today")
    public ReturnData getTodayActivity(HttpServletRequest request, @RequestParam("userId") String userId) {
        return activityService.getTodayActivity(request, userId);
    }

    @PostMapping("/activity/get")
    public ReturnData getOne(Integer actId) {
        return activityService.getActivity(actId);
    }
}
