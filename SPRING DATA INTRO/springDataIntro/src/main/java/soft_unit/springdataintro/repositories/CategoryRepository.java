package soft_unit.springdataintro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soft_unit.springdataintro.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
