package Makine.IUT.demo.controller;

import Makine.IUT.demo.domain.Category;
import Makine.IUT.demo.dto.BookDTO;
import Makine.IUT.demo.dto.CreateBookDTO;
import Makine.IUT.demo.dto.UpdateBookDTO;
import Makine.IUT.demo.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Tag(name = "Books", description = "API pour la gestion des livres")
public class BookController {

    private final BookService bookService;

    @GetMapping
    @Operation(summary = "Liste paginée des livres", description = "Récupère une liste paginée de livres avec filtres optionnels")
    public ResponseEntity<Page<BookDTO>> getBooks(
            @Parameter(description = "Titre du livre (recherche partielle)") @RequestParam(required = false) String title,
            @Parameter(description = "ID de l'auteur") @RequestParam(required = false) Long authorId,
            @Parameter(description = "Catégorie du livre") @RequestParam(required = false) Category category,
            @Parameter(description = "Année minimale") @RequestParam(required = false) Integer yearFrom,
            @Parameter(description = "Année maximale") @RequestParam(required = false) Integer yearTo,
            @Parameter(description = "Numéro de page (0-indexed)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Taille de page") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Tri (ex: year,desc)") @RequestParam(required = false) String sort) {

        Pageable pageable = createPageable(page, size, sort);
        Page<BookDTO> books = bookService.getBooks(title, authorId, category, yearFrom, yearTo, pageable);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Livre par ID", description = "Récupère un livre spécifique par son ID")
    public ResponseEntity<BookDTO> getBookById(
            @Parameter(description = "ID du livre") @PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping
    @Operation(summary = "Créer un livre", description = "Ajoute un nouveau livre (nécessite X-API-KEY)")
    public ResponseEntity<BookDTO> createBook(
            @Valid @RequestBody CreateBookDTO createBookDTO) {
        BookDTO created = bookService.createBook(createBookDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un livre", description = "Modifie un livre existant (nécessite X-API-KEY)")
    public ResponseEntity<BookDTO> updateBook(
            @Parameter(description = "ID du livre") @PathVariable Long id,
            @Valid @RequestBody UpdateBookDTO updateBookDTO) {
        return ResponseEntity.ok(bookService.updateBook(id, updateBookDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un livre", description = "Supprime un livre existant (nécessite X-API-KEY)")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "ID du livre") @PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    private Pageable createPageable(int page, int size, String sort) {
        if (sort != null && !sort.isEmpty()) {
            String[] sortParams = sort.split(",");
            String property = sortParams[0];
            Sort.Direction direction = sortParams.length > 1 && "desc".equalsIgnoreCase(sortParams[1])
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;
            return PageRequest.of(page, size, Sort.by(direction, property));
        }
        return PageRequest.of(page, size);
    }
}
