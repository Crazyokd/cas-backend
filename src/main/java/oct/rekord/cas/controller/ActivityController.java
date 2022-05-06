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
    public ReturnData getRunningActivity(@RequestParam("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize) {
        return activityService.getRunningActivity(pageNum, pageSize);
    }

    @PostMapping("/get-closed")
    public ReturnData getClosedActivity(@RequestParam("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize) {
        return activityService.getClosedActivity(pageNum, pageSize);
    }

    @PostMapping("/get-participate")
    public ReturnData getParticipateActivity(HttpServletRequest request) {
        return activityService.getParticipateActivity(request);
    }

    @PostMapping("/get-today")
    public ReturnData getTodayActivity(HttpServletRequest request) {
        return activityService.getTodayActivity(request);
    }

    @PostMapping("/get-detail")
    public ReturnData getOne(@RequestParam("actId") Integer actId) {
        return activityService.getActivity(actId);
    }

    @PostMapping("/publish")
    public ReturnData publishActivity(HttpServletRequest request, @RequestParam("actName") String actName, @RequestParam("actDescription") String actDescription,
                                      @RequestParam(value = "actImg", required = false) MultipartFile actImg, @RequestParam("actRegMaxCount") Integer actRegMaxCount,
                                      @RequestParam("actRegStartDate") @DateTimeFormat(pattern = "yyyy-MM-dd")Date actRegStartDate, @RequestParam("actRegEndDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date actRegEndDate,
                                      @RequestParam("actTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date actTime, @RequestParam("actPlace") String actPlace,
                                      @RequestParam("actCategory") String actCategory, @RequestParam("semesterName") String semesterName) throws ParseException {
        return activityService.publishActivity(request, actName, actDescription, actImg, actRegMaxCount, actRegStartDate, actRegEndDate, actTime, actPlace, actCategory, semesterName);
    }

    @PostMapping("/register")
    public ReturnData registerActivity(HttpServletRequest request, @RequestParam("actId") Integer actId, @RequestParam("registerNumber") Integer registerNumber) {
        return activityService.registerActivity(request, actId, registerNumber);
    }
}
