package soft_unit.springdataintro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import soft_unit.springdataintro.entities.AgeRestriction;
import soft_unit.springdataintro.entities.Author;
import soft_unit.springdataintro.entities.Book;

import javax.print.attribute.standard.Copies;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    //1. Books Titles by Age Restriction
    List<Book> findAllByAgeRestrictionLike(AgeRestriction pattern);

    //2. Golden Books
    List<Book> findAllByCopiesIsLessThan(Integer copies);

    //3. Books by Price
    List<Book> findAllByPriceIsLessThanOrPriceGreaterThan(BigDecimal lower, BigDecimal higher);

    //4. Not Released Books
    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate dateBefore, LocalDate dateAfter);

    //5. Books Released Before Date
    List<Book> findAllByReleaseDateBefore(LocalDate date);

    //7. Books Search
    List<Book> findAllByTitleContains(String chars);

    //8. Book Titles Search
    @Query(value = "select b from Book as b join b.author as a where a.lastName like :param")
    List<Book> findAllByAuthorLastNameLike(String param);

    //9. Count Books
    @Query(value = "select count(b.id) from Book b where length(b.title) > :param")
    Integer findAllByTitleLength(Integer param);

    //10. Total Book Copies
    @Query(value = "select a.firstName, a.lastName, sum(b.copies) as s from Book b join b.author a group by b.author order by s desc")
    List<List<String>> findAllByCountCopies();

    //11. Reduced Book
    Book findBookByTitleLike(String title);

    //12. Increase Book Copies
    @Transactional
    @Modifying
    @Query(value = "update Book b set b.copies = b.copies + :paramNumber where b.releaseDate > :paramDate")
    void IncreaseBooksCopies(@Param(value = "paramDate") LocalDate date, @Param(value = "paramNumber") Integer number);

    @Query(value = "select count(b.copies) from Book b where b.releaseDate > :paramDate ")
    Integer OnlyOriginalCopies(@Param(value = "paramDate") LocalDate date);

    //13. Remove Books
    @Transactional
    @Modifying
    @Query(value = "delete from Book b where b.copies < :param")
    Integer DeleteBooks(Integer param);
    @Query(value = "select count(b.id) from Book b where b.copies < :param")
    Integer CountOfDeleteBooks(Integer param);
}
