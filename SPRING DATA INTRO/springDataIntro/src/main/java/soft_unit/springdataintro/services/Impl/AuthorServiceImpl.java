package soft_unit.springdataintro.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soft_unit.springdataintro.entities.Author;
import soft_unit.springdataintro.repositories.AuthorRepository;
import soft_unit.springdataintro.services.AuthorService;
import soft_unit.springdataintro.utils.FileUtil;

import java.io.IOException;

import static soft_unit.springdataintro.constants.Constant.AUThORS_FILE_URL;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthor() throws IOException {
        if (this.authorRepository.count() != 0){
            return;
        }
        String[] authors = this.fileUtil.fileText(AUThORS_FILE_URL);

        for (String s : authors) {
            String[] tokens = s.split("\\s+");
            Author author = new Author();
            author.setFirstName(tokens[0]);
            author.setLastName(tokens[1]);

            this.authorRepository.saveAndFlush(author);
        }
    }
}
