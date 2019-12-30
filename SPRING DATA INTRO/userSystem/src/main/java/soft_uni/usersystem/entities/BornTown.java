package soft_uni.usersystem.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity

@DiscriminatorValue(value = "born_town")
public class BornTown extends Town {
    public BornTown() {
        super();
    }
}
