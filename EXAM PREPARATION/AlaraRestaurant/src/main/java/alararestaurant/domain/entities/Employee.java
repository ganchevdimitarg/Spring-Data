package alararestaurant.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity {

    @Column(name = "name", nullable = false)
    @Size(min = 3, max = 30)
    private String name;

    @Column(name = "age", nullable = false)
    @Min(value = 15)
    @Max(value = 80)
    private Integer age;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "position_id", referencedColumnName = "id", nullable = false)
    private Position position;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Order> orders;

    public Employee() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
