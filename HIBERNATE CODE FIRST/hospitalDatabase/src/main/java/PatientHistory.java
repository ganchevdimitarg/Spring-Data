import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "patien_history")
public class PatientHistory extends BaseEntity {

    @OneToMany(mappedBy = "patientHistory", targetEntity = Patient.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Patient> patients;

    public PatientHistory() {
    }

}
