package alpcaproject.infra;

import alpcaproject.config.kafka.KafkaProcessor;
import alpcaproject.domain.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    CutomerRepository cutomerRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='FamilyApproved'"
    )
    public void wheneverFamilyApproved_UpdateActive(
        @Payload FamilyApproved familyApproved
    ) {
        FamilyApproved event = familyApproved;
        System.out.println(
            "\n\n##### listener UpdateActive : " + familyApproved + "\n\n"
        );

        // Sample Logic //
        Cutomer.updateActive(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='FamilyDenied'"
    )
    public void wheneverFamilyDenied_UpdateInactive(
        @Payload FamilyDenied familyDenied
    ) {
        FamilyDenied event = familyDenied;
        System.out.println(
            "\n\n##### listener UpdateInactive : " + familyDenied + "\n\n"
        );

        // Sample Logic //
        Cutomer.updateInactive(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
