package alpcaproject.domain;

import alpcaproject.AlarmApplication;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Alarm_table")
@Data
//<<< DDD / Aggregate Root
public class Alarm {

    @Id
    private String msgId;

    private String customerId;

    private String familyId;

    private String content;

    private String moveStatus;

    private String moveId;

    public static AlarmRepository repository() {
        AlarmRepository alarmRepository = AlarmApplication.applicationContext.getBean(
            AlarmRepository.class
        );
        return alarmRepository;
    }

    //<<< Clean Arch / Port Method
    public static void sendAlarmMsg(MoveCancelled moveCancelled) {
        //implement business logic here:

        /** Example 1:  new item 
        Alarm alarm = new Alarm();
        repository().save(alarm);

        */

        /** Example 2:  finding and process
        
        repository().findById(moveCancelled.get???()).ifPresent(alarm->{
            
            alarm // do something
            repository().save(alarm);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void sendAlarmMsg(MoveUpdated moveUpdated) {
        //implement business logic here:

        /** Example 1:  new item 
        Alarm alarm = new Alarm();
        repository().save(alarm);

        */

        /** Example 2:  finding and process
        
        repository().findById(moveUpdated.get???()).ifPresent(alarm->{
            
            alarm // do something
            repository().save(alarm);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void sendAlarmMsg(MoveEnded moveEnded) {
        //implement business logic here:

        /** Example 1:  new item 
        Alarm alarm = new Alarm();
        repository().save(alarm);

        */

        /** Example 2:  finding and process
        
        repository().findById(moveEnded.get???()).ifPresent(alarm->{
            
            alarm // do something
            repository().save(alarm);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void sendAlarmMsg(MoveStarted moveStarted) {
        //implement business logic here:

        /** Example 1:  new item 
        Alarm alarm = new Alarm();
        repository().save(alarm);

        */

        /** Example 2:  finding and process
        
        repository().findById(moveStarted.get???()).ifPresent(alarm->{
            
            alarm // do something
            repository().save(alarm);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
