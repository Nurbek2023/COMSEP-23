package kg.alatoo.bookstore.mappers;


import kg.alatoo.bookstore.dto.BookListDto;
import kg.alatoo.bookstore.entities.Book;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public BookListDto bookToBookListDto(Book book) {
        // Get the first author's full name (first and last name)
        String author = book.getAuthors().stream()
                .map(a -> a.getFirstName() + " " + a.getLastName())
                .findFirst()
                .orElse("Unknown Author"); // Fallback in case no author is found

        // Return BookListDto with simplified author name and publisher name
        return BookListDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .publisher(book.getPublisher().getName())  // Keep publisher name as string
                .author(author)  // Use the simplified author field
                .build();
    }
}