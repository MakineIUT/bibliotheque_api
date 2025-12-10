package Makine.IUT.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopAuthorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Long bookCount;
}
