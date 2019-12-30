package soft_uni.usersystem.servises.Impl.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soft_uni.usersystem.entities.User;
import soft_uni.usersystem.repositories.UserRepository;
import soft_uni.usersystem.servises.Impl.UserService;
import soft_uni.usersystem.utils.UtilReader;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UtilReader utilReader;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UtilReader utilReader) {
        this.userRepository = userRepository;
        this.utilReader = utilReader;
    }

    @Override
    public void seedUser() {

        if (this.userRepository.count() != 0){
            return;
        }

        List<User> users = new ArrayList<>();
        User user1 = new User("pesho123", "45621dasASd", "pesho@gmail.com");
        user1.setLastTimeLogged(LocalDate.of(2004,10,12));
        users.add(user1);

        User user2 = new User("vanko1", "45621dasASd", "vanko1@gmail.com");
        user2.setLastTimeLogged(LocalDate.of(2015,7,10));
        users.add(user2);

        User user3 = new User("goshko_n00b", "45621dasASd", "gn00b@gmail.com");
        user3.setLastTimeLogged(LocalDate.of(2004,10,12));
        users.add(user3);

        User user4 = new User("penbo", "45621dasASd", "pen@yahoo.co.uk");
        users.add(user4);

        User user5 = new User("catLady", "45621dasASd", "stepheny.p@yahoo.co.uk");
        users.add(user5);

        for (User user : users) {
            this.userRepository.saveAndFlush(user);
        }


    }

    @Override
    public List<String> findAllUserByEmail() throws IOException {
        String pattern = this.utilReader.lines();

        List<String> users = this.userRepository.findAllByEmailLike("%" + pattern)
                .stream()
                .map(m -> String.format("%s %s", m.getUsername(), m.getEmail()))
                .collect(Collectors.toList());

        if (users.size() == 0){
            String noSuchUser = "No users found with email domain " + pattern;
            users.add(noSuchUser);
            return users;
        }
        return users;
    }

    @Override
    public String findAllUserNotLoggedAfterDate() throws IOException, ParseException {
        String[] date = this.utilReader.lines().split("\\s+");
        int year = Integer.parseInt(date[2]);
        int month = convertDateToInt(date[1]);
        int day = Integer.parseInt(date[0]);

        List<User> users = this.userRepository.findAllByLastTimeLogged(LocalDate.of(year, month, day));

        for (User user : users) {
            user.setDeleted(true);
        }

        for (User user : users) {
            this.userRepository.delete(user);
            this.userRepository.flush();
        }

        return hasDeletedUser(users);
    }

    private String hasDeletedUser(List<User> users) {
        String deleted;

        if (users.size() == 0){
            deleted = "No users have been deleted";
        } else if (users.size() == 1){
            deleted = "1 user has been deleted";
        } else {
            deleted = users.size() + " users have been deleted";
        }

        return deleted;
    }


    private static int convertDateToInt(String date) throws ParseException {
        DateFormat dataNumber = new SimpleDateFormat("M");
        DateFormat dateString = new SimpleDateFormat("MMM", Locale.ENGLISH);

        return Integer.parseInt(dataNumber.format(dateString.parse(date)));
    }
}
