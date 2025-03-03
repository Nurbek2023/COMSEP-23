package kg.alatoo.bookstore.dto;

import jakarta.persistence.ManyToMany;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BookDto {
    private Long id;
    private String title;
    private String description;
    private String author;
    private String isbn;

    private String publisher;  //changed Publisher to String

    private String authors; //changed Publisher to String

}
