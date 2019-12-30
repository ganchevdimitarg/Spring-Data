package soft_uni.game.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import soft_uni.game.domain.entities.Game;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    Optional<Game> findById(Integer id);

    @Query(value = "delete from Game g where g.id = :param")
    @Transactional
    @Modifying
    void deleteGameById(@Param(value = "param")Integer id);

    @Query(value = "select g from Game g")
    List<Game> findAllGames();

    List<Game> findAllByTitle(String title);
}
