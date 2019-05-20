package ru.itis.model.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.itis.model.Author;

@Data
@Setter
@Getter
@AllArgsConstructor
public class BookForm {

  private String title;
  private String genre;
  private Author author;
}
