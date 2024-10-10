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

    private String moveId;

    @PostPersist
    public void onPostPersist() {
//        repository().findById(this.locId).ifPresentOrElse(location -> {
//            LocRemoved locRemoved = new LocRemoved(this);
//            locRemoved.publishAfterCommit();
//        }, () -> {
//            LocRegistered locRegistered = new LocRegistered(this);
//            locRegistered.publishAfterCommit();
//        });
    }

    public static LocationRepository repository() {
        LocationRepository locationRepository = LocationApplication.applicationContext.getBean(
            LocationRepository.class
        );
        return locationRepository;
    }

    public void register() {
        LocRegistered locRegistered = new LocRegistered(this);
        locRegistered.publishAfterCommit();
    }

    public void delete() {
        LocRemoved locRemoved = new LocRemoved(this);
        locRemoved.publishAfterCommit();
    }
}
//>>> DDD / Aggregate Root
