package kg.alatoo.bookstore.controllers;

import kg.alatoo.bookstore.dto.BookListDto;
import kg.alatoo.bookstore.services.BookService;
import kg.alatoo.bookstore.entities.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/book/")
public class BookController {

    private final BookService bookService;

    /*@GetMapping("{id}")
    public ResponseEntity<Book> getBook(@PathVariable long id) {
        Book bookById = bookService.getBookById(id);
        if (bookById == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(bookById);
    }*/
    @GetMapping("{id}")
    public Book getBookById(@PathVariable long id) {
        return bookService.getBookById(id);
    }



    @GetMapping
    public Page<BookListDto> getBooks(
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String[] sortBy //TODO
            ) {
            log.debug("Getting all books");
            return bookService.getBooks(pageNumber, pageSize);

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
