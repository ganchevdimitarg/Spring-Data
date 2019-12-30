import javax.persistence.EntityManager;

public class Engine implements Runnable {
    private EntityManager entityManager;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void run() {
        Student student = new Student();
        Teacher teacher = new Teacher();
        Course course = new Course();
    }
}
