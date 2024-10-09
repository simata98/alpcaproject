package alpcaproject.domain;

import alpcaproject.domain.*;
import alpcaproject.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class MemberDeleted extends AbstractEvent {

    private String customerId;
    private String familyId;

    public MemberDeleted(Cutomer aggregate) {
        super(aggregate);
    }

    public MemberDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
