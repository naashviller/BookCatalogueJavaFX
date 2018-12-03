package ru.ivmiit.model;

import lombok.Data;
import ru.ivmiit.model.enums.BookStatus;

@Data

public class Book {
    private String title;
    private String genre;
    private Author author;
    private BookStatus bookStatus;
}
