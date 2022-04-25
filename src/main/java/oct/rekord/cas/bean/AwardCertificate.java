package oct.rekord.cas.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AwardCertificate {
    private Integer acId;
    private Integer userId;
    private String name;
    private String isValid;
    private Integer grade;
    private String category;
    private String explanation;
    private String comment;
    private String imgPath;
    private Date updateDate;

    public AwardCertificate(Integer userId, String name, String isValid, String category, String explanation, String comment, String imgPath) {
        this.userId = userId;
        this.name = name;
        this.isValid = isValid;
        this.category = category;
        this.explanation = explanation;
        this.comment = comment;
        this.imgPath = imgPath;
    }
}
