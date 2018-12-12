package ru.ivmiit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
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

public class CatalogueController extends BaseController implements Initializable {

    private final String BOOK_API = "http://localhost:80/books";

    @FXML
    private ListView results;

    @FXML
    private ListView statusResults;

    @FXML
    private Button back;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(new MappingJackson2HttpMessageConverter());
        RestTemplate restTemplate = new RestTemplate(converters);

        Book[] response = restTemplate.getForObject(BOOK_API, Book[].class);
        List<Book> books = Arrays.asList(response);

        if (books.size() > 0) {
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

