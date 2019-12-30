package mostwanted.repository;

import mostwanted.domain.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    Car findByPrice(BigDecimal price);
    Car findById(int id);
}
