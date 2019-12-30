package soft_uni.game.services;

import soft_uni.game.domain.dtos.GameAddDto;
import soft_uni.game.domain.dtos.GameTitlePriceDto;

import java.math.BigDecimal;
import java.util.List;

public interface GameService {

    String addGame(GameAddDto gameAddDto);

    void loggedInUser(String email);

    void loggedOutUser();

    String updateGame(BigDecimal paramPrice, Double paramSize, int paramId);

    String deleteGame(Integer id);

    List<String> allGames();

    List<String> allAboutGame(String title);

    List<String> allOwnedGames();
}
