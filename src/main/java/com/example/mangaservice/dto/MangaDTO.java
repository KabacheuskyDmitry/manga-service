package com.example.mangaservice.dto;

public class MangaDTO {

    private Long id;
    private String name;
    private String author;
    private Double rating;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getAuthor() {
        return author; }

    public void setAuthor(String author) {

        this.author = author;
    }
    public Double getRating() {

        return rating;
    }

    public void setRating(Double rating) {

        this.rating = rating;
    }

    public MangaDTO(Long id,String name, Double rating, String author) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.author = author;
    }
}