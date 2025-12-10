package Makine.IUT.demo.dto;

import Makine.IUT.demo.domain.Category;
import Makine.IUT.demo.validator.ISBN;
import Makine.IUT.demo.validator.YearRange;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "ISBN is required")
    @ISBN
    private String isbn;

    @NotNull(message = "Year is required")
    @YearRange
    private Integer year;

    @NotNull(message = "Category is required")
    private Category category;

    @NotNull(message = "Author ID is required")
    private Long authorId;
}
