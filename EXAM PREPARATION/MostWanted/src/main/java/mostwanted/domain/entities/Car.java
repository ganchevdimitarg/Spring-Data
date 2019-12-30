package mostwanted.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "cars")
@NoArgsConstructor
@Setter
@Getter
public class Car extends BaseEntity {
    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private String model;
    private BigDecimal price;
    @Column(name = "year_of_production", nullable = false)
    private Integer yearOfProduction;
    @Column(name = "max_speed")
    private Double maxSpeed;
    @Column(name = "zero_to_sixty")
    private Double zeroToSixty;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "racer_id", referencedColumnName = "id")
    private Racer racer;
    @OneToMany(mappedBy = "car", targetEntity = RaceEntry.class, cascade = CascadeType.ALL)
    private List<RaceEntry> raceEntries;

}
