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
    private Text regtext;
    @FXML
    private TextField login;
    @FXML
    private TextField email;
    @FXML
    private TextField password;
    @FXML
    private Text searchLogin;
    @FXML
    private Text searchEmail;
    @FXML
    private Text searchPassword;
    @FXML
    private Button regButton;
    @FXML
    private Text error;
    @FXML
    private Button back;

    private RestTemplate restTemplate;

    private final String REG_API = "http://localhost:80/registration/";
    private LoginForm loggedUser;
    public static final String REG_URL = "fxml/registration.fxml";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        error.setVisible(false);
        back.setOnAction(event -> {
            Main.getNavigation().GoBack();
        });

    }

    public void register(ActionEvent event) throws Exception {

        String log = login.getText();
        System.out.println(log);
        String pas = password.getText();
        String em = email.getText();

        if (log.isEmpty() && pas.isEmpty() && em.isEmpty()) {
            regtext.setVisible(false);
            error.setVisible(true);
        }
        if (pas != null && em != null) {
            LoginForm form = new LoginForm(log, pas, em);

            RestTemplate restTemplate = new RestTemplate();

            String jsonStringUser = "{"
                    + "\"login\":" + "\"" + log + "\","
                    + "\"password\":" + "\"" + pas + "\","
                    + "\"email\":" + "\"" + em + "\""
                    + "}";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<String>(jsonStringUser, headers);

            String answer = restTemplate.postForObject(REG_API, entity, String.class);
//            System.out.println(answer);


            //Close current
            Stage stage = (Stage) regButton.getScene().getWindow();
            // do what you have to do
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/mainPage.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Главная");
            stage.setScene(new Scene(root1));
            stage.show();
        }
    }



}
