package soft_uni.game.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "games")
@Setter @Getter
public class Game extends BasicEntity {

    @Column(unique = true)
    @NotNull
    @Pattern(regexp = "([A-Z][a-zA-Z0-9]+)", message = "The title have to start with uppercase letter")
    @Size(min = 3, max = 100)
    private String title;

    @Column(unique = true)
    @Size(min = 11, max = 11)
    private String trailer;

    @Column(name = "image_thumbnail", unique = true)
    @Pattern(regexp = "(http)?(https)?:\\/\\/.+", message = "Invalid protocol")
    private String imageThumbnail;

    @Column(precision = 19, scale = 1)
    @NotNull
    @Min(value = 0)
    private Double size;

    @Column(precision = 19, scale = 2)
    @NotNull
    @Min(value = 0)
    private BigDecimal price;

    @NotNull
    @Size(min = 20, max = 1000)
    private String description;

    @Column(name = "release_date")
    @NotNull
    private LocalDate releaseDate;

    @ManyToMany(mappedBy = "games", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<User> users;

    @ManyToOne()
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    public Game() {
    }

}
