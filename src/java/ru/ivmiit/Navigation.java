package ru.ivmiit;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ru.ivmiit.controller.BaseController;

import java.util.ArrayList;
import java.util.List;

public class Navigation {
    private Stage stage = new Stage();
    private final Scene scene;

    private List<BaseController> controllers = new ArrayList<>();


    public Navigation(Stage stage) {
        this.stage = stage;
        scene = new Scene(new Pane());
        stage.setScene(scene);
    }


    public BaseController load(String sUrl) {
        try {

            //loads the fxml file
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

            System.out.println("Add to history: " + controller.toString() + ". Total scenes: " + controllers.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GoBack() {
        if (controllers.size() > 1) {
            controllers.remove(controllers.get(controllers.size() - 1));
            scene.setRoot((Parent) controllers.get(controllers.size() - 1).getView());
        }

        System.out.println("GoBack: " + controllers.get(controllers.size() - 1).toString() + ". Total scenes: " + controllers.size());
    }


    public void ClearHistory() {
        while (controllers.size() > 1)
            controllers.remove(0);

        System.out.println("ClearHistory. Total scenes: " + controllers.size());
    }
}

