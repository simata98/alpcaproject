package alpcaproject.domain;

import alpcaproject.domain.*;
import alpcaproject.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class LocRegistered extends AbstractEvent {

    private String locId;
    private String customerId;
    private Double locX;
    private Double locY;
}
