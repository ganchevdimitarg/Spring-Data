package soft_uni.game.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Setter @Getter
public class User extends BasicEntity{

    @Column(unique = true)
    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9._]+@[a-zA-Z]+\\.[a-z]{2,4}", message = "Incorrect email.")
    private String email;

    @NotNull
    @Pattern(regexp = "(?=^.{6,}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s)[0-9a-zA-Z!@#$%^&*()]*$", message = "Incorrect password")
    private String password;

    @Column(name = "full_name")
    @NotNull
    private String fullname;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_games", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
                , inverseJoinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"))
    private Set<Game> games;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Order> orders;

    public User() {
    }

}
