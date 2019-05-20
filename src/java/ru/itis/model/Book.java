package ru.itis.model;

import javafx.beans.property.StringProperty;
import lombok.Data;
import ru.itis.model.enums.BookStatus;

@Data

public class Book {

  private Long id;
  private String title;
  private String genre;
  private Author author;
  private BookStatus bookStatus;

  private String authorFullName;
  private String bookStatusName;
  private StringProperty bookStatusProperty;
}
