package ru.ivmiit.util;

import org.springframework.web.client.RestTemplate;

public class AuthenticationUtil {
    private static final String AUTH_API = "http://localhost:80/auth";

    public static boolean isAuthenticated() {
        RestTemplate restTemplate = new RestTemplate();

        String token = restTemplate.getForEntity(AUTH_API, String.class).toString();

        if (token.isEmpty()) {
            System.out.println("Token is empty");
        } else {
            System.out.println(token);
        }

        return !token.isEmpty();
    }
}
