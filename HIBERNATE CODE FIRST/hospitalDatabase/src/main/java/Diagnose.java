import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "diagnoses")
public class Diagnose extends BaseEntity {

    @Column(length = 20)
    @NotNull
    private String name;

    @Column(length = 50)
    @NotNull
    private String comment;

    @ManyToOne
    @JoinColumn(name = "visitation_id", referencedColumnName = "id")
    private Visitation visitation;

    @OneToMany(mappedBy = "diagnose", targetEntity = Medicament.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Medicament> medicament;

    public Diagnose() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.isEmpty() || name.length() > 20) {
            throw new IllegalArgumentException("The first name cannot be empty or more than 20 chars!");
        }
        this.name = name;
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

    public Visitation getVisitation() {
        return visitation;
    }

    public void setVisitation(Visitation visitation) {
        this.visitation = visitation;
    }

    public Set<Medicament> getMedicament() {
        return medicament;
    }

    public void setMedicament(Set<Medicament> medicament) {
        this.medicament = medicament;
    }
}
