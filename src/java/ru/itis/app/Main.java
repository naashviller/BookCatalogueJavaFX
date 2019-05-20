package ru.itis.app;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.itis.Navigation;

public class Main extends Application {

  private static Navigation navigation;

  public static Navigation getNavigation() {
    return navigation;
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    navigation = new Navigation(primaryStage);

    primaryStage.setTitle("Библиотека");
    primaryStage.show();
    primaryStage.setMaxHeight(400);
    primaryStage.setMinHeight(400);
    primaryStage.setMaxWidth(600);
    primaryStage.setMinWidth(600);
    primaryStage.setResizable(false);

    Main.getNavigation().load("fxml/authorization.fxml").Show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
