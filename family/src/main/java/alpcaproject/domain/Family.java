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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String familyId;

    private String aptCd;

    private String aptNm;

    private String aptRdnAddr;

    private Double locX;

    private Double locY;

    private String cutomerId;

    private String role;

    private String status;

    @PostPersist
    public void onPostPersist() {
        if ("Approved".equals(this.status)) {
            FamilyApproved familyApproved = new FamilyApproved(this);
            familyApproved.publishAfterCommit();
        } else if ("Denied".equals(this.status)) {
            FamilyDenied familyDenied = new FamilyDenied(this);
            familyDenied.publishAfterCommit();
        }
    }

    public static FamilyRepository repository() {
        FamilyRepository familyRepository = FamilyApplication.applicationContext.getBean(
            FamilyRepository.class
        );
        return familyRepository;
    }

    //<<< Clean Arch / Port Method
    public static void customerRegistered(
        CustomerRegistered customerRegistered
    ) {
        Family family = new Family();
        family.setFamilyId(customerRegistered.getFamilyId());
        family.setCutomerId(customerRegistered.getCustomerId());
        family.setStatus("Registered");
        repository().save(family);
    }
}
