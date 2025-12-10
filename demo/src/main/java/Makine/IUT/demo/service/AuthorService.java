package Makine.IUT.demo.service;

import Makine.IUT.demo.domain.Author;
import Makine.IUT.demo.dto.AuthorDTO;
import Makine.IUT.demo.dto.CreateAuthorDTO;
import Makine.IUT.demo.dto.UpdateAuthorDTO;
import Makine.IUT.demo.exception.ResourceNotFoundException;
import Makine.IUT.demo.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorService {

    private final AuthorRepository authorRepository;

    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AuthorDTO getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        return toDTO(author);
    }

    @Transactional
    public AuthorDTO createAuthor(CreateAuthorDTO createAuthorDTO) {
        Author author = new Author();
        author.setFirstName(createAuthorDTO.getFirstName());
        author.setLastName(createAuthorDTO.getLastName());
        author.setBirthYear(createAuthorDTO.getBirthYear());

        Author savedAuthor = authorRepository.save(author);
        return toDTO(savedAuthor);
    }

    @Transactional
    public AuthorDTO updateAuthor(Long id, UpdateAuthorDTO updateAuthorDTO) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));

        author.setFirstName(updateAuthorDTO.getFirstName());
        author.setLastName(updateAuthorDTO.getLastName());
        author.setBirthYear(updateAuthorDTO.getBirthYear());

        Author updatedAuthor = authorRepository.save(author);
        return toDTO(updatedAuthor);
    }

    @Transactional
    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Author not found with id: " + id);
        }
        authorRepository.deleteById(id);
    }

    private AuthorDTO toDTO(Author author) {
        return new AuthorDTO(
                author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getBirthYear()
        );
    }
}
