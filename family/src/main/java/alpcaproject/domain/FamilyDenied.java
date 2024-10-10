package alpcaproject.domain;

import alpcaproject.domain.*;
import alpcaproject.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class FamilyDenied extends AbstractEvent {

    private String familyId;
    private String aptCd;
    private String aptNm;
    private String aptRdnAddr;
    private Double locX;
    private Double locY;
    private String cutomerId;
    private String role;
    private String active;

    public FamilyDenied(Family aggregate) {
        super(aggregate);
    }

    public FamilyDenied() {
        super();
    }
}
//>>> DDD / Domain Event
