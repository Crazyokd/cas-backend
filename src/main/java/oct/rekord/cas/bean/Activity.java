package oct.rekord.cas.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    private Integer actId;

    private String actName;

    private String actDescription;

    private String actImgPath;

    // 已报名人数
    private Integer actRegCount;

    // 活动最大报名人数
    private Integer actRegMaxCount;

    // 活动报名开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date actRegStartDate;
    // 活动报名结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date actRegEndDate;

    // 活动时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date actTime;
    // 活动地点
    private String actPlace;

    // 活动类别
    private String actCategory;

    // 所属学期ID
    private Integer SemesterId;

    // 活动打卡开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date actPunchStartTime;

    // 活动打卡结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date actPunchEndTime;

    // 活动打卡经度
    private Double actPunchLongitude;

    // 活动打卡纬度
    private Double actPunchLatitude;

    // 活动是否成功发布
    private String actIsPublish;

    public Activity(String actName, String actDescription, String actImgPath, Integer actRegMaxCount, Date actRegStartDate, Date actRegEndDate, Date actTime,
                    String actPlace, String actCategory, Integer SemesterId) {
        this.actName = actName;
        this.actDescription = actDescription;
        this.actImgPath = actImgPath;
        this.actRegMaxCount = actRegMaxCount;
        this.actRegStartDate = actRegStartDate;
        this.actRegEndDate = actRegEndDate;
        this.actTime = actTime;
        this.actPlace = actPlace;
        this.actCategory = actCategory;
        this.SemesterId = SemesterId;
    }
}
