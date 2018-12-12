package ru.ivmiit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import ru.ivmiit.model.Book;
import ru.ivmiit.model.enums.BookStatus;

import java.net.URL;
import java.util.*;

public class SearchController extends BaseController implements Initializable {
    @FXML
    private TextField searchField;
    @FXML
    private Button back;
    @FXML
    private ListView results;
    @FXML
    private Text searchLabel;
    @FXML
    private ComboBox<String> itemChoose;
    @FXML
    private Text statusLabel;
    @FXML
    private ListView statusResults;
    @FXML
    private Text nothingFoundText;

    private RestTemplate restTemplate;

    private final String BOOK_API = "http://localhost:80/books/search/";

    public void initialize(URL location, ResourceBundle resources) {
        searchLabel.setVisible(false);
        statusLabel.setVisible(false);
        results.setVisible(false);
        statusResults.setVisible(false);
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
                results.getItems().clear();
                statusResults.getItems().clear();
                setElementsVisibleIfFound();
                for (Book book : books) {
                    results.getItems().add(book.getTitle());
                    if (book.getBookStatus().equals(BookStatus.FREE)) {
                        statusResults.getItems().add("ДОСТУПНА ДЛЯ БРОНИ");
                    } else if (book.getBookStatus().equals(BookStatus.BOOKED)) {
                        statusResults.getItems().add("ЗАБРОНИРОВАНА");
                    } else {
                        statusResults.getItems().add("У ЧИТАТЕЛЯ");
                    }
                }
            }
            else {
                setElementsInvisibleIfNothingFound();
            }
        } else if (Objects.equals(output, "Названию")) {
            Book book = restTemplate.getForEntity(BOOK_API + "title?title=" + title, Book.class).getBody();
            if (book != null) {
                setElementsVisibleIfFound();
                results.getItems().clear();
                statusResults.getItems().clear();
                results.getItems().addAll(book.getTitle());

                switch (book.getBookStatus()) {
                    case FREE:
                        statusResults.getItems().add("ДОСТУПНА ДЛЯ БРОНИ");
                        break;
                    case BOOKED:
                        statusResults.getItems().add("ЗАБРОНИРОВАНА");
                        break;
                    case READING:
                        statusResults.getItems().addAll("У ЧИТАТЕЛЯ");
                        break;
                }
            } else {
                setElementsInvisibleIfNothingFound();
            }
        } else {
            Book[] response = restTemplate.getForObject(BOOK_API + "genre?genre=" + searchField.getText(), Book[].class);
            List<Book> books = Arrays.asList(response);

            if (books.size() > 0) {
                setElementsVisibleIfFound();
                results.getItems().clear();
                statusResults.getItems().clear();
                for (Book book : books) {
                    results.getItems().add(book.getTitle());
                    if (book.getBookStatus().equals(BookStatus.FREE)) {
                        statusResults.getItems().add("ДОСТУПНА ДЛЯ БРОНИ");
                    } else if (book.getBookStatus().equals(BookStatus.BOOKED)) {
                        statusResults.getItems().add("ЗАБРОНИРОВАНА");
                    } else {
                        statusResults.getItems().add("У ЧИТАТЕЛЯ");
                    }
                }
            } else {
                setElementsInvisibleIfNothingFound();
            }
        }
    }

    private void setElementsInvisibleIfNothingFound() {
        results.setVisible(false);
        statusLabel.setVisible(false);
        statusResults.setVisible(false);
        searchLabel.setVisible(false);
        nothingFoundText.setVisible(true);
    }

    private void setElementsVisibleIfFound() {
        results.setVisible(true);
        statusLabel.setVisible(true);
        statusResults.setVisible(true);
        searchLabel.setVisible(true);
        nothingFoundText.setVisible(false);
    }


    public void goBack(ActionEvent event) throws Exception {
        Stage stage = (Stage) back.getScene().getWindow();

        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/mainPage.fxml"));
        Parent root1 = fxmlLoader.load();
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setScene(new Scene(root1));
        stage.show();
    }
}
