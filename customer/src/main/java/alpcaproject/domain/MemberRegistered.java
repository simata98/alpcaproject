package alpcaproject.domain;

import alpcaproject.infra.AbstractEvent;
import lombok.Data;
import lombok.ToString;

//<<< DDD / Domain Event
@Data
@ToString
public class MemberRegistered extends AbstractEvent {

    private String customerId;
    private String familyId;
    private String status;

    public MemberRegistered(Customer aggregate) {
        super(aggregate);
    }

    public MemberRegistered() {
        super();
    }
}
//>>> DDD / Domain Event
