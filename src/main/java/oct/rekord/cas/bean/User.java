package oct.rekord.cas.bean;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer userId;

    private String username;

    private String password;

    private String sn;

    private String snPassword;

    private String phone;

    private String email;

    private Integer level;

    private String headImgPath;

    public User (String username, String password, String phone) {
        this.username = username;
        this.password = password;
        this.phone = phone;
    }
}
