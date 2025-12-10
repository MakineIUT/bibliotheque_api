package Makine.IUT.demo.dto;

import Makine.IUT.demo.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private String isbn;
    private Integer year;
    private Category category;
    private AuthorDTO author;
}
