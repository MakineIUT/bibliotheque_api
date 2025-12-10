package Makine.IUT.demo.dto;

import Makine.IUT.demo.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryStatsDTO {
    private Category category;
    private Long count;
}
