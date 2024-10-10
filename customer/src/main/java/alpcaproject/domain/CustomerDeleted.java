package alpcaproject.domain;

import alpcaproject.infra.AbstractEvent;
import lombok.Data;
import lombok.ToString;

//<<< DDD / Domain Event
@Data
@ToString
public class CustomerDeleted extends AbstractEvent {

    private String customerId;
    private String status;

    public CustomerDeleted(Customer aggregate) {
        super(aggregate);
    }

    public CustomerDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
