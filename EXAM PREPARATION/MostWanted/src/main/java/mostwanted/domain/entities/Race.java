package mostwanted.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "races")
@NoArgsConstructor
@Setter
@Getter
public class Race extends BaseEntity{

    private Integer laps;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "district_id", referencedColumnName = "id", nullable = false)
    private District district;
    @OneToMany(mappedBy = "race", cascade = CascadeType.ALL)
    private List<RaceEntry> raceEntries;
}
