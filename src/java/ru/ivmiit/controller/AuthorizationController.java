package ru.ivmiit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;
import ru.ivmiit.app.Main;
import ru.ivmiit.dto.TokenDto;
import ru.ivmiit.model.form.LoginForm;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthorizationController extends BaseController implements Initializable {

    public static final String AUTH_API = "http://localhost:80/login/";

    public static final String REG_URL = "registration.fxml";

    @Autowired
    private AuthenticationManager authenticationManager;
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;
    @FXML
    private Label label;
    @FXML
    private Button buttonAuth;
    @FXML
    private Button buttonReg;

    private RestTemplate restTemplate;

    private final Stage thisStage;

    public AuthorizationController() {
        thisStage = new Stage();
    }

    /**
     * Show the stage that was loaded in the constructor
     */
    public void showStage() {
        thisStage.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.restTemplate = new RestTemplate();
    }

    @FXML
    public void login() throws Exception {
        String authenticationData = "{"
                + "\"login\":" + "\"" + login.getText() + "\","
                + "\"password\":" + "\"" + password.getText() + "\""
                + "}";

        System.out.println(authenticationData);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(authenticationData, headers);

        TokenDto token = restTemplate.postForObject(AUTH_API, entity, TokenDto.class);

        if (token.getToken() != null) {
            Stage stage = (Stage) buttonAuth.getScene().getWindow();
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

    @FXML
    public void reg() {
        buttonReg.setOnAction(event -> Main.getNavigation().load(RegistrationController.REG_URL).Show());
    }
}
