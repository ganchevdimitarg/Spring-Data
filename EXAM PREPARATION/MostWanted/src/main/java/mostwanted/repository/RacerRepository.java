package mostwanted.repository;

import mostwanted.domain.entities.Racer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RacerRepository extends JpaRepository<Racer, Integer> {
    Racer findByName(String name);

    @Query("select r from Racer r join Car c on r.id = c.racer where r.age is not null group by r.name order by count(c.id) desc, r.name")
    List<Racer> findAllCars();
}
