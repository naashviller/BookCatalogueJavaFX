package ru.itis.model.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.itis.model.enums.BookStatus;

@Data
@AllArgsConstructor
public class EditBookStatusForm {

  public Long bookId;

  private BookStatus bookStatus;
}
