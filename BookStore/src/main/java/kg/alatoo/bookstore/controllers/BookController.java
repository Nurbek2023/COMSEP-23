package kg.alatoo.bookstore.controllers;

import jakarta.validation.Valid;
import kg.alatoo.bookstore.dto.BookListDto;
import kg.alatoo.bookstore.mappers.BookMapper;
import kg.alatoo.bookstore.services.BookService;
import kg.alatoo.bookstore.entities.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/book/")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;  // Inject BookMapper

//    @GetMapping("/two/{id}")
//    public ResponseEntity<Book> getBook(@PathVariable long id) {
//        Book bookById = bookService.getBookById(id);
//        if (bookById == null) {
//            ResponseEntity.status(HttpStatus.NOT_FOUND);
//        }
//        return ResponseEntity.ok(bookById);
//    }

//    @GetMapping("{id}")//author has null value while authors and publisher has multiple value
//    public Book getBookById(@PathVariable long id) {
//        return bookService.getBookById(id);
//    }

    @GetMapping("{id}")
    public ResponseEntity<BookListDto> getBookById(@PathVariable long id) {
        Book bookById = bookService.getBookById(id);
        if (bookById == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // Map the Book entity to BookListDto
        BookListDto bookListDto = bookMapper.bookToBookListDto(bookById);
        return ResponseEntity.ok(bookListDto);
    }


    @GetMapping
    public List<BookListDto> getBooks( //has proper output
            @RequestParam(required = false) String author
            ) {
        if (author == null) {
            log.debug("Getting all books");
            return bookService.getBooks();
        }
        log.debug("Getting all books of author {}", author);
        return bookService.getBooksByAuthor(author);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody @Validated Book book) {
        Book createdBook = bookService.addBook(book);

        /*return ResponseEntity
                .created(URI.create("/api/v1/book/" + createdBook.getId()))
                .header("foo", "bar")
                .build();*/

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location",
                        "/api/v1/book/"+createdBook.getId().toString())
                .body(createdBook);
    }

    @PutMapping("{id}")
    public Book updateBook(@PathVariable long id, @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    /*    @Setter(onMethod_ = @Value("${java.version}"))
    private String javaVersion;

    @Setter(onMethod_ = @Value("${my.text}"))
    private String myText;*/


/*    @PostConstruct
    public void init() {
        log.info("My text is {}", myText);
        log.info("Java version is {}", javaVersion);
    }*/


}
