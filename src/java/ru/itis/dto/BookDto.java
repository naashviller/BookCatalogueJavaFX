package ru.itis.dto;

import ru.itis.model.Author;
import ru.itis.model.enums.BookStatus;

public class BookDto {

  private String title;
  private String genre;
  private Author author;


  public BookDto(String title, String genre, Author author) {
    this.title = title;
    this.genre = genre;
    this.author = author;
  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public Author getAuthor() {
    return author;
  }

  public void setAuthor(Author author) {
    this.author = author;
  }

  public BookStatus getBookStatus() {
    return bookStatus;
  }

  public void setBookStatus(BookStatus bookStatus) {
    this.bookStatus = bookStatus;
  }

  private BookStatus bookStatus;

}
