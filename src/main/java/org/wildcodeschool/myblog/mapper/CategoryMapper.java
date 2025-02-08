package org.wildcodeschool.myblog.mapper;

import org.springframework.stereotype.Component;
import org.wildcodeschool.myblog.Service.ArticleService;
import org.wildcodeschool.myblog.dto.ArticleDTO;
import org.wildcodeschool.myblog.dto.CategoryDTO;
import org.wildcodeschool.myblog.model.Article;
import org.wildcodeschool.myblog.model.Category;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    private final ArticleService articleService;

    public CategoryMapper(ArticleService articleService) {
        this.articleService = articleService;
    }

    public CategoryDTO convertToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        if (category.getArticles() != null) {
            List<Article> articles = category.getArticles();
            List<ArticleDTO> articleDTOs = articles.stream()
                    .map(article -> {
                        if (!articleService.existsById(article.getId())) {
                            ArticleDTO articleDTO = new ArticleDTO();
                            articleDTO.setId(article.getId());
                            articleDTO.setTitle(article.getTitle());
                            articleDTO.setContent(article.getContent());
                            articleDTO.setUpdatedAt(article.getUpdatedAt());
                            return articleDTO;
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            categoryDTO.setArticles(articleDTOs);
        }
        return categoryDTO;
    }
}