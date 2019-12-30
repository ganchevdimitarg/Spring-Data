package soft_uni.game.domain.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter @Getter
public class GameInfoDto {
    private String title;
    private BigDecimal price;
    private String description;
    private LocalDate releaseDate;

    public GameInfoDto() {
    }

    public GameInfoDto(String title, BigDecimal price, String description, LocalDate releaseDate) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.releaseDate = releaseDate;
    }
}
