import javax.persistence.EntityManager;
import java.io.BufferedReader;

public class Engine implements Runnable {
    private EntityManager entityManager;
    private BufferedReader reader;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void run() {

    }
}
