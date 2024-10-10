package alpcaproject.domain;

import alpcaproject.domain.*;
import alpcaproject.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class CustomerRegistered extends AbstractEvent {

    private String customerId;
    private String familyId;
    private String status;
}
