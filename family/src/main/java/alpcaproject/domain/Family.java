package alpcaproject.domain;

import alpcaproject.FamilyApplication;
import alpcaproject.domain.FamilyApproved;
import alpcaproject.domain.FamilyDenied;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Family_table")
@Data
//<<< DDD / Aggregate Root
public class Family {

    @Id
    private String familyId;

    private String aptCd;

    private String aptNm;

    private String aptRdnAddr;

    private Double locX;

    private Double locY;

    private String cutomerId;

    private String role;

    private String active;

    @PostPersist
    public void onPostPersist() {
        FamilyApproved familyApproved = new FamilyApproved(this);
        familyApproved.publishAfterCommit();

        FamilyDenied familyDenied = new FamilyDenied(this);
        familyDenied.publishAfterCommit();
    }

    public static FamilyRepository repository() {
        FamilyRepository familyRepository = FamilyApplication.applicationContext.getBean(
            FamilyRepository.class
        );
        return familyRepository;
    }
}
//>>> DDD / Aggregate Root
