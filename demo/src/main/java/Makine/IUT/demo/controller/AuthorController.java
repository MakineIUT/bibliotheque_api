package Makine.IUT.demo.controller;

import Makine.IUT.demo.dto.AuthorDTO;
import Makine.IUT.demo.dto.CreateAuthorDTO;
import Makine.IUT.demo.dto.UpdateAuthorDTO;
import Makine.IUT.demo.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
@Tag(name = "Authors", description = "API pour la gestion des auteurs")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    @Operation(summary = "Liste des auteurs", description = "Récupère la liste complète des auteurs")
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Auteur par ID", description = "Récupère un auteur spécifique par son ID")
    public ResponseEntity<AuthorDTO> getAuthorById(
            @Parameter(description = "ID de l'auteur") @PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @PostMapping
    @Operation(summary = "Créer un auteur", description = "Ajoute un nouvel auteur (nécessite X-API-KEY)")
    public ResponseEntity<AuthorDTO> createAuthor(
            @Valid @RequestBody CreateAuthorDTO createAuthorDTO) {
        AuthorDTO created = authorService.createAuthor(createAuthorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un auteur", description = "Modifie un auteur existant (nécessite X-API-KEY)")
    public ResponseEntity<AuthorDTO> updateAuthor(
            @Parameter(description = "ID de l'auteur") @PathVariable Long id,
            @Valid @RequestBody UpdateAuthorDTO updateAuthorDTO) {
        return ResponseEntity.ok(authorService.updateAuthor(id, updateAuthorDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un auteur", description = "Supprime un auteur existant (nécessite X-API-KEY)")
    public ResponseEntity<Void> deleteAuthor(
            @Parameter(description = "ID de l'auteur") @PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
