package alpcaproject.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Table;

import alpcaproject.CustomerApplication;
import lombok.Data;

@Entity
@Table(name = "Cutomer_table")
@Data
//<<< DDD / Aggregate Root
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String customerId;
    private String familyId;
    private String status;

    @PostPersist
    public void onPostPersist() {
        // customer를 등록하려면 family 등록도 진행되어야 함
        CustomerRegistered customerRegistered = new CustomerRegistered(this);
        customerRegistered.publishAfterCommit();
    }

    public static CustomerRepository repository() {
        CustomerRepository customerRepository = CustomerApplication.applicationContext.getBean(
                CustomerRepository.class);
        return customerRepository;
    }
    
    @PostUpdate
    public void onPostUpdate() {
        if (this.status.equals("Active")) {
            MemberRegistered memberRegistered = new MemberRegistered(this);
            memberRegistered.publishAfterCommit();
        } else if (this.status.equals("Inactive")) {
            MemberDeleted memberDeleted = new MemberDeleted(this);
            memberDeleted.publishAfterCommit();
        }
    }

    public void deleteCustomer() {
        this.status = "Deleted";
        CustomerDeleted customerDeleted = new CustomerDeleted(this);
        customerDeleted.publishAfterCommit();
    }

    public static void updateActive(FamilyApproved familyApproved) {
        Customer customer = repository().findById(Long.parseLong(familyApproved.getCutomerId())).orElse(new Customer());
        if (customer != null) {
            customer.setStatus("Active");
            customer.setFamilyId(familyApproved.getFamilyId());
    
            repository().save(customer);
    
            MemberRegistered memberRegistered = new MemberRegistered(customer);
            memberRegistered.publishAfterCommit();
        }
    }

    public static void updateInactive(FamilyDenied familyDenied) {
        Customer customer = repository().findById(Long.parseLong(familyDenied.getCutomerId())).orElse(new Customer());
        customer.setStatus("Inactive");
        customer.setFamilyId("null");

        repository().save(customer);

        MemberDeleted memberDeleted = new MemberDeleted(customer);
        memberDeleted.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
