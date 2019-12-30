import javax.persistence.*;

@Entity
@Table(name = "billing_details")
@DiscriminatorColumn(name = "type")
public class BillingDetail extends BasicEntity {
    private int number;
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    public BillingDetail() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
