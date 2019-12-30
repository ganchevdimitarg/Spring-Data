import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "medicaments")
public class Medicament extends BaseEntity {

    @Column(length = 20)
    @NotNull
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "diagnose_id", referencedColumnName = "id")
    private Diagnose diagnose;

    public Medicament() {
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

}
