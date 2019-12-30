package mostwanted.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "racers")
@NoArgsConstructor
@Setter
@Getter
public class Racer extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String name;
    private Integer age;
    private BigDecimal bounty;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "town_id", referencedColumnName = "id")
    private Town homeTown;
    @OneToMany(mappedBy = "racer", targetEntity = Car.class ,cascade = CascadeType.ALL)
    private List<Car> car;
    @OneToMany(mappedBy = "racer", targetEntity = RaceEntry.class,cascade = CascadeType.ALL)
    private List<RaceEntry> raceEntries;
}
