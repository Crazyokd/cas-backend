package oct.rekord.cas.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.extern.slf4j.Slf4j;
import oct.rekord.cas.common.ReturnData;
import oct.rekord.cas.service.ActivityService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

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

    @PostMapping("/get-detail")
    public ReturnData getOne(@RequestParam("actId") Integer actId) {
        return activityService.getActivity(actId);
    }

    @PostMapping("/publish")
    public ReturnData publishActivity(@RequestParam("actName") String actName, @RequestParam("actDescription") String actDescription,
                                      @RequestParam(value = "actImg", required = false) MultipartFile actImg, @RequestParam("actRegMaxCount") Integer actRegMaxCount,
                                      @RequestParam("actRegStartDate") @DateTimeFormat(pattern = "yyyy-MM-dd")Date actRegStartDate, @RequestParam("actRegEndDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date actRegEndDate,
                                      @RequestParam("actTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date actTime, @RequestParam("actPlace") String actPlace,
                                      @RequestParam("actCategory") String actCategory, @RequestParam("semesterName") String semesterName, @RequestParam("userId") Integer userId) throws ParseException {
        return activityService.publishActivity(userId, actName, actDescription, actImg, actRegMaxCount, actRegStartDate, actRegEndDate, actTime, actPlace, actCategory, semesterName);
    }

    @PostMapping("/register")
    public ReturnData registerActivity(@RequestParam("userId") Integer userId, @RequestParam("actId") Integer actId, @RequestParam("registerNumber") Integer registerNumber) {
        return activityService.registerActivity(userId, actId, registerNumber);
    }
}
