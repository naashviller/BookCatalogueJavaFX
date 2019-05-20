package ru.itis.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import ru.itis.model.Book;

public class SearchController extends BaseController implements Initializable {

  @FXML
  private TextField searchField;

  @FXML
  private Button back;

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
  private Text searchLabel;

  @FXML
  private ComboBox<String> itemChoose;

  @FXML
  private Text nothingFoundText;

  private RestTemplate restTemplate;

  private final String BOOK_API = "http://localhost:80/books/search/";

  public void initialize(URL location, ResourceBundle resources) {
    searchLabel.setVisible(false);
    results.setVisible(false);
    nothingFoundText.setVisible(false);
    List<HttpMessageConverter<?>> converters = new ArrayList<>();
    converters.add(new MappingJackson2HttpMessageConverter());
    restTemplate = new RestTemplate(converters);
  }

  public void search(ActionEvent event) throws Exception {
    String title = searchField.getText();

    String output = itemChoose.getSelectionModel().getSelectedItem();
    if (Objects.equals(output, "Автору")) {
      Book[] response = restTemplate.getForObject(
          BOOK_API + "author?author=" + searchField.getText(),
          Book[].class);
      List<Book> books = Arrays.asList(response);

      if (books.size() > 0) {
        setElementsVisibleIfFound();
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

        name.setCellValueFactory(new PropertyValueFactory<>("title"));
        author.setCellValueFactory(new PropertyValueFactory<>("authorFullName"));
        genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        status.setCellValueFactory(new PropertyValueFactory<>("bookStatusName"));

        results.getItems().setAll(response);
      } else {
        setElementsInvisibleIfNothingFound();
      }
    } else if (Objects.equals(output, "Названию")) {
      Book[] response = restTemplate
          .getForObject(BOOK_API + "title?title=" + searchField.getText(), Book[].class);
      List<Book> books = Arrays.asList(response);
      if (books.size() > 0) {
        for (Book book : books) {
          setElementsVisibleIfFound();
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

        name.setCellValueFactory(new PropertyValueFactory<>("title"));
        author.setCellValueFactory(new PropertyValueFactory<>("authorFullName"));
        genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        status.setCellValueFactory(new PropertyValueFactory<>("bookStatusName"));

        results.getItems().setAll(response);
      } else {
        setElementsInvisibleIfNothingFound();
      }
    } else {
      Book[] response = restTemplate
          .getForObject(BOOK_API + "genre?genre=" + searchField.getText(), Book[].class);
      List<Book> books = Arrays.asList(response);

      if (books.size() > 0) {
        setElementsVisibleIfFound();
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

        name.setCellValueFactory(new PropertyValueFactory<>("title"));
        author.setCellValueFactory(new PropertyValueFactory<>("authorFullName"));
        genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        status.setCellValueFactory(new PropertyValueFactory<>("bookStatusName"));

        results.getItems().setAll(response);
      } else {
        setElementsInvisibleIfNothingFound();
      }
    }
  }

  private void setElementsInvisibleIfNothingFound() {
    results.setVisible(false);
    searchLabel.setVisible(false);
    nothingFoundText.setVisible(true);
  }

  private void setElementsVisibleIfFound() {
    results.setVisible(true);
    searchLabel.setVisible(true);
    nothingFoundText.setVisible(false);
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
