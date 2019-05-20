package ru.itis.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import ru.itis.app.Main;
import ru.itis.dto.TokenDto;
import ru.itis.util.AuthenticationUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthorizationController extends BaseController implements Initializable {

    public static final String AUTH_API = "http://localhost:80/login/";

    public static final String REG_URL = "registration.fxml";

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

    public void login(ActionEvent event) throws Exception {
        String authenticationData = "{"
                + "\"login\":" + "\"" + login.getText() + "\","
                + "\"password\":" + "\"" + password.getText() + "\""
                + "}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(authenticationData, headers);
        TokenDto token;

        try {
            token = restTemplate.postForObject(AUTH_API, entity, TokenDto.class);
        } catch (Exception e) {
            token = null;
        }

        if (token != null) {
            AuthenticationUtil.token = token.getToken();
            Stage stage = (Stage) buttonAuth.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/mainPage.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Главная");
            stage.setScene(new Scene(root1));
            stage.show();
        } else {
            Label wrongEmPas = new Label("Неправильный логин или пароль");
            StackPane emPaLayout = new StackPane();
            emPaLayout.getChildren().add(wrongEmPas);
            Scene emScene = new Scene(emPaLayout);
            Stage windowEmPas = new Stage();
            windowEmPas.setTitle("Ограничение на email");
            windowEmPas.setScene(emScene);
            windowEmPas.initModality(Modality.APPLICATION_MODAL);
            windowEmPas.initOwner(((Node) event.getSource()).getScene().getWindow());
            windowEmPas.setX(((Node) event.getSource()).getScene().getWindow().getX() + 100);
            windowEmPas.setY(((Node) event.getSource()).getScene().getWindow().getY() + 100);
            windowEmPas.show();
            windowEmPas.setOnCloseRequest((WindowEvent windowEvent) -> {
                //Close current
                Stage stage = (Stage) buttonAuth.getScene().getWindow();
                stage.show();
            });
        }
    }

    @FXML
    public void reg() {
        buttonReg
                .setOnAction(event -> Main.getNavigation().load(RegistrationController.REG_URL).Show());
    }
}
