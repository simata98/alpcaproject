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

    //<<< Clean Arch / Port Method
    public static void customerRegistered(
        CustomerRegistered customerRegistered
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Family family = new Family();
        repository().save(family);

        */

        /** Example 2:  finding and process
        
        repository().findById(customerRegistered.get???()).ifPresent(family->{
            
            family // do something
            repository().save(family);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
