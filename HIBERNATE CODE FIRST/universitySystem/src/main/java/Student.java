import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "student")
public class Student extends BaseEntity {
    @Column(name = "average_grade")
    private double averageGrade;
    private int attendance;
    @ManyToMany(mappedBy = "student", targetEntity = Course.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Course> courses;
}
