package ru.ivmiit.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController extends BaseController implements Initializable {
    @FXML
    private Button findBook;
    @FXML
    private Button findAllBooks;
    @FXML
    private Button findBooked;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void findBook() throws Exception {
        Stage stage = (Stage) findBook.getScene().getWindow();

        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/search.fxml"));
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/findAllBooks.fxml"));
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/bookedBooks.fxml"));
        Parent root =  fxmlLoader.load();
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Забронированные книги");
        stage.setScene(new Scene(root));
        stage.show();
    }
}

