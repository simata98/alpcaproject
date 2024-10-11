package alpcaproject.domain;

import alpcaproject.MoveApplication;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.*;

import alpcaproject.util.NaverMapClient;
import alpcaproject.util.NaverMapService;
import alpcaproject.util.NaverMapsAPIDTO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "Move_table")
@Data
@Slf4j
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

    public static MoveRepository repository() {
        MoveRepository moveRepository = MoveApplication.applicationContext.getBean(
            MoveRepository.class
        );
        return moveRepository;
    }
    public static NaverMapService naverMapService() {
        NaverMapService naverMapService = MoveApplication.applicationContext.getBean(
                NaverMapService.class
        );
        return naverMapService;
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
    //<<< Clean Arch / Port Method
    public void startMove(StartMoveCommand startMoveCommand) {
        //implement business logic here: move 생성
        Move move = new Move();
        // from caller
        move.setMoveId(startMoveCommand.getMoveId());
        move.setCustomerId(startMoveCommand.getCustomerId());
        move.setFamilyId(startMoveCommand.getFamilyId());
        move.setRole("143rwe");
        move.setStartRdnAddr(startMoveCommand.getStartRdnAddr());
        // from api
        NaverMapsAPIDTO.ResponseDTO resp = naverMapService().getDirections(
                startMoveCommand.getStartLocX().toString() + "," + startMoveCommand.getStartLocY().toString(),
                startMoveCommand.getGoalLocX().toString() + "," + startMoveCommand.getGoalLocY().toString()
                );
        move.setDistance(resp.getRoute().getTraavoidtoll().get(0).getSummary().getDistance());
        move.setDuration(resp.getRoute().getTraavoidtoll().get(0).getSummary().getDuration());
        move.setPath(resp.getRoute().getTraavoidtoll().get(0).getPath().get(2).toString());
        move.setStartLocX(startMoveCommand.getStartLocX());
        move.setStartLocY(startMoveCommand.getStartLocY());
        move.setGoalLocX(startMoveCommand.getGoalLocX());
        move.setGoalLocY(startMoveCommand.getGoalLocY());
        // fixed
        move.setStatus("start");
        repository().save(move);
        MoveStarted moveStarted = new MoveStarted(move);
        moveStarted.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void updateMove(UpdateMoveCommand updateMoveCommand) {
        //implement business logic here: update properties
        repository().findById(updateMoveCommand.getMoveId()).ifPresent(move->{

            String coordinates = move.getPath().replace("[", "").replace("]", "");
            String[] parts = coordinates.split(",\\s*");

            String startLocx = parts[0];
            String startLocy = parts[1];

            // from api
            NaverMapsAPIDTO.ResponseDTO resp = naverMapService().getDirections(
                    startLocx + "," + startLocy,
                    move.getGoalLocX().toString() + "," + move.getGoalLocY().toString()
            );
            move.setDistance(resp.getRoute().getTraavoidtoll().get(0).getSummary().getDistance());
            move.setDuration(resp.getRoute().getTraavoidtoll().get(0).getSummary().getDuration());
            move.setPath(resp.getRoute().getTraavoidtoll().get(0).getPath().get(2).toString());
            move.setStatus("moving");
            repository().save(move);
            MoveUpdated moveUpdated = new MoveUpdated(move);
            moveUpdated.publishAfterCommit();
         });
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void cancelMove(CancelMoveCommand cancelMoveCommand) {
        //implement business logic here: update status

        repository().findById(cancelMoveCommand.getMoveId()).ifPresent(move->{
            move.setStatus("cancel");
            repository().save(move);
            MoveCancelled moveCancelled = new MoveCancelled(move);
            moveCancelled.publishAfterCommit();
         });
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void endMove(EndMoveCommand endMoveCommand) {
        //implement business logic here: update status
        repository().findById(endMoveCommand.getMoveId()).ifPresent(move->{
            NaverMapsAPIDTO.ResponseDTO resp = naverMapService().getDirections(
                    move.getStartLocX().toString() + "," + move.getStartLocY(),
                    move.getGoalLocX() + "," + move.getGoalLocY()
            );
            move.setDistance(resp.getRoute().getTraavoidtoll().get(0).getSummary().getDistance());
            move.setDuration(resp.getRoute().getTraavoidtoll().get(0).getSummary().getDuration());
            move.setPath(resp.getRoute().getTraavoidtoll().get(0).getPath().get(0).toString());
            move.setStatus("end");
            repository().save(move);
            MoveEnded moveEnded = new MoveEnded(move);
            moveEnded.publishAfterCommit();
         });
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
