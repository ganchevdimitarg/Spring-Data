package soft_uni.game.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soft_uni.game.domain.dtos.GameInfoDto;
import soft_uni.game.domain.dtos.UserLoginDto;
import soft_uni.game.domain.dtos.UserRegisterDto;
import soft_uni.game.domain.entities.Game;
import soft_uni.game.domain.entities.Role;
import soft_uni.game.domain.entities.User;
import soft_uni.game.repositories.UserRepository;
import soft_uni.game.services.UserService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private ModelMapper modelMapper;
    private String loggedInUser;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();
        this.loggedInUser = "";
    }

    @Override
    public String register(UserRegisterDto userRegisterDto) {
        StringBuilder sb = new StringBuilder();

        User user = this.modelMapper.map(userRegisterDto, User.class);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        User userInDatabase = this.userRepository.findByEmail(user.getEmail()).orElse(null);

        if (userInDatabase != null) {
            return sb.append("User is already registered!").toString();
        }

        if (violations.size() > 0) {
            for (ConstraintViolation<User> violation : violations) {
                sb.append(violation.getMessage());
            }
        } else {
            if (this.userRepository.count() == 0) {
                user.setRole(Role.ADMIN);
            } else {
                user.setRole(Role.USER);
            }
            sb.append(String.format("%s was registered", user.getFullname()));
            this.userRepository.saveAndFlush(user);
        }

        return sb.toString();
    }

    @Override
    public String login(UserLoginDto userLoginDto) {
        StringBuilder sb = new StringBuilder();

        if (!this.loggedInUser.isEmpty()) {
            return sb.append("User is already logged in").toString();
        }

        User user = this.userRepository.findByEmail(userLoginDto.getEmail()).orElse(null);

        if (user == null) {
            return sb.append("Incorrect email").toString();
        } else {
            if (!user.getPassword().equals(userLoginDto.getPassword())) {
                return sb.append("Incorrect password").toString();
            }
        }

        this.loggedInUser = user.getEmail();
        sb.append(String.format("Successfully logged in %s", user.getFullname()));


        return sb.toString();
    }

    @Override
    public String logout() {
        StringBuilder sb = new StringBuilder();

        if (this.loggedInUser.isEmpty()) {
            sb.append("Cannot log out. No user was logged in.");
        } else {
            User user = this.userRepository.findByEmail(this.loggedInUser).orElse(null);
            sb.append(String.format("User %s successfully logged out", user.getFullname()));
            this.loggedInUser = "";
        }

        return sb.toString();
    }
}
