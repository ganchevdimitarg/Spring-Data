import entities.Address;
import entities.Employee;
import entities.Project;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Engine implements Runnable {

    private EntityManager entityManager;
    private BufferedReader reader;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        //02
//        this.removeObject();
        //04
//        this.employeesSalary();
        //05
//        this.employeesDepartment();
        //07
//        this.addressesEmployeeCount();
        //09
//        this.findLatest10Projects();
        //10
//        this.increaseSalaries();
        //13
//        this.employeesMaximumSalaries();
//        try {
//            03
//            this.containsEmployee();
//            06
//            this.addingAddressUpdatingEmployee();
//            08
//            this.getEmployeeProject();
            //11
//            this.removeTowns();
            //12
//            this.findEmployeesFirstName();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void removeObject() {
        this.entityManager.getTransaction().begin();

        Query query = this.entityManager.createQuery("UPDATE Town SET name = LOWER(name) WHERE LENGTH(name) < 5");
        query.executeUpdate();
        System.out.println();
        this.entityManager.getTransaction().commit();
    }

    private void containsEmployee() throws IOException {
        String townName = reader.readLine();

        this.entityManager.getTransaction().begin();
        try {

            Employee employee = this.entityManager
                    .createQuery("FROM Employee WHERE CONCAT_WS(' ', firstName, lastName) = :name", Employee.class)
                    .setParameter("name", townName).getSingleResult();
            System.out.println("Yes");
        } catch (Exception e) {
            System.out.println("No");
        }

        this.entityManager.getTransaction().commit();
    }

    private void employeesSalary() {

        this.entityManager.getTransaction().begin();
        List<Employee> employee = this.entityManager.createQuery("FROM Employee WHERE salary > 50000", Employee.class).getResultList();

        employee.forEach(e -> System.out.println(e.getFirstName()));
        this.entityManager.getTransaction().commit();
    }

    private void employeesDepartment() {

        this.entityManager.getTransaction().begin();
        List<Employee> employee = this.entityManager.createQuery("FROM Employee WHERE department.id = 6 ORDER BY salary, id", Employee.class).getResultList();

        employee.forEach(e -> System.out.printf("%s %s from %s - %s%n",
                e.getFirstName(), e.getLastName(), e.getDepartment().getName(), e.getSalary()));

        this.entityManager.getTransaction().commit();
    }

    private void addingAddressUpdatingEmployee() throws IOException {
        this.entityManager.getTransaction().begin();

        Town town = this.entityManager.createQuery("FROM Town WHERE id = 32", Town.class).getSingleResult();

        Address address = new Address();
        address.setText("Vitoshka 15");
        address.setTown(town);

        this.entityManager.persist(address);
        this.entityManager.getTransaction().commit();
        this.entityManager.getTransaction().begin();
        String lastName = this.reader.readLine();

        Employee employee = this.entityManager.createQuery("FROM Employee WHERE lastName =: lastName", Employee.class)
                .setParameter("lastName", lastName).getSingleResult();

        employee.setAddress(address);

        this.entityManager.flush();
        this.entityManager.getTransaction().commit();

    }

    private void addressesEmployeeCount() {
        this.entityManager.getTransaction().begin();

        List<Address> employees = this.entityManager.createQuery("FROM Address ORDER BY size(employees) DESC, town.id", Address.class)
                .setMaxResults(10)
                .getResultList();

        employees.forEach(e -> System.out.printf("%s, %s - %s%n", e.getText(), e.getTown().getName(), e.getEmployees().size()));

        this.entityManager.getTransaction().commit();
    }

    private void getEmployeeProject() throws IOException {
        this.entityManager.getTransaction().begin();

        int inputId = Integer.parseInt(reader.readLine());

        Employee employee = this.entityManager.createQuery("FROM Employee WHERE id =:inputId", Employee.class)
                .setParameter("inputId", inputId)
                .getSingleResult();

        System.out.printf("%s %s - %s%n", employee.getFirstName(), employee.getLastName(), employee.getJobTitle());

        List<Object> employeeProjects = this.entityManager.createNativeQuery("SELECT p.name FROM projects AS p JOIN employees_projects ep on p.project_id = ep.project_id JOIN employees e on ep.employee_id = e.employee_id WHERE e.employee_id = ? order by p.name;")
                .setParameter(1, inputId)
                .getResultList();

        for (Object employeeProject : employeeProjects) {
            System.out.printf("      %s%n", employeeProject);
        }


        this.entityManager.getTransaction().commit();
    }

    private void findLatest10Projects() {
        this.entityManager.getTransaction().begin();

        List<Project> project = this.entityManager.createQuery("FROM Project ORDER BY name", Project.class)
                .setMaxResults(10)
                .getResultList();

        project.forEach(e -> System.out.printf("Project name:%s%n     Project Description:%s%n     Project Start Date:%s%n     Project End Date:%s%n",
                e.getName(), e.getDescription(), e.getStartDate(), e.getEndDate()));

        this.entityManager.getTransaction().commit();
    }

    private void increaseSalaries() {
        this.entityManager.getTransaction().begin();

        Query query = this.entityManager.createQuery("UPDATE Employee SET salary = salary * 1.12 WHERE department.id IN (1, 2, 4, 11)");
        query.executeUpdate();

        List<Employee> employee = this.entityManager.createQuery("FROM Employee WHERE department.id IN (1, 2, 4, 11)", Employee.class).getResultList();
        employee.forEach(e -> System.out.printf("%s %s ($%s)%n", e.getFirstName(), e.getLastName(), e.getSalary()));

        this.entityManager.getTransaction().commit();
    }

    private void removeTowns() throws IOException {

        this.entityManager.getTransaction().begin();
        String townName = this.reader.readLine();

        Town town = this.entityManager.createQuery("FROM Town WHERE name =: townName",Town.class)
                .setParameter("townName", townName)
                .getSingleResult();

        int townId = town.getId();

        this.entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=0;").executeUpdate();
        Query query = this.entityManager.createNativeQuery("DELETE FROM addresses WHERE town_id = ?;").setParameter(1, townId);
        int countsOfDeletedAddresses = query.executeUpdate();
        this.entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=1;").executeUpdate();


        System.out.printf("%d address in %s deleted", countsOfDeletedAddresses, townName);

        this.entityManager.getTransaction().commit();
    }

    private void findEmployeesFirstName() throws IOException {
        this.entityManager.getTransaction().begin();
        char[] patterInput = reader.readLine().toCharArray();

        String patter = "" + patterInput[0] + Character.toLowerCase(patterInput[1]) + "%";
        //нещо не работи
//        List<Employee> employees = this.entityManager.createQuery("FROM Employee WHERE firstName LIKE :patter", Employee.class)
//                .setParameter("patter", patter).getResultList();

        List<Object[]> employees = this.entityManager.createNativeQuery("SELECT first_name,last_name,job_title,salary FROM employees WHERE first_name LIKE ?;")
                .setParameter(1, patter).getResultList();

        for (Object[] employee : employees) {
            System.out.printf("%s %s - %s - ($%s)%n", employee[0], employee[1], employee[2], employee[3]);
        }

        this.entityManager.getTransaction().commit();
    }

    private void employeesMaximumSalaries() {
        this.entityManager.getTransaction().begin();

        List<Object[]> objects = this.entityManager.createNativeQuery("SELECT d.name, MAX(e.salary) AS ma FROM departments AS d JOIN employees e on d.department_id = e.department_id  GROUP BY d.name HAVING ma NOT BETWEEN 30000 AND 70000;").getResultList();

        for (Object[] object : objects) {
            System.out.printf("%s - %s%n", object[0], object[1]);
        }

        this.entityManager.getTransaction().commit();
    }
}
