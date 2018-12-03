package ru.ivmiit.model;

import java.util.ArrayList;
import java.util.List;

public class BooksList {
    private List<Book> books;

    public BooksList() {
        books = new ArrayList<>();
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
