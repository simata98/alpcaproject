package alpcaproject.domain;

import alpcaproject.domain.*;
import alpcaproject.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class MoveUpdated extends AbstractEvent {

    private String moveId;
    private String familyId;
    private String role;
    private String startRdnAddr;
    private Double startLocX;
    private Double startLocY;
    private Double goalLocX;
    private Double goalLocY;
    private Integer distance;
    private Integer duration;
    private String path;
    private String status;
    private String customerId;

    public MoveUpdated(Move aggregate) {
        super(aggregate);
    }

    public MoveUpdated() {
        super();
    }
}
//>>> DDD / Domain Event
