package kg.alatoo.bookstore.services;

import kg.alatoo.bookstore.dto.BookListDto;
import kg.alatoo.bookstore.entities.Author;
import kg.alatoo.bookstore.entities.Book;
import kg.alatoo.bookstore.exceptions.NotFoundException;
import kg.alatoo.bookstore.mappers.BookMapper;
import kg.alatoo.bookstore.repositories.AuthorRepository;
import kg.alatoo.bookstore.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Primary
public class DatabaseBookService implements BookService {

    private static final Integer DEFAULT_PAGE_SIZE = 20;
    private static final Integer DEFAULT_PAGE = 0;

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;

    @Override
    public Book updateBook(Long id, Book book) {
        if (!bookRepository.existsById(id)) {
            throw new NotFoundException("The book with id " + id + " does not exist");
        }
        if (book.getId()!=null && book.getId() != 0 && !book.getId().equals(id)) {
            throw new IllegalArgumentException("The id does not match the id of a book");
        }
        book.setId(id);
        return bookRepository.save(book);
    }

    @Override
    public Book addBook(Book book) {
        if(book.getId()!=null && book.getId() != 0) {
            throw new IllegalArgumentException("To create new book, you must not specify an id");
        }
        book.setId(null);
        return bookRepository.save(book);
    }

    @Override
    public Page<BookListDto> getBooks(Integer pageNumber, Integer pageSize) {
        return bookRepository
                .findAll(generatePagable(pageNumber, pageSize))
                .map(bookMapper::bookToBookListDto);
    }

    private static Pageable generatePagable(Integer pageNumber, Integer pageSize) {
        int requestedPage, requestedPageSize;

        if (pageSize == null || pageSize <= 0) {
            requestedPageSize = DEFAULT_PAGE_SIZE;
        } else {
            requestedPageSize = pageSize;
        }

        if (pageNumber == null || pageNumber <= 0) {
            requestedPage = DEFAULT_PAGE;
        } else {
            requestedPage = pageNumber - 1;
        }

        return PageRequest.of(requestedPage, requestedPageSize);
    }

    @Override
    public List<BookListDto> getBooksByAuthor(String author) {
        List<Author> authors = authorRepository.getAuthorsByFirstNameOrLastName(author, author);
        return bookMapper.booksToBookListDtos(
                bookRepository.findAllByAuthorsContains(authors)
        );
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Book with id " + id + " does not exist"));
    }


    @Override
    public Book deleteBook(Long id){
        Book deletedBook = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("The book with id " + id + " does not exist"));
        bookRepository.deleteById(id);
        return deletedBook;
    }
}
