package alpcaproject.domain;

import alpcaproject.CustomerApplication;
import alpcaproject.domain.CustomerDeleted;
import alpcaproject.domain.CustomerRegistered;
import alpcaproject.domain.MemberDeleted;
import alpcaproject.domain.MemberRegistered;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Cutomer_table")
@Data
//<<< DDD / Aggregate Root
public class Cutomer {

    @Id
    private String customerId;

    private String familyId;

    @PostPersist
    public void onPostPersist() {
        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        alpcaproject.external.Family family = new alpcaproject.external.Family();
        // mappings goes here
        CustomerApplication.applicationContext
            .getBean(alpcaproject.external.FamilyService.class)
            .approveMember(family);

        CustomerRegistered customerRegistered = new CustomerRegistered(this);
        customerRegistered.publishAfterCommit();

        CustomerDeleted customerDeleted = new CustomerDeleted(this);
        customerDeleted.publishAfterCommit();

        MemberRegistered memberRegistered = new MemberRegistered(this);
        memberRegistered.publishAfterCommit();

        MemberDeleted memberDeleted = new MemberDeleted(this);
        memberDeleted.publishAfterCommit();
    }

    public static CutomerRepository repository() {
        CutomerRepository cutomerRepository = CustomerApplication.applicationContext.getBean(
            CutomerRepository.class
        );
        return cutomerRepository;
    }

    //<<< Clean Arch / Port Method
    public static void updateActive(FamilyApproved familyApproved) {
        //implement business logic here:

        /** Example 1:  new item 
        Cutomer cutomer = new Cutomer();
        repository().save(cutomer);

        */

        /** Example 2:  finding and process
        
        repository().findById(familyApproved.get???()).ifPresent(cutomer->{
            
            cutomer // do something
            repository().save(cutomer);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void updateInactive(FamilyDenied familyDenied) {
        //implement business logic here:

        /** Example 1:  new item 
        Cutomer cutomer = new Cutomer();
        repository().save(cutomer);

        */

        /** Example 2:  finding and process
        
        repository().findById(familyDenied.get???()).ifPresent(cutomer->{
            
            cutomer // do something
            repository().save(cutomer);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
