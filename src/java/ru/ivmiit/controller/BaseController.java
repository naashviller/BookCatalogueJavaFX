package ru.ivmiit.controller;

import javafx.scene.Node;
import org.springframework.stereotype.Controller;
import ru.ivmiit.app.Main;

import java.lang.annotation.Annotation;

public class BaseController implements Controller {
    private Node view;

    @Override
    public String value() {
        return null;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }


    public Node getView() {
        return view;
    }

    public void setView(Node view) {
        this.view = view;
    }

    public void Show() {
        PreShowing();
        Main.getNavigation().Show(this);
        PostShowing();
    }

    public void PreShowing() {
    }

    public void PostShowing() {
    }
}

