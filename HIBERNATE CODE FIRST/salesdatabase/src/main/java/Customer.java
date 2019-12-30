import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {

    private String name;
    private String email;
    @Column(name = "credit_card_number")
    private String creditCardNumber;
    @OneToMany(mappedBy = "customer", targetEntity = Sale.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Sale> sales;

    public Customer() {
        this.sales = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }
}
