package soft_unit.springdataintro.services;

import java.io.IOException;
import java.util.List;

public interface BookService {

    void seedBook() throws IOException;

    List<String> findAllBookTitle();

    List<String> findAllAuthor();

    List<String> findAllGeorgesBooks();

//    List<String> findAuthorByBooks();
}
