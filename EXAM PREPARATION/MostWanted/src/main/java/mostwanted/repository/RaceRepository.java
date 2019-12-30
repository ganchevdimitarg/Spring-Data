package mostwanted.repository;

import mostwanted.domain.entities.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceRepository extends JpaRepository<Race,Integer> {
    @Query("select r.district.name from Race r join r.district where r.district.name = :name")
    Race findByDistrictName(String name);
}
