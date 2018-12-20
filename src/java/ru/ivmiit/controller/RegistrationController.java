package ru.ivmiit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import ru.ivmiit.app.Main;
import ru.ivmiit.model.form.LoginForm;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController extends BaseController implements Initializable {
    @FXML
    public Text regtext;
    @FXML
    public Text searchLogin;
    @FXML
    public Text searchPassword;
    @FXML
    public Text searchEmail;
    @FXML
    public TextField email;
    @FXML
    private TextField login;
    @FXML
    private TextField password;
    @FXML
    private Button regButton;
    /*@FXML
    private Text error;
    @FXML
    private Button back;
*/
    static final String REG_URL = "fxml/registration.fxml";
    private final String REG_API = "http://localhost:80/registration/";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*error.setVisible(false);
        back.setOnAction(event -> Main.getNavigation().GoBack());*/
    }

    public void register(ActionEvent event) throws Exception {
        String login = this.login.getText();
        String password = this.password.getText();

        if (login.isEmpty() || password.isEmpty()) {
            //error.setVisible(true);
            return;
        }

        RestTemplate restTemplate = new RestTemplate();

        String jsonStringUser = "{"
                + "\"login\":" + "\"" + login + "\","
                + "\"password\":" + "\"" + password + "\""
                + "}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(jsonStringUser, headers);

        restTemplate.postForObject(REG_API, entity, String.class);

        //Close current
        Stage stage = (Stage) regButton.getScene().getWindow();
        // do what you have to do
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/authorization.fxml"));
        Parent root1 = fxmlLoader.load();
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Главная");
        stage.setScene(new Scene(root1));
        stage.show();
    }
}
