package com.example.mangaservice.exceptions;

public class ListIsEmpty extends RuntimeException
{
    public ListIsEmpty(String message) {
        super(message);
    }
}
