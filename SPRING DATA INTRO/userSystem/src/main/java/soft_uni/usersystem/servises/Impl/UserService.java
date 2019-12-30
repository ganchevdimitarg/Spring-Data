package soft_uni.usersystem.servises.Impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface UserService {

    void seedUser();

    List<String> findAllUserByEmail() throws IOException;

    String findAllUserNotLoggedAfterDate() throws IOException, ParseException;
}
