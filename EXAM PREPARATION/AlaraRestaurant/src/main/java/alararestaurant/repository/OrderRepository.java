package alararestaurant.repository;

import alararestaurant.domain.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("select o from Order o join Employee e on o.employee = e.id where e.position.name = 'Burger Flipper' order by e.name, o.id")
    List<Order> findAllByFinishesFlippers();
}
