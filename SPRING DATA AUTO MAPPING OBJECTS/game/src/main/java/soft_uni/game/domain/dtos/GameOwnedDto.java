package soft_uni.game.domain.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class GameOwnedDto {
    private String title;

    public GameOwnedDto() {
    }

    public GameOwnedDto(String title) {
        this.title = title;
    }
}
