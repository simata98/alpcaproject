package alpcaproject.domain;

import alpcaproject.infra.AbstractEvent;
import lombok.Data;
import lombok.ToString;

//<<< DDD / Domain Event
@Data
@ToString
public class MemberDeleted extends AbstractEvent {

    private String customerId;
    private String familyId;
    private String status;

    public MemberDeleted(Customer aggregate) {
        super(aggregate);
    }

    public MemberDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
