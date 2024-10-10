package alpcaproject.domain;

import alpcaproject.domain.*;
import alpcaproject.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
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
    private String status;

    public FamilyApproved(Family aggregate) {
        super(aggregate);
    }

    public FamilyApproved() {
        super();
    }
}
//>>> DDD / Domain Event
