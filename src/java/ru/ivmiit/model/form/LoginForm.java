package ru.ivmiit.model.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class LoginForm {
    private String login;
    private String email;
    private String password;

    public LoginForm(String login, String email, String password) {
        this.login = login;
        this.email = email;
        this.password = password;
    }

}
