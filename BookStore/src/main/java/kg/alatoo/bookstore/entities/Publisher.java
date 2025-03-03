package kg.alatoo.bookstore.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Publisher {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String address;

    @OneToMany(mappedBy = "publisher")
    private List<Book> books;
}
