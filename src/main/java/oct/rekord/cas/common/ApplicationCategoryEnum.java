package oct.rekord.cas.common;

import lombok.Getter;

@Getter
public enum ApplicationCategoryEnum {
    /**
     * 证书申请
     */
    CERTIFICATE_APPLICATION("证书申请"),

    /**
     * 活动发布申请
     */
    ACTIVITY_APPLICATION("活动申请");

    private String category;
    ApplicationCategoryEnum(String category) {
        this.category = category;
    }
}
