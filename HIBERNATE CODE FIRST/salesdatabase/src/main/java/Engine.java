import javax.persistence.EntityManager;

public class Engine implements Runnable {
    private EntityManager entityManager;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void run() {
        Product product = new Product();
        Customer customer = new Customer();
        StoreLocation storeLocation = new StoreLocation();
        Sale sale = new Sale();
    }
}
