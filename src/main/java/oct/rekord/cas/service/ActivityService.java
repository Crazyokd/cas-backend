package oct.rekord.cas.service;

import oct.rekord.cas.common.ReturnData;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public interface ActivityService {
    /**
     * 图片后缀
     */
    List<String> IMG_SUFFIX = Arrays.asList(".jpg","jpeg",".png",".gif");

    /**
     * 单个图片的最大大小
     */
    long MAX_IMG_SIZE = 1024 * 1024 * 8;

    ReturnData getRunningActivity(HttpServletRequest request, Integer pageNum, Integer pageSize);

    ReturnData getClosedActivity(HttpServletRequest request, Integer pageNum, Integer pageSize);

    ReturnData getParticipateActivity(HttpServletRequest request, String userId);

    ReturnData getTodayActivity(HttpServletRequest request, String userId);

    ReturnData getActivity(Integer actId);

    ReturnData publishActivity(Integer userId, String actName, String actDescription, MultipartFile actImg, Integer actRegMaxCount, Date actRegStartDate,
                               Date actRegEndDate, Date actTime, String actPlace, String actCategory, String semesterName);
}
