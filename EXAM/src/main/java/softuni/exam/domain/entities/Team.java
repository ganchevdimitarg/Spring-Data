package softuni.exam.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "teams")
public class Team extends BaseEntity{

    @Column(nullable = false)
    @NotNull
    @Size(min = 3, max = 20)
    private String name;

    @ManyToOne
    @JoinColumn(name = "picture_id", referencedColumnName = "id", nullable = false)
    @NotNull
    private Picture picture;

    @OneToMany(mappedBy = "team")
    private List<Player> players;

    public Team() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
