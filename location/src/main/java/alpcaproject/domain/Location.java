package alpcaproject.domain;

import alpcaproject.LocationApplication;
import alpcaproject.domain.LocRegistered;
import alpcaproject.domain.LocRemoved;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Location_table")
@Data
//<<< DDD / Aggregate Root
public class Location {

    @Id
    private String locId;

    private String customerId;

    private Double locX;

    private Double locY;

    @PostPersist
    public void onPostPersist() {
        LocRegistered locRegistered = new LocRegistered(this);
        locRegistered.publishAfterCommit();

        LocRemoved locRemoved = new LocRemoved(this);
        locRemoved.publishAfterCommit();
    }

    public static LocationRepository repository() {
        LocationRepository locationRepository = LocationApplication.applicationContext.getBean(
            LocationRepository.class
        );
        return locationRepository;
    }
}
//>>> DDD / Aggregate Root
