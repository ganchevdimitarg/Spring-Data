import javax.persistence.*;

@Entity

@DiscriminatorValue(value = "Bank Account")
public class BankAccount extends BillingDetail {
    @Column(name = "bank_name")
    private String bankName;
    @Column(name = "swift_code")
    private String swiftCode;

    public BankAccount() {
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }
}
