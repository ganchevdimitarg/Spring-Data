package soft_uni.usersystem.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "current_living_town")
@DiscriminatorValue(value = "current_living_town")
public class CurrentLivingTown extends Town {
    public CurrentLivingTown() {
        super();
    }
}
