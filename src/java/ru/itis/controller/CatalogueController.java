package ru.itis.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import ru.itis.dto.UserDto;
import ru.itis.model.Book;
import ru.itis.model.enums.BookStatus;
import ru.itis.model.form.EditBookStatusForm;
import ru.itis.util.AuthenticationUtil;

public class CatalogueController extends BaseController implements Initializable {

  private final String BOOK_API = "http://localhost:80/books";
  private final String EDIT_BOOK_API = "http://localhost:80/books/edit/status";
  private final String ROLE_API = "http://localhost:80/user/";
  private final String USER_DATA_API = "http://localhost:80/user/";
  private final String NOTIFY_API = "http://localhost:80/books/notify/";

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
  private TableColumn<Book, String> statusChange = new TableColumn<>("Изменить");

  @FXML
  private Button notifyButton;

  @FXML
  private Button bookButton;

  @FXML
  private Button back;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    List<HttpMessageConverter<?>> converters = new ArrayList<>();
    converters.add(new MappingJackson2HttpMessageConverter());
    RestTemplate restTemplate = new RestTemplate(converters);
    Book[] response = restTemplate.getForObject(BOOK_API, Book[].class);
    List<Book> books = Arrays.asList(response);
    ObservableList<String> bookStatuses = FXCollections.observableArrayList();
    bookStatuses.add(BookStatus.FREE.toString());
    bookStatuses.add(BookStatus.READING.toString());
    bookStatuses.add(BookStatus.BOOKED.toString());

    RestTemplate restTemplate1 = new RestTemplate();
    ResponseEntity<String> userRole = restTemplate1
        .getForEntity(ROLE_API + AuthenticationUtil.token, String.class);

    boolean isAdmin = userRole.getBody().equals("ADMIN");

    if (!isAdmin) {
      bookButton.setDisable(true);
      statusChange.setVisible(false);
      notifyButton.setDisable(true);
      results.setMaxWidth(573);
      results.setMinWidth(573);
      results.setPrefWidth(573);
    } else {
      bookButton.setVisible(false);
      notifyButton.setVisible(false);
      statusChange.setSortable(false);
      statusChange.setEditable(true);
      statusChange.setVisible(true);
    }

    results.setEditable(true);
    name.setSortable(false);
    author.setSortable(false);
    genre.setSortable(false);
    status.setSortable(false);

    for (Book book : books) {
      book.setAuthorFullName(book.getAuthor().getName() +
          " " + book.getAuthor().getLastName());
      switch (book.getBookStatus()) {
        case BOOKED:
          book.setBookStatusName(BookStatus.BOOKED.toString());
          break;
        case FREE:
          book.setBookStatusName(BookStatus.FREE.toString());
          break;
        case READING:
          book.setBookStatusName(BookStatus.READING.toString());
          break;
      }
    }

    if (books.size() > 0) {
      name.setCellValueFactory(new PropertyValueFactory<>("title"));
      author.setCellValueFactory(new PropertyValueFactory<>("authorFullName"));
      genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
      status.setCellValueFactory(new PropertyValueFactory<>("bookStatusName"));

      if (isAdmin) {
        statusChange.setCellFactory(ComboBoxTableCell.forTableColumn(bookStatuses));
        statusChange.setOnEditCommit(
            (CellEditEvent<Book, String> event) -> restTemplate.postForObject(EDIT_BOOK_API,
                new EditBookStatusForm(
                    event.getTableView().getItems().get(event.getTablePosition().getRow()).getId(),
                    BookStatus.fromString(event.getNewValue())), EditBookStatusForm.class));
      } else {
        results.setOnMouseClicked(event -> {
          Book book = results.getSelectionModel().getSelectedItem();
          UserDto userDto = restTemplate1
              .getForObject(USER_DATA_API + AuthenticationUtil.token + "/all", UserDto.class);
          if (book.getBookStatus().equals(BookStatus.BOOKED)) {
            notifyButton.setDisable(false);
            notifyButton.setOnAction(clickEvent -> {
              RestTemplate postNotifyMessage = new RestTemplate();
              postNotifyMessage
                  .postForLocation(NOTIFY_API + book.getId() + "/" + userDto.getId(), String.class);

              Label label = new Label("Как только книга появится в библиотеке, Вам придет письмо на почту");
              StackPane secondaryLayout = new StackPane();
              secondaryLayout.getChildren().add(label);

              Scene secondScene = new Scene(secondaryLayout, 430, 100);

              // New window (Stage)
              Stage newWindow = new Stage();
              newWindow.setTitle("Уведомление");
              newWindow.setScene(secondScene);

              // Specifies the modality for new window.
              newWindow.initModality(Modality.WINDOW_MODAL);

              newWindow.initOwner(((Node)event.getSource()).getScene().getWindow());

              // Set position of second window, related to primary window.
              newWindow.setX(((Node)event.getSource()).getScene().getWindow().getX() + 200);
              newWindow.setY(((Node)event.getSource()).getScene().getWindow().getY() + 100);

              newWindow.show();
            });
          } else {
            notifyButton.setDisable(true);
          }
          if (book.getBookStatus().equals(BookStatus.FREE)) {
            bookButton.setDisable(false);
            bookButton.setOnAction(clickEvent -> {
              RestTemplate postBook = new RestTemplate();
              postBook.postForLocation(EDIT_BOOK_API, new EditBookStatusForm(book.getId(), BookStatus.BOOKED));

              Label label = new Label("Книга успешно забронирована!");
              StackPane secondaryLayout = new StackPane();
              secondaryLayout.getChildren().add(label);

              Scene secondScene = new Scene(secondaryLayout, 230, 100);

              // New window (Stage)
              Stage newWindow = new Stage();
              newWindow.setTitle("Книга забронирована");
              newWindow.setScene(secondScene);

              // Specifies the modality for new window.
              newWindow.initModality(Modality.WINDOW_MODAL);

              newWindow.initOwner(((Node)event.getSource()).getScene().getWindow());

              // Set position of second window, related to primary window.
              newWindow.setX(((Node)event.getSource()).getScene().getWindow().getX() + 200);
              newWindow.setY(((Node)event.getSource()).getScene().getWindow().getY() + 100);

              newWindow.show();
            });
          } else {
            bookButton.setDisable(true);
          }
        });
      }

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

