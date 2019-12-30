package soft_uni.game.domain.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter @Getter
public class GameAddDto {

    private String title;
    private String trailer;
    private String imageThumbnail;
    private Double size;
    private BigDecimal price;
    private String description;
    private LocalDate releaseDate;

    public GameAddDto() {
    }

    public GameAddDto(String title, String trailer, String imageThumbnail, Double size, BigDecimal price, String description, LocalDate releaseDate) {
        this.title = title;
        this.trailer = trailer;
        this.imageThumbnail = imageThumbnail;
        this.size = size;
        this.price = price;
        this.description = description;
        this.releaseDate = releaseDate;
    }
}
