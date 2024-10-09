package alpcaproject.infra;

import alpcaproject.domain.*;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/cutomers")
@Transactional
public class CustomerController {
    // Customer를 삭제하는 로직
    @Autowired
    CustomerRepository customerRepository;
    
    @RequestMapping(
        value="/customers/{id}/deleteCustomer",
        method=RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public Customer deleteCustomer(
        @PathVariable String id,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /customer/deleteCustomer  called #####");
        Long customerId = Long.parseLong(id);
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        optionalCustomer.orElseThrow(() -> new Exception("No Customer Found"));
        Customer customer = optionalCustomer.get();
        customer.deleteCustomer();

        customerRepository.save(customer);
        return customer;
    }
}
//>>> Clean Arch / Inbound Adaptor
