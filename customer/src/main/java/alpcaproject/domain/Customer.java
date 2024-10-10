package alpcaproject.domain;

import java.util.List;

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
        List<Customer> customers = repository().findByFamilyId(familyApproved.getFamilyId());
        for (Customer customer : customers) {
            customer.setStatus("Active");
            repository().save(customer);
            
            MemberRegistered memberRegistered = new MemberRegistered(customer);
            memberRegistered.publishAfterCommit();
        }
    }

    public static void updateInactive(FamilyDenied familyDenied) {
        repository().findById(Long.parseLong(familyDenied.getCutomerId())).ifPresent(customer -> {
            customer.setStatus("Inactive");
            customer.setFamilyId(null);
            repository().save(customer);
            MemberDeleted memberDeleted = new MemberDeleted(customer);
            memberDeleted.publishAfterCommit();
        });
    }
}
