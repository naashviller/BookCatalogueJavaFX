package ru.ivmiit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.ivmiit.app.Main;
import ru.ivmiit.model.Author;

import java.net.URL;
import java.util.ResourceBundle;

public class AddBookController extends BaseController implements Initializable {

    @FXML
    private TextField title;
    @FXML
    private TextField genre;
    @FXML
    private ComboBox comboBox;
    @FXML
    private Button back;
    @FXML
    private Button buttonAdd;
    private final String ADDBOOK_API = "http://localhost:80/addBooks";
    private final String AUTHOR_API = "http://localhost:80/author";
    public static final String ADD_URL = "fxml/addBookAdmin.fxml";


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        back.setOnAction(event -> {
            Main.getNavigation().GoBack();
        });

    }

    public void addBook(ActionEvent event) throws Exception {

        String title = this.title.getText();
        String genre = this.genre.getText();
        Author authorSelected = (Author) comboBox.getSelectionModel().getSelectedItem();

        RestTemplate template = new RestTemplate();

        ResponseEntity<Author> author = template.getForEntity(AUTHOR_API + "?name= "
                + authorSelected.getName() + "&lastname="
                + authorSelected.getLastName(), Author.class);

        String jsonStringBook = "{"
                + "\"title\":" + "\"" + title + "\","
                + "\"genre\":" + "\"" + genre + "\","
                + "\"author\":" + "\"" + author.toString() + "\""
                + "}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(jsonStringBook, headers);

        template.postForEntity(ADDBOOK_API, entity, String.class);
    }

}
