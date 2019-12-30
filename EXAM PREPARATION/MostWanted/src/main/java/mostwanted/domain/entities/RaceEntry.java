package mostwanted.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "race_entries")
@NoArgsConstructor
@Setter
@Getter
public class RaceEntry extends BaseEntity{

    @Column(name = "has_Finished")
    private Boolean hasFinished;
    @Column(name = "finish_time")
    private Double finishTime;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "racer_id", referencedColumnName = "id")
    private Racer racer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "race_id", referencedColumnName = "id")
    private Race race;
}
