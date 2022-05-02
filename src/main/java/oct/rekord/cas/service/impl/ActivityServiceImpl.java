package oct.rekord.cas.service.impl;

import oct.rekord.cas.bean.Activity;
import oct.rekord.cas.bean.Application;
import oct.rekord.cas.common.ApplicationCategoryEnum;
import oct.rekord.cas.common.Image;
import oct.rekord.cas.common.ReturnData;
import oct.rekord.cas.dao.ActivityDAO;
import oct.rekord.cas.service.ActivityService;
import oct.rekord.cas.service.ApplicationService;
import oct.rekord.cas.service.SemesterService;
import oct.rekord.cas.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service("activityService")
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityDAO activityDAO;
    @Autowired
    ApplicationService applicationService;
    @Autowired
    SemesterService semesterService;

    private String activityImgDir;
    private String activityImgDefaultDir;

    @Autowired
    public ActivityServiceImpl(@Value("${my.image.activity-image-dir}") String activityImgDir, @Value("${my.image.activity-image-default-dir}") String activityImgDefaultDir) {
        this.activityImgDir = activityImgDir;
        this.activityImgDefaultDir = activityImgDefaultDir;

        // 创建用户设置的默认文件夹
        File activityImgDefaultDirectory = new File(activityImgDefaultDir);
        if (!activityImgDefaultDirectory.exists()) {
            activityImgDefaultDirectory.mkdirs();
        }
        // 创建用户设置的文件夹
        File activityImgDirectory = new File(activityImgDir);
        if (!activityImgDirectory.exists()) {
            activityImgDirectory.mkdirs();
        }
    }

    @Override
    public ReturnData getRunningActivity(HttpServletRequest request, Integer pageNum, Integer pageSize) {
        pageNum = pageNum < 1 ? 1 : pageNum;
        pageSize = pageSize == null ? 4 : pageSize;
        int startIndex = (pageNum - 1) * pageSize;

        List<Activity> activityList = activityDAO.selectRunningActivityByPageNum(startIndex, pageSize);
        return ReturnData.success(activityList);
    }

    @Override
    public ReturnData getClosedActivity(HttpServletRequest request, Integer pageNum, Integer pageSize) {
        pageNum = pageNum < 1 ? 1 : pageNum;
        pageSize = pageSize == null ? 4 : pageSize;
        int startIndex = (pageNum - 1) * pageSize;
        List<Activity> activityList = activityDAO.selectClosedActivity(startIndex, pageSize);
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

    @Override
    public ReturnData getActivity(Integer actId) {
        Map<String, Object> data = new LinkedHashMap<>();
        try {
            Activity activity = activityDAO.selectActivityByActId(actId);
            data.put("活动名称", activity.getActName());
            data.put("活动描述", activity.getActDescription());
            data.put("活动图片", FileUtil.fileNameToBytes(activity.getActImgPath()));
            data.put("活动已报名人数", activity.getActRegCount());
            data.put("活动报名人数上限", activity.getActRegMaxCount());
            data.put("活动报名开始时间", activity.getActRegStartDate());
            data.put("活动报名截止时间", activity.getActRegEndDate());
            data.put("活动开始时间", activity.getActTime());
            data.put("活动地点", activity.getActPlace());
            data.put("活动类别", activity.getActCategory());
            data.put("活动是否成功发布", activity.getActIsPublish() == "1" ? "是" : "否");
            data.put("活动所属学年", semesterService.getSemesterNameBySemesterId(activity.getSemesterId()));
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnData.fail(502, "获取失败");
        }
        return ReturnData.success(data);
    }

    @Override
    public ReturnData publishActivity(Integer userId, String actName, String actDescription, MultipartFile actImg, Integer actRegMaxCount, Date actRegStartDate, Date actRegEndDate, Date actTime, String actPlace, String actCategory, String semesterName) {
        String fileName = FileUtil.fileTransfer(actImg, this.activityImgDir, Image.IMG_SUFFIX, 0, Image.MAX_IMG_SIZE);

        // 通过 semesterName 查询 semesterId
        Integer semesterId = semesterService.getSemesterIdBySemesterName(semesterName);
        if (semesterId == null) {
            return ReturnData.fail(502, "学年有误");
        }

        String actImgPath = (fileName.equals("default.jpg") ? this.activityImgDefaultDir : this.activityImgDir) + fileName;
        try {
                Activity activity = new Activity(actName, actDescription, actImgPath, actRegMaxCount, actRegStartDate,
                    actRegEndDate, actTime, actPlace, actCategory, semesterId);
                activityDAO.publishActivity(activity);
                applicationService.insertApplication(new Application(userId, ApplicationCategoryEnum.ACTIVITY_APPLICATION.getCategory(), activity.getActId(), ""));
        } catch (Exception e) {
            return ReturnData.fail(502, "活动创建失败");
        }
        return ReturnData.success("活动创建成功");
    }

    @Override
    public ReturnData registerActivity(Integer userId, Integer actId, Integer registerNumber) {
        activityDAO.registerActivity(userId, actId, registerNumber);
        return ReturnData.success();
    }
}
