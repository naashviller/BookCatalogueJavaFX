package ru.ivmiit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;
import ru.ivmiit.app.Main;

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
    private TextField password;
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


    }

    @FXML
    public void login(ActionEvent event) throws Exception {

        Authentication authentication = new UsernamePasswordAuthenticationToken(login.getText(), password.getText());

        try {
            authentication = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException e) {
            label.setText("Login failure, please try again:");
            label.setTextFill(Color.RED);
        }


    }

    @FXML
    public void reg() {
        buttonReg.setOnAction(event -> {
            Main.getNavigation().load(RegistrationController.REG_URL).Show();
        });
    }


}
