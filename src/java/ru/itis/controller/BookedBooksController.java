package ru.itis.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import ru.itis.model.Book;

public class BookedBooksController extends BaseController implements Initializable {

  @FXML
  private TableView<Book> results;

  @FXML
  private TableColumn<Book, String> name;

  @FXML
  private TableColumn<Book, String> author;

  @FXML
  private TableColumn<Book, String> genre;

  @FXML
  private TableColumn<Book, String> status;

  @FXML
  private Button back;

  private final String BOOKED_BOOKS_API = "http://localhost:80/books/search/booked";

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    List<HttpMessageConverter<?>> converters = new ArrayList<>();
    converters.add(new MappingJackson2HttpMessageConverter());
    RestTemplate restTemplate = new RestTemplate(converters);

    Book[] response = restTemplate.getForObject(BOOKED_BOOKS_API, Book[].class);
    List<Book> books = Arrays.asList(response);

    for (Book book : books) {
      book.setAuthorFullName(book.getAuthor().getName() +
          " " + book.getAuthor().getLastName());
      switch (book.getBookStatus()) {
        case BOOKED:
          book.setBookStatusName("Забронирована");
          break;
        case FREE:
          book.setBookStatusName("В наличии");
          break;
        case READING:
          book.setBookStatusName("У читателя");
          break;
      }
    }

    if (books.size() > 0) {
      name.setCellValueFactory(new PropertyValueFactory<>("title"));
      author.setCellValueFactory(new PropertyValueFactory<>("authorFullName"));
      genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
      status.setCellValueFactory(new PropertyValueFactory<>("bookStatusName"));

      results.getItems().setAll(response);
    }
  }

  public void goBack(ActionEvent event) throws Exception {
    Stage stage = (Stage) back.getScene().getWindow();

    stage.close();
    FXMLLoader fxmlLoader = new FXMLLoader(
        getClass().getClassLoader().getResource("fxml/mainPage.fxml"));
    Parent root1 = fxmlLoader.load();
    stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);

    stage.setScene(new Scene(root1));
    stage.show();
  }
}
