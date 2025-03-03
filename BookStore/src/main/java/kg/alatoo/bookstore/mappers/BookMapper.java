package kg.alatoo.bookstore.mappers;

import kg.alatoo.bookstore.dto.BookListDto;
import kg.alatoo.bookstore.entities.Book;

import java.util.List;
import java.util.stream.Collectors;


public interface BookMapper {

    BookListDto bookToBookListDto(Book book);

    default List<BookListDto> booksToBookListDtos(List<Book> books) {
        return books.stream()
                .map(this::bookToBookListDto)
                .collect(Collectors.toList());
    }
}
