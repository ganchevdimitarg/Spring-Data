import com.sun.istack.NotNull;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "patients")
public class Patient extends BaseEntity{

    @Column(name = "fist_name", length = 12)
    @NotNull
    private String firstName;

    @Column(name = "last_name", length = 12)
    @NotNull
    private String lastName;

    @Column(length = 20)
    @NotNull
    private String address;

    @Column(length = 20)
    @NotNull
    private String email;

    @NotNull
    private LocalDate birthDate;

    @Lob
    @NotNull
    private byte[] picture;

    @Column(name = "is_has_insurance")
    @NotNull
    private boolean isHasInsurance;

    @OneToMany(mappedBy = "patient", targetEntity = Visitation.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Visitation> visitation;

    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_history_id", referencedColumnName = "id")
    private PatientHistory patientHistory;

    public Patient() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName.isEmpty() || firstName.matches("[^a-zA-Z]+") || firstName.length() > 12){
            throw new IllegalArgumentException("The first name cannot be empty, cannot be with symbols or more than 12 chars!");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName.isEmpty() || lastName.matches("[^a-zA-Z]+") || lastName.length() > 12){
            throw new IllegalArgumentException("The name cannot be empty, cannot be with symbols or more than 12 chars!");
        }
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address.isEmpty() || address.length() > 20){
            throw new IllegalArgumentException("The name cannot be empty or more than 20 chars!");
        }
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (!email.matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}") || email.length() > 20){
            throw new IllegalArgumentException("Invalid email or more than 20 chars!");
        }
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        if (!birthDate.toString().matches("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))")){
            throw new IllegalArgumentException("The date of birth must be in format: yyyy-mm-dd");
        }
        this.birthDate = birthDate;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public boolean isHasInsurance() {
        return isHasInsurance;
    }

    public void setHasInsurance(boolean hasInsurance) {
        isHasInsurance = hasInsurance;
    }

    public PatientHistory getPatientHistory() {
        return patientHistory;
    }

    public Set<Visitation> getVisitation() {
        return visitation;
    }

    public void setVisitation(Set<Visitation> visitation) {
        this.visitation = visitation;
    }

    public void setPatientHistory(PatientHistory patientHistory) {
        this.patientHistory = patientHistory;
    }
}
