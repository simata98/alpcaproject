package alpcaproject.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "Moveview_table")
@Data
public class Moveview {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private String moveId;

    private String customerId;
    private Integer distance;
    private Integer duration;
    private String status;
    private String locId;
}
