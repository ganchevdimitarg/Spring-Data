package mostwanted.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "towns")
@NoArgsConstructor
@Setter
@Getter
public class Town extends BaseEntity{

    @Column(nullable = false,unique = true)
    @NotNull
    private String name;
    @OneToMany(mappedBy = "town", targetEntity = District.class, cascade = CascadeType.ALL)
    private List<District> districts;
    @OneToMany(mappedBy = "homeTown", targetEntity = Racer.class, cascade = CascadeType.ALL)
    private List<Racer> racers;
}
