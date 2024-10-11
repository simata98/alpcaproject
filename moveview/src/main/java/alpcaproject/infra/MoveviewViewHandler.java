package alpcaproject.infra;

import alpcaproject.config.kafka.KafkaProcessor;
import alpcaproject.domain.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class MoveviewViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private MoveviewRepository moveviewRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenMoveStarted_then_CREATE_1(
        @Payload MoveStarted moveStarted
    ) {
        try {
            if (!moveStarted.validate()) return;

            // view 객체 생성
            Moveview moveview = new Moveview();
            // view 객체에 이벤트의 Value 를 set 함
            moveview.setMoveId(moveStarted.getMoveId());
            moveview.setCustomerId(moveStarted.getCustomerId());
            moveview.setStatus(moveStarted.getStatus());
            moveview.setDistance(moveStarted.getDistance());
            moveview.setDuration(moveStarted.getDuration());
            // view 레파지 토리에 save
            moveviewRepository.save(moveview);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenMoveCancelled_then_UPDATE_1(
        @Payload MoveCancelled moveCancelled
    ) {
        try {
            if (!moveCancelled.validate()) return;
            // view 객체 조회
            Optional<Moveview> moveviewOptional = moveviewRepository.findById(
                moveCancelled.getMoveId()
            );

            if (moveviewOptional.isPresent()) {
                Moveview moveview = moveviewOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                moveview.setStatus(moveCancelled.getStatus());
                // view 레파지 토리에 save
                moveviewRepository.save(moveview);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenMoveUpdated_then_UPDATE_2(
        @Payload MoveUpdated moveUpdated
    ) {
        try {
            if (!moveUpdated.validate()) return;
            // view 객체 조회
            Optional<Moveview> moveviewOptional = moveviewRepository.findById(
                moveUpdated.getMoveId()
            );

            if (moveviewOptional.isPresent()) {
                Moveview moveview = moveviewOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                moveview.setDistance(moveUpdated.getDistance());
                moveview.setDuration(moveUpdated.getDuration());
                moveview.setStatus(moveUpdated.getStatus());
                // view 레파지 토리에 save
                moveviewRepository.save(moveview);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenLocRegistered_then_UPDATE_3(
            @Payload LocRegistered locRegistered
    ) {
        try {
            if (!locRegistered.validate()) return;
            // view 객체 조회
            Optional<Moveview> moveviewOptional = moveviewRepository.findById(
                    locRegistered.getMoveId()
            );

            if (moveviewOptional.isPresent()) {
                Moveview moveview = moveviewOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                moveview.setLocId(locRegistered.getLocId());
                // view 레파지 토리에 save
                moveviewRepository.save(moveview);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
