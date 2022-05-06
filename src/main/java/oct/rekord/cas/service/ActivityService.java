package oct.rekord.cas.service;

import oct.rekord.cas.common.ReturnData;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public interface ActivityService {

    ReturnData getRunningActivity(Integer pageNum, Integer pageSize);

    ReturnData getClosedActivity(Integer pageNum, Integer pageSize);

    ReturnData getParticipateActivity(HttpServletRequest request);

    ReturnData getTodayActivity(HttpServletRequest request);

    ReturnData getActivity(Integer actId);

    ReturnData publishActivity(HttpServletRequest request, String actName, String actDescription, MultipartFile actImg, Integer actRegMaxCount, Date actRegStartDate,
                               Date actRegEndDate, Date actTime, String actPlace, String actCategory, String semesterName);

    ReturnData registerActivity(HttpServletRequest request, Integer actId, Integer registerNumber);
}
