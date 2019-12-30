package soft_unit.springdataintro.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soft_unit.springdataintro.entities.*;
import soft_unit.springdataintro.repositories.AuthorRepository;
import soft_unit.springdataintro.repositories.BookRepository;
import soft_unit.springdataintro.repositories.CategoryRepository;
import soft_unit.springdataintro.services.BookService;
import soft_unit.springdataintro.utils.FileUtil;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static soft_unit.springdataintro.constants.Constant.BOOKS_FILE_URL;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final FileUtil fileUtil;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, FileUtil fileUtil, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.fileUtil = fileUtil;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedBook() throws IOException {
        if (this.bookRepository.count() != 0) {
            return;
        }

        String[] bookLineInfo = this.fileUtil.fileText(BOOKS_FILE_URL);

        for (String line : bookLineInfo) {
            String[] tokens = line.split("\\s+");

            Book book = new Book();
            book.setAuthor(randomAuthor());

            EditionType editionType = EditionType.values()[Integer.parseInt(tokens[0])];
            book.setEditionType(editionType);

            LocalDate releaseDate = getLocalDate(tokens[1]);
            book.setReleaseDate(releaseDate);

            book.setCopies(Integer.parseInt(tokens[2]));

            BigDecimal price = new BigDecimal(tokens[3]);
            book.setPrice(price);

            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(tokens[4])];
            book.setAgeRestriction(ageRestriction);

            StringBuilder titleName = new StringBuilder();

            for (int i = 5; i <= tokens.length - 1; i++) {
                titleName.append(tokens[i]).append(" ");
            }

            book.setTitle(titleName.toString().trim());

            book.setCategories(randomCategories());

            this.bookRepository.saveAndFlush(book);
        }
    }

    @Override
    public List<String> findAllBookTitle() {
        LocalDate releaseDate = getLocalDate("31/12/2000");

        return this.bookRepository.findAllByReleaseDateAfter(releaseDate)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllAuthor() {
        LocalDate releaseDate = getLocalDate("01/01/1990");
        return this.bookRepository.findAllByReleaseDateBefore(releaseDate)
                .stream()
                .map(m -> String.format("%s %s",m.getAuthor().getFirstName(), m.getAuthor().getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllGeorgesBooks() {
        Author author = this.authorRepository.getOne(4);
        return this.bookRepository.findBookByAuthorOrderByReleaseDateDescCopiesAsc(author)
                .stream()
                .map(m -> String.format("Title: %s; Release date: %s; Copies: %s", m.getTitle(), m.getReleaseDate(), m.getCopies()))
                .collect(Collectors.toList());
    }

//    @Override
//    public List<String> findAuthorByBooks() {
//        List<Author> author = this.authorRepository.findAll();
//        return this.bookRepository.countAllByAuthor_Books(author)
//                .stream()
//                .map(m -> String.format("%s %s %s",
//                        m.getAuthor().getFirstName(), m.getAuthor().getLastName(), m.getAuthor().getBooks().size()))
//                .collect(Collectors.toList());
//    }

    private LocalDate getLocalDate(String s) {
        return LocalDate.parse(s, DateTimeFormatter.ofPattern("d/M/yyyy"));
    }

    private Author randomAuthor() {
        Random random = new Random();

        int indexId = random.nextInt((int) this.authorRepository.count()) + 1;

        return this.authorRepository.getOne(indexId);
    }

    private Category randomCategory() {
        Random random = new Random();

        int indexId = random.nextInt((int) this.categoryRepository.count()) + 1;

        return this.categoryRepository.getOne(indexId);
    }

    private Set<Category> randomCategories() {
        Set<Category> categories = new LinkedHashSet<>();

        Random random = new Random();
        int index = random.nextInt((int) this.categoryRepository.count()) + 1;

        for (int i = 0; i < index; i++){
            categories.add(randomCategory());
        }

        return categories;
    }
}
