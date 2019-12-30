package soft_uni.game.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soft_uni.game.domain.entities.Order;

@Repository
public interface OrderRepositiry extends JpaRepository<Order, Integer> {
}
