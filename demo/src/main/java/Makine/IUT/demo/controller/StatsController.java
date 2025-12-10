package Makine.IUT.demo.controller;

import Makine.IUT.demo.dto.CategoryStatsDTO;
import Makine.IUT.demo.dto.TopAuthorDTO;
import Makine.IUT.demo.service.StatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
@Tag(name = "Statistics", description = "API pour les statistiques de la bibliothèque")
public class StatsController {

    private final StatsService statsService;

    @GetMapping("/books-per-category")
    @Operation(summary = "Livres par catégorie", description = "Récupère le nombre de livres par catégorie")
    public ResponseEntity<List<CategoryStatsDTO>> getBooksPerCategory() {
        return ResponseEntity.ok(statsService.getBooksPerCategory());
    }

    @GetMapping("/top-authors")
    @Operation(summary = "Top auteurs", description = "Récupère les auteurs ayant le plus de livres")
    public ResponseEntity<List<TopAuthorDTO>> getTopAuthors(
            @Parameter(description = "Nombre d'auteurs à retourner") @RequestParam(defaultValue = "3") int limit) {
        return ResponseEntity.ok(statsService.getTopAuthors(limit));
    }
}
