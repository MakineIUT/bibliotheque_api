package Makine.IUT.demo.repository;

import Makine.IUT.demo.domain.Book;
import Makine.IUT.demo.domain.Category;
import Makine.IUT.demo.dto.CategoryStatsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);

    boolean existsByIsbnAndIdNot(String isbn, Long id);

    @Query("SELECT b FROM Book b WHERE " +
           "(:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
           "(:authorId IS NULL OR b.author.id = :authorId) AND " +
           "(:category IS NULL OR b.category = :category) AND " +
           "(:yearFrom IS NULL OR b.year >= :yearFrom) AND " +
           "(:yearTo IS NULL OR b.year <= :yearTo)")
    Page<Book> findBooksWithFilters(
            @Param("title") String title,
            @Param("authorId") Long authorId,
            @Param("category") Category category,
            @Param("yearFrom") Integer yearFrom,
            @Param("yearTo") Integer yearTo,
            Pageable pageable
    );

    @Query("SELECT new Makine.IUT.demo.dto.CategoryStatsDTO(b.category, COUNT(b)) " +
           "FROM Book b GROUP BY b.category ORDER BY b.category")
    List<CategoryStatsDTO> countBooksByCategory();
}
