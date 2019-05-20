package ru.itis.model.enums;

public enum BookStatus {
  FREE("В наличии"), READING("У читателя"), BOOKED("Забронирована");

  private final String name;

  BookStatus(String s) {
    name = s;
  }

  public boolean equalsName(String otherName) {
    return name.equals(otherName);
  }

  public static BookStatus fromString(String text) {
    for (BookStatus b : BookStatus.values()) {
      if (b.name.equalsIgnoreCase(text)) {
        return b;
      }
    }
    return null;
  }

  public String toString() {
    return this.name;
  }
}
