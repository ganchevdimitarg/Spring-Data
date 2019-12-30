package softuni.exam.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "players")
public class Player extends BaseEntity {

    @Column(name =  "first_name", nullable = false)
    @NotNull
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotNull
    @Size(min = 3, max = 15)
    private String lastName;

    @Column(nullable = false)
    @NotNull
    @Min(value = 1)
    @Max(value = 99)
    private Integer number;

    @Column(nullable = false)
    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private Position position;

    @ManyToOne
    @JoinColumn(name = "picture_id", referencedColumnName = "id", nullable = false)
    private Picture picture;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id", nullable = false)
    private Team team;

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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
