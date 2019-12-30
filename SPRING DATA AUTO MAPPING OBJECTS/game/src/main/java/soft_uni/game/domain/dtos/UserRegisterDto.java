package soft_uni.game.domain.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class UserRegisterDto {

    private String email;
    private String password;
    private String confirmPassword;
    private String fullname;

    public UserRegisterDto() {
    }

    public UserRegisterDto(String email, String password, String confirmPassword, String fullname) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.fullname = fullname;
    }
}
