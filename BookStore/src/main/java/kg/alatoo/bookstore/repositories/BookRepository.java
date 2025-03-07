package kg.alatoo.bookstore.repositories;

import kg.alatoo.bookstore.entities.Author;
import kg.alatoo.bookstore.entities.Book;
import kg.alatoo.bookstore.entities.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long>, PagingAndSortingRepository<Book, Long> {
    List<Book> findByAuthor(Author author);

    List<Book> findAllByAuthorsContains(List<Author> authors);

    Page<Book> findBooksByPublisher(Publisher publisher, Pageable pageable);
}
