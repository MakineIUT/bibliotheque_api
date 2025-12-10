package Makine.IUT.demo.repository;

import Makine.IUT.demo.domain.Author;
import Makine.IUT.demo.dto.TopAuthorDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT new Makine.IUT.demo.dto.TopAuthorDTO(a.id, a.firstName, a.lastName, COUNT(b.id)) " +
           "FROM Author a LEFT JOIN a.books b " +
           "GROUP BY a.id, a.firstName, a.lastName " +
           "ORDER BY COUNT(b.id) DESC")
    List<TopAuthorDTO> findTopAuthors(@Param("limit") int limit);
}
