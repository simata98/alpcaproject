package alpcaproject.domain;

import alpcaproject.MoveApplication;
import alpcaproject.domain.MoveCancelled;
import alpcaproject.domain.MoveEnded;
import alpcaproject.domain.MoveStarted;
import alpcaproject.domain.MoveUpdated;
import java.time.LocalDate;
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
        MoveStarted moveStarted = new MoveStarted(this);
        moveStarted.publishAfterCommit();

        MoveCancelled moveCancelled = new MoveCancelled(this);
        moveCancelled.publishAfterCommit();

        MoveEnded moveEnded = new MoveEnded(this);
        moveEnded.publishAfterCommit();

        MoveUpdated moveUpdated = new MoveUpdated(this);
        moveUpdated.publishAfterCommit();
    }

    public static MoveRepository repository() {
        MoveRepository moveRepository = MoveApplication.applicationContext.getBean(
            MoveRepository.class
        );
        return moveRepository;
    }

    //<<< Clean Arch / Port Method
    public static void updateGoalLoc(LocRegistered locRegistered) {
        //implement business logic here:

        /** Example 1:  new item 
        Move move = new Move();
        repository().save(move);

        */

        /** Example 2:  finding and process
        
        repository().findById(locRegistered.get???()).ifPresent(move->{
            
            move // do something
            repository().save(move);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void removeGoalLoc(LocRemoved locRemoved) {
        //implement business logic here:

        /** Example 1:  new item 
        Move move = new Move();
        repository().save(move);

        */

        /** Example 2:  finding and process
        
        repository().findById(locRemoved.get???()).ifPresent(move->{
            
            move // do something
            repository().save(move);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void startMove(MoveStarted moveStarted) {
        //implement business logic here:

        /** Example 1:  new item 
        Move move = new Move();
        repository().save(move);

        */

        /** Example 2:  finding and process
        
        repository().findById(moveStarted.get???()).ifPresent(move->{
            
            move // do something
            repository().save(move);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void updateMove(MoveUpdated moveUpdated) {
        //implement business logic here:

        /** Example 1:  new item 
        Move move = new Move();
        repository().save(move);

        */

        /** Example 2:  finding and process
        
        repository().findById(moveUpdated.get???()).ifPresent(move->{
            
            move // do something
            repository().save(move);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void cancelMove(MoveCancelled moveCancelled) {
        //implement business logic here:

        /** Example 1:  new item 
        Move move = new Move();
        repository().save(move);

        */

        /** Example 2:  finding and process
        
        repository().findById(moveCancelled.get???()).ifPresent(move->{
            
            move // do something
            repository().save(move);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void endMove(MoveEnded moveEnded) {
        //implement business logic here:

        /** Example 1:  new item 
        Move move = new Move();
        repository().save(move);

        */

        /** Example 2:  finding and process
        
        repository().findById(moveEnded.get???()).ifPresent(move->{
            
            move // do something
            repository().save(move);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
