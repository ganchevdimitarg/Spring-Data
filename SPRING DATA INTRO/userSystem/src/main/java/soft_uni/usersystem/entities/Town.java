package soft_uni.usersystem.entities;

import javax.persistence.*;

@Entity
@Table(name = "towns")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public class Town extends BaseEntity {

    private String name;

    private String country;

    @OneToOne(mappedBy = "town", targetEntity = User.class)
    private User user;

    public Town() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
