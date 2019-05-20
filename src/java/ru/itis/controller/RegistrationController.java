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
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

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
    private PasswordField password;
    @FXML
    private Button regButton;


    static final String REG_URL = "fxml/registration.fxml";
    private final String REG_API = "http://localhost:80/registration/";

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    public void register(ActionEvent event) throws Exception {

        String login = this.login.getText();
        String email = this.email.getText();
        String password = this.password.getText();
        String p = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{8,}$";//пароль
        String em = "^[\\w-_.]*[\\w-_.]@[\\w].+[\\w]+[\\w]$";//email


        if (!password.matches(p)) {
            Label informPassword = new Label(" Строка должна быть не меньше 8 символов, " +
                    "содержать как минимум 1 большую букву, одну маленькую букву и ЛИБО спец. символ ЛИБО цифру.");
            StackPane pasLayout = new StackPane();
            pasLayout.getChildren().add(informPassword);
            Scene pasScene = new Scene(pasLayout);
            Stage windowPas = new Stage();
            windowPas.setTitle("Ограничение на пароль");
            windowPas.setScene(pasScene);
            windowPas.initModality(Modality.APPLICATION_MODAL);
            windowPas.initOwner(((Node) event.getSource()).getScene().getWindow());
            windowPas.setX(((Node) event.getSource()).getScene().getWindow().getX() + 100);
            windowPas.setY(((Node) event.getSource()).getScene().getWindow().getY() + 100);
            windowPas.show();


            windowPas.setOnCloseRequest((WindowEvent windowEvent) -> {
                //Close current
                Stage stage = (Stage) regButton.getScene().getWindow();
                stage.show();
            });
        } else if (!email.matches(em)) {
            Label informEmail = new Label(" Email должен быть действительным и содержать символы до @ и после.");
            StackPane emLayout = new StackPane();
            emLayout.getChildren().add(informEmail);
            Scene emScene = new Scene(emLayout);
            Stage windowEm = new Stage();
            windowEm.setTitle("Ограничение на email");
            windowEm.setScene(emScene);
            windowEm.initModality(Modality.APPLICATION_MODAL);
            windowEm.initOwner(((Node) event.getSource()).getScene().getWindow());
            windowEm.setX(((Node) event.getSource()).getScene().getWindow().getX() + 100);
            windowEm.setY(((Node) event.getSource()).getScene().getWindow().getY() + 100);
            windowEm.show();
            windowEm.setOnCloseRequest((WindowEvent windowEvent) -> {
                //Close current
                Stage stage = (Stage) regButton.getScene().getWindow();
                stage.show();
            });
        } else {
            RestTemplate restTemplate = new RestTemplate();

            String jsonStringUser = "{"
                    + "\"login\":" + "\"" + login + "\","
                    + "\"email\":" + "\"" + email + "\","
                    + "\"password\":" + "\"" + password + "\""
                    + "}";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(jsonStringUser, headers);

            restTemplate.postForObject(REG_API, entity, String.class);
            Label secondLabel = new Label("Вы успешно зарегистрировались!");
            StackPane secondaryLayout = new StackPane();
            secondaryLayout.getChildren().add(secondLabel);
            Scene secondScene = new Scene(secondaryLayout, 230, 100);
            // New window (Stage)
            Stage newWindow = new Stage();
            newWindow.setTitle("Регистрация успешна");
            newWindow.setScene(secondScene);
            // Specifies the modality for new window.
            newWindow.initModality(Modality.WINDOW_MODAL);
            // Specifies the owner Window (parent) for new window
            newWindow.initOwner(((Node) event.getSource()).getScene().getWindow());
            // Set position of second window, related to primary window.
            newWindow.setX(((Node) event.getSource()).getScene().getWindow().getX() + 200);
            newWindow.setY(((Node) event.getSource()).getScene().getWindow().getY() + 100);
            newWindow.show();

            newWindow.setOnCloseRequest((WindowEvent windowEvent) -> {
                //Close current
                Stage stage = (Stage) regButton.getScene().getWindow();
                // do what you have to do
                stage.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/authorization.fxml"));
                Parent root1 = null;
                try {
                    root1 = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Главная");
                stage.setScene(new Scene(root1));
                //pause.play();
                stage.show();
            });

        }
    }
}

