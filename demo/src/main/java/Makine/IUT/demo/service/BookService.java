package Makine.IUT.demo.service;

import Makine.IUT.demo.domain.Author;
import Makine.IUT.demo.domain.Book;
import Makine.IUT.demo.domain.Category;
import Makine.IUT.demo.dto.*;
import Makine.IUT.demo.exception.DuplicateResourceException;
import Makine.IUT.demo.exception.ResourceNotFoundException;
import Makine.IUT.demo.repository.AuthorRepository;
import Makine.IUT.demo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public Page<BookDTO> getBooks(
            String title,
            Long authorId,
            Category category,
            Integer yearFrom,
            Integer yearTo,
            Pageable pageable) {

        Page<Book> books = bookRepository.findBooksWithFilters(
                title, authorId, category, yearFrom, yearTo, pageable);

        return books.map(this::toDTO);
    }

    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return toDTO(book);
    }

    @Transactional
    public BookDTO createBook(CreateBookDTO createBookDTO) {
        // Check if ISBN already exists
        if (bookRepository.existsByIsbn(createBookDTO.getIsbn())) {
            throw new DuplicateResourceException("Book with ISBN " + createBookDTO.getIsbn() + " already exists");
        }

        // Check if author exists
        Author author = authorRepository.findById(createBookDTO.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + createBookDTO.getAuthorId()));

        Book book = new Book();
        book.setTitle(createBookDTO.getTitle());
        book.setIsbn(createBookDTO.getIsbn());
        book.setYear(createBookDTO.getYear());
        book.setCategory(createBookDTO.getCategory());
        book.setAuthor(author);

        Book savedBook = bookRepository.save(book);
        return toDTO(savedBook);
    }

    @Transactional
    public BookDTO updateBook(Long id, UpdateBookDTO updateBookDTO) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        // Check if ISBN is being changed and if it already exists
        if (!book.getIsbn().equals(updateBookDTO.getIsbn()) &&
                bookRepository.existsByIsbnAndIdNot(updateBookDTO.getIsbn(), id)) {
            throw new DuplicateResourceException("Book with ISBN " + updateBookDTO.getIsbn() + " already exists");
        }

        // Check if author exists
        Author author = authorRepository.findById(updateBookDTO.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + updateBookDTO.getAuthorId()));

        book.setTitle(updateBookDTO.getTitle());
        book.setIsbn(updateBookDTO.getIsbn());
        book.setYear(updateBookDTO.getYear());
        book.setCategory(updateBookDTO.getCategory());
        book.setAuthor(author);

        Book updatedBook = bookRepository.save(book);
        return toDTO(updatedBook);
    }

    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    private BookDTO toDTO(Book book) {
        AuthorDTO authorDTO = new AuthorDTO(
                book.getAuthor().getId(),
                book.getAuthor().getFirstName(),
                book.getAuthor().getLastName(),
                book.getAuthor().getBirthYear()
        );

        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getYear(),
                book.getCategory(),
                authorDTO
        );
    }
}
