package Makine.IUT.demo.service;

import Makine.IUT.demo.dto.CategoryStatsDTO;
import Makine.IUT.demo.dto.TopAuthorDTO;
import Makine.IUT.demo.repository.AuthorRepository;
import Makine.IUT.demo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatsService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public List<CategoryStatsDTO> getBooksPerCategory() {
        return bookRepository.countBooksByCategory();
    }

    public List<TopAuthorDTO> getTopAuthors(int limit) {
        return authorRepository.findTopAuthors(limit).stream()
                .limit(limit)
                .collect(Collectors.toList());
    }
}
