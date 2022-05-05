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
public class AwardCertificate {
    private Integer acId;
    private Integer userId;
    private String name;
    private Integer isValid;
    private Integer grade;
    private String category;
    private String explanation;
    private String comment;
    private String imgPath;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Integer semesterId;

    public AwardCertificate(Integer userId, String name, Integer isValid, String category, String explanation, String comment, String imgPath, Integer semesterId) {
        this.userId = userId;
        this.name = name;
        this.isValid = isValid;
        this.category = category;
        this.explanation = explanation;
        this.comment = comment;
        this.imgPath = imgPath;
        this.semesterId = semesterId;
    }
}
