package ru.ivmiit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.ivmiit.model.Author;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AdminController extends BaseController implements Initializable {

    @FXML
    private TextField title;
    @FXML
    private TextField genre;
    @FXML
    private ComboBox authorsComboBox;
    @FXML
    private Button back;

    private final String ADD_BOOK_API = "http://localhost:80/books/add";
    private final String AUTHORS_API = "http://localhost:80/authors";
    private final String FIND_AUTHOR_API= "http://localhost:80/author";
    public static final String ADD_URL = "fxml/adminPanel.fxml";


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RestTemplate restTemplate = new RestTemplate();

        Author[] response = restTemplate.getForObject(AUTHORS_API, Author[].class);
        List<Author> authors = Arrays.asList(response);

        for (Author author : authors) {
            authorsComboBox.getItems().addAll(author.getName() + " " + author.getLastName());
        }

    }

    public void addBook(ActionEvent event) throws Exception {
        String title = this.title.getText();
        String genre = this.genre.getText();
        String authorSelected = authorsComboBox.getSelectionModel().getSelectedItem().toString();


        RestTemplate template = new RestTemplate();

        Author author = template.getForEntity(FIND_AUTHOR_API + "?name=" + authorSelected.split(" ")[0] +
                "&lastname=" + authorSelected.split(" ")[1], Author.class).getBody();

        ObjectMapper mapper = new ObjectMapper();
        String authorJson = mapper.writeValueAsString(author);

        String jsonStringBook = "{"
                + "\"title\":" + "\"" + title + "\","
                + "\"genre\":" + "\"" + genre + "\","
                + "\"author\":" + authorJson + "}";

        System.out.println(jsonStringBook);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(jsonStringBook, headers);

        template.postForEntity(ADD_BOOK_API, entity, String.class);
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
