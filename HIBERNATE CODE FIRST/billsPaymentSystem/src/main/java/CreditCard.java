import javax.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorValue(value = "Cradit Card")
public class CreditCard extends BillingDetail {
    @Column(name = "card_type")
    private String cardType;
    @Column(name = "expiratio_month")
    private String expiratioMonth;
    @Column(name = "expiratio_year")
    private String expiratioYear;

    public CreditCard() {
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getExpiratioMonth() {
        return expiratioMonth;
    }

    public void setExpiratioMonth(String expiratioMonth) {
        this.expiratioMonth = expiratioMonth;
    }

    public String getExpiratioYear() {
        return expiratioYear;
    }

    public void setExpiratioYear(String expiratioYear) {
        this.expiratioYear = expiratioYear;
    }
}
