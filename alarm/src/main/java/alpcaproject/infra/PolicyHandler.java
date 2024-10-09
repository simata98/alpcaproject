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
    AlarmRepository alarmRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='MoveStarted'"
    )
    public void wheneverMoveStarted_SendAlarmMsg(
        @Payload MoveStarted moveStarted
    ) {
        MoveStarted event = moveStarted;
        System.out.println(
            "\n\n##### listener SendAlarmMsg : " + moveStarted + "\n\n"
        );

        // Sample Logic //
        Alarm.sendAlarmMsg(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='MoveCancelled'"
    )
    public void wheneverMoveCancelled_CancelAlarmMsg(
        @Payload MoveCancelled moveCancelled
    ) {
        MoveCancelled event = moveCancelled;
        System.out.println(
            "\n\n##### listener CancelAlarmMsg : " + moveCancelled + "\n\n"
        );

        // Sample Logic //
        Alarm.cancelAlarmMsg(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='MoveEnded'"
    )
    public void wheneverMoveEnded_EndAlarmMsg(@Payload MoveEnded moveEnded) {
        MoveEnded event = moveEnded;
        System.out.println(
            "\n\n##### listener EndAlarmMsg : " + moveEnded + "\n\n"
        );

        // Sample Logic //
        Alarm.endAlarmMsg(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
