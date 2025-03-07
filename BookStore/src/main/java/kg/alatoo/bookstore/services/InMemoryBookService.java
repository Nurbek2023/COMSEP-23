package kg.alatoo.bookstore.services;

import kg.alatoo.bookstore.dto.BookListDto;
import kg.alatoo.bookstore.entities.Book;
import kg.alatoo.bookstore.exceptions.NotFoundException;
import kg.alatoo.bookstore.mappers.BookMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InMemoryBookService implements BookService {

    private final Map<Long, Book> books = new HashMap<>();
    private final BookMapper bookMapper;

    private static long nextId = 1;

    public InMemoryBookService(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
        Book book1 = Book.builder()
                .id(nextId++)
                .title("Book 1")
                .author("Author 1")
                .isbn("32135415135")
                .build();

        Book book2 = Book.builder()
                .id(nextId++)
                .title("Book 2")
                .author("Author 2")
                .isbn("5432151")
                .build();

        Book book3 = Book.builder()
                .id(nextId++)
                .title("Book 3")
                .author("Author 3")
                .isbn("85436413851")
                .build();

        books.put(book1.getId(), book1);
        books.put(book2.getId(), book2);
        books.put(book3.getId(), book3);
    }

    @Override
    public Book updateBook(Long id, Book book) {
        Book existingBook = books.get(id);
        if (existingBook == null) {
            throw new IllegalArgumentException("Book not found");
        }
        if (book.getId() != null) {
            if (!book.getId().equals(id)) {
                throw new IllegalArgumentException("Book id mismatch");
            }
        }
        book.setId(id);
        books.put(id, book);
        return book;
    }

    @Override
    public Book addBook(Book book) {
        book.setId(nextId++);
        books.put(book.getId(), book);
        return book;
    }

    @Override
    public Page<BookListDto> getBooks(Integer pageNumber, Integer pageSize) {
        return null;//bookMapper.booksToBookListDtos(new ArrayList<>(books.values()));
    }

    @Override
    public List<BookListDto> getBooksByAuthor(String author) {
        List<Book> books = this.books.values()
                .stream()
                .filter(book -> book.getAuthor().equals(author))
                .collect(Collectors.toList());
        return bookMapper.booksToBookListDtos(books);
    }

    @Override
    public Book getBookById(Long id) {
        if (!books.containsKey(id)) {
            throw new NotFoundException("Error: Book with id " + id + " not found");
        }
        return books.get(id);
    }

    @Override
    public Book deleteBook(Long id) {
        if (!books.containsKey(id)) {
            throw new NotFoundException("Error: Book with id " + id + " not found");
        }
        return books.remove(id);
    }
}
