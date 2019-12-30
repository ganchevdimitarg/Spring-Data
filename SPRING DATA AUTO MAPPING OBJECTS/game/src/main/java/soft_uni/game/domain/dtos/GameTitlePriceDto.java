package soft_uni.game.domain.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter @Getter
public class GameTitlePriceDto {
    private String title;
    private BigDecimal price;

    public GameTitlePriceDto() {
    }

    public GameTitlePriceDto(String title, BigDecimal price) {
        this.title = title;
        this.price = price;
    }
}
