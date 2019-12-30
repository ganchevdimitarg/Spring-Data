package soft_uni.game.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import soft_uni.game.domain.dtos.GameAddDto;
import soft_uni.game.domain.dtos.UserLoginDto;
import soft_uni.game.domain.dtos.UserRegisterDto;
import soft_uni.game.services.GameService;
import soft_uni.game.services.UserService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class AppController implements CommandLineRunner {

    private BufferedReader reader;
    private final UserService userService;
    private final GameService gameService;

    @Autowired
    public AppController(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {

        while (true) {

            String[] tokans = this.reader.readLine().split("\\|");

            switch (tokans[0]) {
                case "RegisterUser":
                    UserRegisterDto userRegisterDto = new UserRegisterDto(tokans[1], tokans[2], tokans[3], tokans[4]);
                    System.out.println(this.userService.register(userRegisterDto));
                    break;
                case "LoginUser":
                    UserLoginDto userLoginDto = new UserLoginDto(tokans[1], tokans[2]);
                    System.out.println(this.userService.login(userLoginDto));
                    this.gameService.loggedInUser(userLoginDto.getEmail());
                    break;
                case "LogoutUser":
                    System.out.println(this.userService.logout());
                    this.gameService.loggedOutUser();
                    break;
                case "AddGame":
                    LocalDate date = LocalDate.parse(tokans[7], DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    GameAddDto gameAddDto = new GameAddDto(tokans[1], tokans[4], tokans[5], Double.parseDouble(tokans[3]),
                            new BigDecimal(tokans[2]), tokans[6], date);
                    System.out.println(this.gameService.addGame(gameAddDto));
                    break;
                case "EditGame":
                    int id = Integer.parseInt(tokans[1]);
                    String[] paramPrice = tokans[2].split("=");
                    String[] paramSize = tokans[3].split("=");
                    System.out.println(this.gameService.updateGame(new BigDecimal(paramPrice[1]), Double.parseDouble(paramSize[1]), id));
                    break;
                case "DeleteGame":
                    System.out.println(this.gameService.deleteGame(Integer.parseInt(tokans[1])));
                    break;
                case "AllGames":
                    this.gameService.allGames().forEach(System.out::println);
                    break;
                case "DetailGame":
                    this.gameService.allAboutGame(tokans[1]).forEach(System.out::println);
                    break;
                case "OwnedGames":
                    this.gameService.allOwnedGames().forEach(System.out::println);
                    break;
            }
        }
    }
}
