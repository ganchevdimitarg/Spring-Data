package soft_unit.springdataintro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import soft_unit.springdataintro.services.AuthorService;
import soft_unit.springdataintro.services.BookService;
import soft_unit.springdataintro.services.CategoryService;

@Controller
public class BookShopController implements CommandLineRunner {

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;


    @Autowired
    public BookShopController(AuthorService authorService, CategoryService categoryService, BookService bookService) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.authorService.seedAuthor();
        this.categoryService.seedCategory();
        this.bookService.seedBook();

        //1. Books Titles by Age Restriction
//        this.bookService.findAllTitle().forEach(System.out::println);

        //2. Golden Books
//        this.bookService.findAllGoldEdition().forEach(System.out::println);

        //3. Books by Price
//        this.bookService.findAllByPrice().forEach(System.out::println);

        //4. Not Released Books
//        this.bookService.findAllByReleasedDate().forEach(System.out::println);

        //5. Books Released Before Date
//        this.bookService.findAllReleaseDateBefore().forEach(System.out::println);

        //6. Authors Search
//        this.authorService.findAllByName().forEach(System.out::println);

        //7. Books Search
//        this.bookService.findAllByTitle().forEach(System.out::println);

        //8. Book Titles Search
//        this.bookService.findAllByAuthorLastName().forEach(System.out::println);

        //9. Count Books
//        System.out.println(this.bookService.findAllByTitleSize());

        //10. Total Book Copies
//        this.bookService.findAllByCountCopies().forEach(System.out::println);

        //11. Reduced Book
//        System.out.println(this.bookService.findAllByTitleText());

        //12. Increase Book Copies
//        System.out.println(this.bookService.OnlyOriginalCopies());

        //13. Remove Books
//        System.out.println(this.bookService.DeleteBooks());
    }
}
