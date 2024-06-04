package me.leekukju.springbootdeveloper.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.leekukju.springbootdeveloper.domain.Article;
import me.leekukju.springbootdeveloper.dto.AddArticleRequest;
import me.leekukju.springbootdeveloper.dto.UpdateArticleRequest;
import me.leekukju.springbootdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(Long id) {
        return blogRepository.findById(id)  // BlogRepository가 JpaRepository<Article, Long>을 상속하므로 Optional<Article>을 반환
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

    }

    public void deleteById(long id) {
        blogRepository.deleteById(id);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        article.update(request.getTitle(), request.getContent());

        return article;
    }
}
