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
    MoveRepository moveRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='LocRegistered'"
    )
    public void wheneverLocRegistered_UpdateGoalLoc(
        @Payload LocRegistered locRegistered
    ) {
        LocRegistered event = locRegistered;
        System.out.println(
            "\n\n##### listener UpdateGoalLoc : " + locRegistered + "\n\n"
        );

        // Sample Logic //
        Move.updateGoalLoc(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='LocRemoved'"
    )
    public void wheneverLocRemoved_RemoveGoalLoc(
        @Payload LocRemoved locRemoved
    ) {
        LocRemoved event = locRemoved;
        System.out.println(
            "\n\n##### listener RemoveGoalLoc : " + locRemoved + "\n\n"
        );

        // Sample Logic //
        Move.removeGoalLoc(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='MoveStarted'"
    )
    public void wheneverMoveStarted_StartMove(
        @Payload MoveStarted moveStarted
    ) {
        MoveStarted event = moveStarted;
        System.out.println(
            "\n\n##### listener StartMove : " + moveStarted + "\n\n"
        );

        // Sample Logic //
        Move.startMove(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='MoveUpdated'"
    )
    public void wheneverMoveUpdated_UpdateMove(
        @Payload MoveUpdated moveUpdated
    ) {
        MoveUpdated event = moveUpdated;
        System.out.println(
            "\n\n##### listener UpdateMove : " + moveUpdated + "\n\n"
        );

        // Sample Logic //
        Move.updateMove(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='MoveCancelled'"
    )
    public void wheneverMoveCancelled_CancelMove(
        @Payload MoveCancelled moveCancelled
    ) {
        MoveCancelled event = moveCancelled;
        System.out.println(
            "\n\n##### listener CancelMove : " + moveCancelled + "\n\n"
        );

        // Sample Logic //
        Move.cancelMove(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='MoveEnded'"
    )
    public void wheneverMoveEnded_EndMove(@Payload MoveEnded moveEnded) {
        MoveEnded event = moveEnded;
        System.out.println(
            "\n\n##### listener EndMove : " + moveEnded + "\n\n"
        );

        // Sample Logic //
        Move.endMove(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
