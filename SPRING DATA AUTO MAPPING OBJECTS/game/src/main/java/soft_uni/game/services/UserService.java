package soft_uni.game.services;

import soft_uni.game.domain.dtos.GameInfoDto;
import soft_uni.game.domain.dtos.UserLoginDto;
import soft_uni.game.domain.dtos.UserRegisterDto;
import soft_uni.game.domain.entities.Game;


public interface UserService {

    String register(UserRegisterDto userDto);

    String login(UserLoginDto userLoginDto);

    String logout();
}
