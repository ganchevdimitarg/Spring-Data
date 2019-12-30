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

//        this.bookService.findAllBookTitle().forEach(System.out::println);
//        this.bookService.findAllAuthor().forEach(System.out::println);
//        this.bookService.findAllGeorgesBooks().forEach(System.out::println);

//        this.bookService.findAuthorByBooks().forEach(System.out::println);
    }
}
