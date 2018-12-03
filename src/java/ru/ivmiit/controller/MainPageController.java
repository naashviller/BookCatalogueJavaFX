package ru.ivmiit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.web.client.RestTemplate;
import ru.ivmiit.app.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController extends BaseController implements Initializable {
    @FXML
    private Button findBook;
    @FXML
    private Button findAllBooks;
    @FXML
    private Button findBooked;
    @FXML
    private Button back;
    public static final String FINDBOOK_URL = "/fxml/search.fxml";
    private RestTemplate restTemplate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*findBook.setOnAction(event -> {
            Main.getNavigation().load(SearchController.FINDBOOK_URL).Show();
        });*/


    }

    @FXML
    public void findBook() throws Exception {
        Stage stage = (Stage) findBook.getScene().getWindow();

        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/search.fxml"));
        Parent root1 = fxmlLoader.load();
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("FindAllBooks");
        stage.setScene(new Scene(root1));
        stage.show();


    }

    @FXML
    public void findAllBooks(ActionEvent event) throws Exception {
        Stage stage = (Stage) findAllBooks.getScene().getWindow();

        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/findAllBooks.fxml"));
        Parent root1 = fxmlLoader.load();
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("FindAllBooks");
        stage.setScene(new Scene(root1));
        stage.show();
    }

    @FXML
    public void findBooked(ActionEvent event) throws Exception {
        Stage stage = (Stage) findBooked.getScene().getWindow();

        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/bookedBooks.fxml"));
        //FXMLLoader(Main.class.getClassLoader().getResource("fxml/bookedBooks.fxml"));
        System.out.println("мы зашли в метод и получили фхмл");
        Parent root =  fxmlLoader.load();
        //Parent root = (Parent)FXMLLoader.load( BaseController.class.getResource("/fxml/bookedBooks.fxml"));
        System.out.println("мы сделали метод лоад");
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        System.out.println("осталось показать сцену");
        stage.show();

    }


}

