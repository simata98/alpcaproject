package alpcaproject.domain;

import alpcaproject.domain.*;
import alpcaproject.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class LocRemoved extends AbstractEvent {

    private String locId;
    private String customerId;
    private Double locX;
    private Double locY;

    public LocRemoved(Location aggregate) {
        super(aggregate);
    }

    public LocRemoved() {
        super();
    }
}
//>>> DDD / Domain Event
