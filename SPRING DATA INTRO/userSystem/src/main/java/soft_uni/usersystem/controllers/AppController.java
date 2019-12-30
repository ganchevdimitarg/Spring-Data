package soft_uni.usersystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import soft_uni.usersystem.servises.Impl.UserService;

@Controller
public class AppController implements CommandLineRunner {

    private final UserService userService;

    @Autowired
    public AppController(UserService userService) {
        this.userService = userService;

    }

    @Override
    public void run(String... args) throws Exception {

        this.userService.seedUser();

//        this.userService.findAllUserByEmail().forEach(System.out::println);

        System.out.println(this.userService.findAllUserNotLoggedAfterDate());
    }
}
