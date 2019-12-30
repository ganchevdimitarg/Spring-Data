package soft_unit.springdataintro.services;

import java.io.IOException;
import java.util.List;

public interface AuthorService {

    void seedAuthor() throws IOException;

    List<String> findAllByName();
}
