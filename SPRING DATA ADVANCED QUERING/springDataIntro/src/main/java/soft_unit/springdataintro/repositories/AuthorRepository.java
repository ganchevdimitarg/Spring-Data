package soft_unit.springdataintro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import soft_unit.springdataintro.entities.Author;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    //6. Authors Search
    List<Author> findAllByFirstNameEndingWith(String name);


}
