package ru.itis.controller;

import java.lang.annotation.Annotation;
import javafx.scene.Node;
import org.springframework.stereotype.Controller;
import ru.itis.app.Main;

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

