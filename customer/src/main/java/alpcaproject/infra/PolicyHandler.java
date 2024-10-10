package alpcaproject.infra;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import alpcaproject.config.kafka.KafkaProcessor;
import alpcaproject.domain.Customer;
import alpcaproject.domain.CustomerRepository;
import alpcaproject.domain.FamilyApproved;
import alpcaproject.domain.FamilyDenied;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    CustomerRepository customerRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='FamilyApproved'"
    )
    public void wheneverFamilyApproved_UpdateActive(
        @Payload FamilyApproved familyApproved
    ) {
        if (!familyApproved.validate()) {
            return;
        }

        System.out.println(
            "\n\n##### listener UpdateActive : " + familyApproved + "\n\n"
        );

        // FamilyApproved 이벤트 수신 시 Customer의 상태를 Active로 변경
        Customer.updateActive(familyApproved);
    }
    

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='FamilyDenied'"
    )
    public void wheneverFamilyDenied_UpdateInactive(
        @Payload FamilyDenied familyDenied
    ) {
        if (!familyDenied.validate()) {
            return;
        }

        System.out.println(
            "\n\n##### listener UpdateInactive : " + familyDenied + "\n\n"
        );

        // FamilyDenied 이벤트 수신 시 Customer의 상태를 Inactive로 변경
        Customer.updateInactive(familyDenied);
    }
}
//>>> Clean Arch / Inbound Adaptor
