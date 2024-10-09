package alpcaproject.domain;

import alpcaproject.MoveApplication;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.*;

import alpcaproject.util.NaverMapClient;
import alpcaproject.util.NaverMapService;
import alpcaproject.util.NaverMapsAPIDTO;
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

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static String startMove(MoveStarted moveStarted) {
        //implement business logic here: move 생성
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddss");
        Move move = new Move();
        move.setMoveId("move-" + LocalDateTime.now().format(formatter));
        // from caller
        move.setCustomerId(moveStarted.getCustomerId());
        move.setFamilyId(moveStarted.getFamilyId());
        move.setRole("143rwe");
        move.setStartRdnAddr(moveStarted.getStartRdnAddr());
        // from api
        NaverMapsAPIDTO.ResponseDTO resp = naverMapService().getDirections(
                moveStarted.getStartLocX().toString() + "," + moveStarted.getStartLocY().toString(),
                moveStarted.getGoalLocX().toString() + "," + moveStarted.getGoalLocY().toString()
                );
        move.setDistance(resp.getRoute().getTraavoidtoll().get(0).getSummary().getDistance());
        move.setDuration(resp.getRoute().getTraavoidtoll().get(0).getSummary().getDuration());
        move.setPath(resp.getRoute().getTraavoidtoll().get(0).getPath().get(0).toString());
        move.setStartLocX(moveStarted.getStartLocX());
        move.setStartLocY(moveStarted.getStartLocY());
        move.setGoalLocX(moveStarted.getGoalLocX());
        move.setGoalLocY(moveStarted.getGoalLocY());
        // fixed
        move.setStatus("start");
        repository().save(move);

        moveStarted.publishAfterCommit();

        return move.getMoveId();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void updateMove(MoveUpdated moveUpdated) {
        //implement business logic here: update properties
        repository().findById(moveUpdated.getMoveId()).ifPresent(move->{

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
            move.setPath(resp.getRoute().getTraavoidtoll().get(0).getPath().get(0).toString());
            move.setStatus("moving");
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
            NaverMapsAPIDTO.ResponseDTO resp = naverMapService().getDirections(
                    moveEnded.getStartLocX().toString() + "," + moveEnded.getStartLocY(),
                    moveEnded.getGoalLocX() + "," + moveEnded.getGoalLocY()
            );
            move.setDistance(resp.getRoute().getTraavoidtoll().get(0).getSummary().getDistance());
            move.setDuration(resp.getRoute().getTraavoidtoll().get(0).getSummary().getDuration());
            move.setPath(resp.getRoute().getTraavoidtoll().get(0).getPath().get(0).toString());
            move.setStatus("end");
            repository().save(move);
         });
        moveEnded.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
