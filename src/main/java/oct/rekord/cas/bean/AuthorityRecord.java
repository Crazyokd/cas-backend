package oct.rekord.cas.bean;

import lombok.Data;

@Data
public class AuthorityRecord {
    private Integer authorityRecordId;
    private Integer fromUserId;
    private Integer toUserId;
    private String action;
}
