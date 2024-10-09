package alpcaproject.domain;

import alpcaproject.MoveApplication;
import alpcaproject.domain.MoveCancelled;
import alpcaproject.domain.MoveEnded;
import alpcaproject.domain.MoveStarted;
import alpcaproject.domain.MoveUpdated;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Move_table")
@Data
//<<< DDD / Aggregate Root
public class Move {

    @Id
    private String moveId;

    private String familyId;

    private String role;

    private String startRdnAddr;

    private Double startLocX;

    private Double startLocY;

    private Double goalLocX;

    private Double goalLocY;

    private Integer distance;

    private Integer duration;

    private String path;

    private String status;

    private String customerId;

    @PostPersist
    public void onPostPersist() {
//        MoveStarted moveStarted = new MoveStarted(this);
//        moveStarted.publishAfterCommit();
//
//        MoveCancelled moveCancelled = new MoveCancelled(this);
//        moveCancelled.publishAfterCommit();
//
//        MoveEnded moveEnded = new MoveEnded(this);
//        moveEnded.publishAfterCommit();
//
//        MoveUpdated moveUpdated = new MoveUpdated(this);
//        moveUpdated.publishAfterCommit();
    }

    public static MoveRepository repository() {
        MoveRepository moveRepository = MoveApplication.applicationContext.getBean(
            MoveRepository.class
        );
        return moveRepository;
    }

    //<<< Clean Arch / Port Method
    public static void updateGoalLoc(LocRegistered locRegistered) {
        //implement business logic here: 목적지 좌표 업데이트
        repository().findById(locRegistered.getMoveId()).ifPresent(move-> {
            move.setStartLocX(locRegistered.getLocX());
            move.setStartLocY(locRegistered.getLocY());
            repository().save(move);
        });
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void removeGoalLoc(LocRemoved locRemoved) {
            //implement business logic here: 목적지 삭제
            repository().findById(locRemoved.getMoveId()).ifPresent(move->{
                repository().delete(move);
            });

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void startMove(MoveStarted moveStarted) {
        //implement business logic here: move 생성
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddsssss");
        Move move = new Move();
        move.setMoveId("move-" + LocalDateTime.now().format(formatter));
        // from caller
        move.setCustomerId("1234");
        move.setFamilyId("1234");
        move.setRole("143rwe");
        move.setStartRdnAddr("fadsf");
        // from api
        move.setDistance(123);
        move.setDuration(123);
        move.setPath("1234");
        move.setStartLocX(55.33);
        move.setStartLocY(1.23);
        move.setGoalLocX(5.234);
        move.setGoalLocY(4.232);
        // fixed
        move.setStatus("start");
        repository().save(move);

        moveStarted.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void updateMove(MoveUpdated moveUpdated) {
        //implement business logic here: update properties
        repository().findById(moveUpdated.getMoveId()).ifPresent(move->{
            move.setPath("fasdf");
            move.setDuration(14123);
            move.setDistance(25234);
            repository().save(move);
         });
        moveUpdated.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void cancelMove(MoveCancelled moveCancelled) {
        //implement business logic here: update status

        repository().findById(moveCancelled.getMoveId()).ifPresent(move->{
            move.setStatus("cancel");
            repository().save(move);
         });
        moveCancelled.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void endMove(MoveEnded moveEnded) {
        //implement business logic here: update status
        repository().findById(moveEnded.getMoveId()).ifPresent(move->{
            move.setPath("fasdf");
            move.setDuration(14123);
            move.setDistance(25234);
            move.setStatus("end");
            repository().save(move);
         });
        moveEnded.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
