package alpcaproject.domain;

import alpcaproject.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class LocRegistered extends AbstractEvent {

    private String locId;
    private String customerId;
    private Double locX;
    private Double locY;
    private String moveId;
}
