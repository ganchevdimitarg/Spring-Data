import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "store_locations")
public class StoreLocation extends BaseEntity {

    @Column(name = "location_name")
    private String locationName;
    @OneToMany(mappedBy = "storeLocation", targetEntity = Sale.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Sale> sales;

    public StoreLocation() {
        this.sales = new HashSet<>();
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }
}
