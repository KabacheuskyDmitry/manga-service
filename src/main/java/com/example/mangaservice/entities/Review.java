package com.example.mangaservice.entities;
import jakarta.persistence.*;


@Entity

@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne
    @JoinColumn(name = "manga_id", nullable = false)
    private Manga manga;
    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() { return content; }
    public Manga getManga() {
        return manga;
    }

    public void setManga(Manga manga) {
        this.manga = manga;
    }
}