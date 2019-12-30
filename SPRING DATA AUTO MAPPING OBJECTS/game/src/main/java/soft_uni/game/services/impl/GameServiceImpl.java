package soft_uni.game.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soft_uni.game.domain.dtos.GameAddDto;
import soft_uni.game.domain.dtos.GameInfoDto;
import soft_uni.game.domain.dtos.GameSetDto;
import soft_uni.game.domain.dtos.GameTitlePriceDto;
import soft_uni.game.domain.entities.Game;
import soft_uni.game.domain.entities.Role;
import soft_uni.game.domain.entities.User;
import soft_uni.game.repositories.GameRepository;
import soft_uni.game.repositories.UserRepository;
import soft_uni.game.services.GameService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private ModelMapper modelMapper;
    private String loggedInUser;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();
    }


    @Override
    public String addGame(GameAddDto gameAddDto) {
        StringBuilder sb = new StringBuilder();

        if (this.loggedInUser.isEmpty()){
            return sb.append("There is not logged user").toString();
        }

        Game game = this.modelMapper.map(gameAddDto, Game.class);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Game>> violations = validator.validate(game);

        if (violations.size() != 0){
            for (ConstraintViolation<Game> violation : violations) {
                sb.append(violation.getMessage());
            }
            return sb.toString();
        }

        User user = this.userRepository.findByEmail(this.loggedInUser).orElse(null);

        if (!user.getRole().equals(Role.ADMIN)){
            return sb.append(String.format("%s is not admin", user.getFullname())).toString();
        }

        Set<Game> games = user.getGames();
        for (Game gameInDB : games) {

            if (gameInDB.getTitle().contains(gameAddDto.getTitle())){
                return sb.append(String.format("Already exist %s", game.getTitle())).toString();
            }
        }

        this.gameRepository.saveAndFlush(game);
        games.add(game);
        user.setGames(games);
        this.userRepository.saveAndFlush(user);
        sb.append(String.format("Added %s", game.getTitle()));

        return sb.toString();
    }

    @Override
    public String updateGame(BigDecimal paramPrice, Double paramSize, int paramId) {
        StringBuilder sb = new StringBuilder();

        String user1 = validations(paramId, sb);
        if (user1 != null) return user1;

        Game game = this.gameRepository.findById(paramId).orElse(null);
        game.setPrice(paramPrice);
        game.setSize(paramSize);
        sb.append(String.format("Edited %s", game.getTitle()));
        this.gameRepository.saveAndFlush(game);
        return sb.toString();
    }

    @Override
    public String deleteGame(Integer id) {
        StringBuilder sb = new StringBuilder();

        String user1 = validations(id, sb);
        if (user1 != null) return user1;

        Game game = this.gameRepository.findById(id).orElse(null);

        this.gameRepository.deleteGameById(id);

        sb.append(String.format("Deleted %s", game.getTitle()));

        return sb.toString();
    }

    @Override
    public List<String> allGames() {

        List<Game> games = this.gameRepository.findAllGames();
        List<GameTitlePriceDto> allGames = new ArrayList<>();

        for (Game game : games) {
            GameTitlePriceDto allGame = this.modelMapper.map(game, GameTitlePriceDto.class);
            allGames.add(allGame);
        }

        return allGames.stream().map(g -> String.format("%s %s", g.getTitle(), g.getPrice())).collect(Collectors.toList());
    }

    @Override
    public List<String> allAboutGame(String title) {

        List<Game> gamesInfo = this.gameRepository.findAllByTitle(title);
        List<GameInfoDto> allGames = new ArrayList<>();

        for (Game game : gamesInfo) {
            GameInfoDto allGame = this.modelMapper.map(game, GameInfoDto.class);
            allGames.add(allGame);
        }

        return allGames.stream().map(g -> String.format("Title: %s\n" +
                        "Price: %s\n" +
                        "Description: %s\n" +
                        "Release date: %s\n", g.getTitle(), g.getPrice(), g.getDescription(), g.getReleaseDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> allOwnedGames() {
        List<String> message = new ArrayList<>();
        if (this.loggedInUser == null) {
            message.add("There is not logged user");
            return message;
        }

        User user = this.userRepository.findByEmail(this.loggedInUser).orElse(null);

        return user.getGames().stream().map(g -> String.format("%s", g.getTitle())).collect(Collectors.toList());

    }


    private String validations(int paramId, StringBuilder sb) {
        if (this.loggedInUser == null){
            return sb.append("There is not logged user").toString();
        }

        if (this.gameRepository.findById(paramId).isEmpty()){
            return sb.append("There is no game with this id").toString();
        }

        User user = this.userRepository.findByEmail(this.loggedInUser).orElse(null);

        if (!user.getRole().equals(Role.ADMIN)){
            return sb.append(String.format("%s is not admin", user.getFullname())).toString();
        }
        return null;
    }

    @Override
    public void loggedInUser(String email) {
        this.loggedInUser = email;
    }

    @Override
    public void loggedOutUser() {
        this.loggedInUser = "";
    }

}
