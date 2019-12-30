package soft_uni.usersystem.entities;

import soft_uni.usersystem.entities.validators.EmailValidator;
import soft_uni.usersystem.entities.validators.PasswordValidator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @NotNull
    private String username;

    @NotNull
    @PasswordValidator
    private String password;

    @NotNull
    @EmailValidator
    private String email;

    @Column(name = "registered_on")
    private LocalDate registeredOn;

    @Column(name = "last_time_logged_in")
    private LocalDate lastTimeLogged;

    @Size(min = 1, max = 120)
    private Integer age;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "frist_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Transient
    private String fullName;

    @OneToOne
    @JoinColumn(name = "town_id", referencedColumnName = "id")
    private Town town;

    @ManyToMany(mappedBy = "friends", cascade = CascadeType.ALL)
    private Set<User> users;

    @ManyToMany
    @JoinTable(name = "users_friends", joinColumns = @JoinColumn(name = "friends_id"), inverseJoinColumns = @JoinColumn(name = "users_id"))
    private Set<User> friends;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Album> albums;

    public String getFullName() {
        return fullName = this.firstName + " " + this.lastName;
    }

    public User() {
    }

    public User(@NotNull String username, @NotNull String password, @NotNull String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDate registeredOn) {
        this.registeredOn = registeredOn;
    }

    public LocalDate getLastTimeLogged() {
        return lastTimeLogged;
    }

    public void setLastTimeLogged(LocalDate lastTimeLogged) {
        this.lastTimeLogged = lastTimeLogged;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }
}
