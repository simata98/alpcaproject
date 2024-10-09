package alpcaproject.domain;

import alpcaproject.domain.*;
import alpcaproject.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class CustomerDeleted extends AbstractEvent {

    private String customerId;

    public CustomerDeleted(Cutomer aggregate) {
        super(aggregate);
    }

    public CustomerDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
