package oct.rekord.cas.bean;

import lombok.Data;

@Data
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
    private String actRegStartDate;
    // 活动报名结束时间
    private String actRegEndDate;

    // 活动时间
    private String actTime;
    // 活动地点
    private String actPlace;

    // 活动类别
    private String actCategory;

    private Integer actSemesterId;

}
