package alpcaproject.domain;

import alpcaproject.domain.*;
import alpcaproject.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class LocRegistered extends AbstractEvent {

    private String locId;
    private String customerId;
    private Double locX;
    private Double locY;
    private String moveId;

    public LocRegistered(Location aggregate) {
        super(aggregate);
    }

    public LocRegistered() {
        super();
    }
}
//>>> DDD / Domain Event
