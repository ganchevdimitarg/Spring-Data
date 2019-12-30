import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "visitations")
public class Visitation extends BaseEntity {

    @NotNull
    private Date date;

    @Column(length = 50)
    @NotNull
    private String comment;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @OneToMany(mappedBy = "visitation", targetEntity = Diagnose.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Diagnose> diagnose;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        if (!date.toString().matches("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))")){
            throw new IllegalArgumentException("The date must be in format: yyyy-mm-dd");
        }
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        if (comment.isEmpty() || comment.length() > 50){
            throw new IllegalArgumentException("The comment cannot be empty or more than 50 chars!");
        }
        this.comment = comment;
    }

    public Set<Diagnose> getDiagnose() {
        return diagnose;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDiagnose(Set<Diagnose> diagnose) {
        this.diagnose = diagnose;
    }
}
