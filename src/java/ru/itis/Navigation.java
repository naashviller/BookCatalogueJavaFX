package ru.itis;

import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ru.itis.controller.BaseController;

public class Navigation {

  private final Scene scene;

  private List<BaseController> controllers = new ArrayList<>();

  public Navigation(Stage stage) {
    scene = new Scene(new Pane());
    stage.setScene(scene);
  }

  public BaseController load(String sUrl) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(sUrl));
      Node root = fxmlLoader.load();

      BaseController controller = fxmlLoader.getController();
      controller.setView(root);

      return controller;

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public void Show(BaseController controller) {
    try {
      scene.setRoot((Parent) controller.getView());
      controllers.add(controller);

      System.out.println(
          "Add to history: " + controller.toString() + ". Total scenes: " + controllers.size());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

