import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "teacher")
public class Teacher extends BaseEntity {
    private String email;
    @Column(name = "salary_per_hour")
    private BigDecimal salaryPerHour;
    @OneToMany(mappedBy = "teacher", targetEntity = Course.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Course> courses;
}
