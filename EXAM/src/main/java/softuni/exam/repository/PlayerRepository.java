package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.Team;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
    List<Player> findAllByTeamOrderById(Team team);

    @Query("select p from Player p where p.salary > 100000 order by p.salary desc")
    List<Player> findSalaryOrderBySalaryDesc();
}
