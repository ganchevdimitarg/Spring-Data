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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private Scanner scanner;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, FileUtil fileUtil, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.fileUtil = fileUtil;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.scanner = new Scanner(System.in);
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

    //1. Books Titles by Age Restriction
    @Override
    public List<String> findAllTitle() {
        Scanner scanner = new Scanner(System.in);
        AgeRestriction pattern = AgeRestriction.valueOf(scanner.nextLine().toUpperCase());

        return this.bookRepository.findAllByAgeRestrictionLike(pattern)
                .stream()
                .map(b -> String.format("%s", b.getTitle()))
                .collect(Collectors.toList());
    }

    //2. Golden Books
    @Override
    public List<String> findAllGoldEdition() {

        return this.bookRepository.findAllByCopiesIsLessThan(5000)
                .stream()
                .map(c -> String.format("%s", c.getTitle()))
                .collect(Collectors.toList());

    }

    //3. Books by Price
    @Override
    public List<String> findAllByPrice() {

        return this.bookRepository.findAllByPriceIsLessThanOrPriceGreaterThan(new BigDecimal(5), new BigDecimal(40))
                .stream()
                .map(b -> String.format("%s - $%s", b.getTitle(), b.getPrice()))
                .collect(Collectors.toList());

    }

    //4. Not Released Books
    @Override
    public List<String> findAllByReleasedDate() {
        String year = this.scanner.nextLine();
        String befor = year + "-01-01";
        String after = year + "-12-31";
        LocalDate dateBefor = LocalDate.parse(befor);
        LocalDate dateAfter = LocalDate.parse(after);

        return this.bookRepository.findAllByReleaseDateBeforeOrReleaseDateAfter(dateBefor, dateAfter)
                .stream()
                .map(b -> String.format("%s", b.getTitle()))
                .collect(Collectors.toList());

    }

    //5. Books Released Before Date
    @Override
    public List<String> findAllReleaseDateBefore() throws ParseException {
        String[] tokens = this.scanner.nextLine().split("-");
        String date = String.format("%s-%s-%s", tokens[2], tokens[1], tokens[0]);

        LocalDate localDate = LocalDate.parse(date);

        return this.bookRepository.findAllByReleaseDateBefore(localDate)
                .stream()
                .map(b -> String.format("%s %s $%s", b.getTitle(), b.getEditionType(), b.getPrice()))
                .collect(Collectors.toList());
    }

    //7. Books Search
    @Override
    public List<String> findAllByTitle() {
        String pattern = this.scanner.nextLine();

        return this.bookRepository.findAllByTitleContains(pattern)
                .stream()
                .map(b -> String.format("%s", b.getTitle()))
                .collect(Collectors.toList());
    }

    //8. Book Titles Search
    @Override
    public List<String> findAllByAuthorLastName() {
        String chars = this.scanner.nextLine() + "%";

        return this.bookRepository.findAllByAuthorLastNameLike(chars)
                .stream()
                .map(b -> String.format("%s (%s %s)", b.getTitle(), b.getAuthor().getFirstName(), b.getAuthor().getLastName()))
                .collect(Collectors.toList());
    }

    //9. Count Books
    @Override
    public String findAllByTitleSize() {
        Integer number = Integer.parseInt(this.scanner.nextLine());

        return String.format("There are %d books with longer title than %d symbols", this.bookRepository.findAllByTitleLength(number), number);
    }

    //10. Total Book Copies
    @Override
    public List<String> findAllByCountCopies() {

        return this.bookRepository.findAllByCountCopies()
                .stream()
                .map(m -> String.format("%s %s - %s", m.get(0), m.get(1), m.get(2)))
                .collect(Collectors.toList());

    }

    //11. Reduced Book
    @Override
    public String findAllByTitleText() {
        String title = "%" + this.scanner.nextLine() + "%";

        Book book = this.bookRepository.findBookByTitleLike(title);

        return String.format("%s %s %s %.2f", book.getTitle(), book.getEditionType().name(), book.getAgeRestriction().name(), book.getPrice());

    }

    //12. Increase Book Copies
    @Override
    public void IncreaseBooksCopies(String n, Integer m) throws ParseException {
        String[] dateInput = n.split("\\s+");

        int year = Integer.parseInt(dateInput[2]);
        int month = convertDateToInt(dateInput[1]);
        int day = Integer.parseInt(dateInput[0]);

        LocalDate date = LocalDate.of(year, month, day);

        int numberOfCopies = m;

        this.bookRepository.IncreaseBooksCopies(date, numberOfCopies);
    }

    @Override
    public Integer OnlyOriginalCopies() throws ParseException {
        String date = this.scanner.nextLine();
        int number = Integer.parseInt(this.scanner.nextLine());

        String[] dateInput = date.split("\\s+");

        int year = Integer.parseInt(dateInput[2]);
        int month = convertDateToInt(dateInput[1]);
        int day = Integer.parseInt(dateInput[0]);

        LocalDate d = LocalDate.of(year, month, day);

        this.IncreaseBooksCopies(date, number);

        return this.bookRepository.OnlyOriginalCopies(d) * number;
    }

    //13. Remove Books
    @Override
    public Integer DeleteBooks() {
        Integer number = Integer.parseInt(this.scanner.nextLine());
        int deleteBooks = this.bookRepository.CountOfDeleteBooks(number);
        this.bookRepository.DeleteBooks(number);

        return deleteBooks;
    }

    @Override
    public Integer CountOfDeleteBooks(Integer number) {
        return this.bookRepository.CountOfDeleteBooks(number);
    }




    private static int convertDateToInt(String date) throws ParseException {
        DateFormat dateString = new SimpleDateFormat("MMM", Locale.ENGLISH);
        DateFormat dataNumber = new SimpleDateFormat("M");

        return Integer.parseInt(dataNumber.format(dateString.parse(date)));
    }

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

        for (int i = 0; i < index; i++) {
            categories.add(randomCategory());
        }

        return categories;
    }
}
