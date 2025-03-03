package kg.alatoo.bookstore.dto;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class BookListDto {
    private Long id;
    private String title;
    private String author; //Simplified to String for the author and renamed it from authors
    private String publisher;
    private String isbn;
}
