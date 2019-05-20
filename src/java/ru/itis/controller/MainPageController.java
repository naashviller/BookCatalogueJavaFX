package ru.itis.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.itis.util.AuthenticationUtil;

public class MainPageController extends BaseController implements Initializable {

  @FXML
  public Button admin;
  @FXML
  public Text addBookText;
  @FXML
  private Button findBook;
  @FXML
  private Button findAllBooks;
  @FXML
  private Button findBooked;
  @FXML
  private Button logout;

  final Menu menu = new Menu("Выйти");
  private final String ROLE_API = "http://localhost:80/user/";

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> userRole = restTemplate
        .getForEntity(ROLE_API + AuthenticationUtil.token, String.class);

    boolean isAdmin = userRole.getBody().equals("ADMIN");

    admin.setVisible(isAdmin);
    addBookText.setVisible(isAdmin);
  }

  @FXML
  public void findBook() throws Exception {
    Stage stage = (Stage) findBook.getScene().getWindow();

    stage.close();
    FXMLLoader fxmlLoader = new FXMLLoader(
        getClass().getClassLoader().getResource("fxml/search.fxml"));
    Parent root1 = fxmlLoader.load();
    stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setTitle("Найти книгу");
    stage.setScene(new Scene(root1));
    stage.show();
  }

  @FXML
  public void findAllBooks() throws Exception {
    Stage stage = (Stage) findAllBooks.getScene().getWindow();

    stage.close();
    FXMLLoader fxmlLoader = new FXMLLoader(
        getClass().getClassLoader().getResource("fxml/findAllBooks.fxml"));
    Parent root1 = fxmlLoader.load();
    stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setTitle("Каталог");
    stage.setScene(new Scene(root1));
    stage.show();
  }

  @FXML
  public void findBooked() throws Exception {
    Stage stage = (Stage) findBooked.getScene().getWindow();

    stage.close();
    FXMLLoader fxmlLoader = new FXMLLoader(
        getClass().getClassLoader().getResource("fxml/bookedBooks.fxml"));
    Parent root = fxmlLoader.load();
    stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setTitle("Забронированные книги");
    stage.setScene(new Scene(root));
    stage.show();
  }

  @FXML
  public void addBook() throws Exception {
    Stage stage = (Stage) findBooked.getScene().getWindow();

    stage.close();
    FXMLLoader fxmlLoader = new FXMLLoader(
        getClass().getClassLoader().getResource("fxml/adminPanel.fxml"));
    Parent root = fxmlLoader.load();
    stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setTitle("Забронированные книги");
    stage.setScene(new Scene(root));
    stage.show();
  }

  @FXML
  public void logoutAction() throws IOException {
    AuthenticationUtil.token = "";

    Stage stage = (Stage) logout.getScene().getWindow();
    // do what you have to do
    stage.close();
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/authorization.fxml"));
    Parent root1 = null;
    try {
      root1 = fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setTitle("Главная");
    stage.setScene(new Scene(root1));
    //pause.play();
    stage.show();
  }
}

