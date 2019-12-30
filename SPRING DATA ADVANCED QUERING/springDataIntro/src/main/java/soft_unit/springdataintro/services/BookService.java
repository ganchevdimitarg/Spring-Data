package soft_unit.springdataintro.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface BookService {

    void seedBook() throws IOException;

    List<String> findAllTitle();

    List<String> findAllGoldEdition();

    List<String> findAllByPrice();

    List<String> findAllByReleasedDate();

    List<String> findAllReleaseDateBefore() throws ParseException;

    List<String> findAllByTitle();

    List<String> findAllByAuthorLastName();

    String findAllByTitleSize();

    List<String> findAllByCountCopies();

    String findAllByTitleText();

    void IncreaseBooksCopies(String n, Integer m) throws ParseException;

    Integer OnlyOriginalCopies() throws ParseException;

    Integer DeleteBooks();
    Integer CountOfDeleteBooks(Integer number);
}
