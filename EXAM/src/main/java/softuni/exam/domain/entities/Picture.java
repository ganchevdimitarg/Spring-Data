package softuni.exam.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity{

    @Column(nullable = false)
    @NotNull
    private String url;

    @OneToMany(mappedBy = "picture", targetEntity = Team.class)
    private List<Team> teams;

    @OneToMany(mappedBy = "picture", targetEntity = Player.class)
    private List<Player> players;

    public Picture() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
