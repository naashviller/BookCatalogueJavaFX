package ru.ivmiit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import ru.ivmiit.model.Book;
import ru.ivmiit.model.enums.BookStatus;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class BookedBooksController extends BaseController implements Initializable {
    @FXML
    private ListView results;
    @FXML
    private ListView authorsResult;
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

        if (books.size() > 0) {
            results.getItems().clear();
            authorsResult.getItems().clear();
            for (Book book : books) {
                if (book.getBookStatus().equals(BookStatus.BOOKED)) {
                    results.getItems().add(book.getTitle());
                    authorsResult.getItems().add(book.getAuthor().getName() + " " + book.getAuthor().getLastName());
                }
            }
        }
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
