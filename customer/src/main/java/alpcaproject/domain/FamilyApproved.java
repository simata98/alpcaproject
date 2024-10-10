package alpcaproject.domain;

import alpcaproject.infra.AbstractEvent;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FamilyApproved extends AbstractEvent {

    private String familyId;
    private String aptCd;
    private String aptNm;
    private String aptRdnAddr;
    private Double locX;
    private Double locY;
    private String cutomerId;
    private String role;
    private String active;
}
