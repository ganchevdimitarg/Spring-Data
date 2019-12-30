import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BasicEntity {
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String password;
    @Column(name = "billing_detail")
    private String billingDetail;
    @OneToMany(mappedBy = "owner", targetEntity = BillingDetail.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<BillingDetail> billingDetailSet;

    public User() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBillingDetail() {
        return billingDetail;
    }

    public void setBillingDetail(String billingDetail) {
        this.billingDetail = billingDetail;
    }

    public Set<BillingDetail> getBillingDetailSet() {
        return billingDetailSet;
    }

    public void setBillingDetailSet(Set<BillingDetail> billingDetailSet) {
        this.billingDetailSet = billingDetailSet;
    }
}
