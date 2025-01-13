package org.wildcodeschool.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.wildcodeschool.myblog.model.Article;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    // Trouver des articles par titre
    List<Article> findByTitle(String title);

    // Requête pour chercher des articles par contenu
    @Query("SELECT a FROM Article a WHERE a.content LIKE %:content%")
    List<Article> findByContentContaining(@Param("content") String content);

    // Requête pour chercher des articles créés après une date donnée
    @Query("SELECT a FROM Article a WHERE a.createdAt > :date")
    List<Article> findCreatedAfter(@Param("date") LocalDateTime date);

    // Requête pour les 5 derniers articles créés
    @Query("SELECT a FROM Article a ORDER BY a.createdAt DESC")
    List<Article> findTop5ByOrderByCreatedAtDesc();
}