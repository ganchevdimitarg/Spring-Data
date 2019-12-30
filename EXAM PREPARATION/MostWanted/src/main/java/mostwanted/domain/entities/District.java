package mostwanted.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "districts")
@NoArgsConstructor
@Setter
@Getter
public class District  extends  BaseEntity{
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "town_id", referencedColumnName = "id")
    private Town town;
    @OneToMany(mappedBy = "district", targetEntity = Race.class, cascade = CascadeType.ALL)
    private List<Race> races;
}
