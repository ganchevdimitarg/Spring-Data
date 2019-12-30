package soft_uni.game.domain.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter @Getter
public class GameSetDto {
    private Integer id;
    private BigDecimal price;
    private Double size;

    public GameSetDto() {
    }

}
