package application.controller;

/**
 * a controller for the settingsMenu view.
 *
 * This object should be passed a GameSettings object at construction and should allow
 * for the user to make changes to the elements of this class to be used by GameController.
 */

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class SettingsMenuController {
    ChangeController controllerChange;
    @FXML Button rightButton;
    private Stage stage;

    public void rightClick(javafx.event.ActionEvent event) throws IOException{
        String rightValue = "D";
        controllerChange.setScene(rightValue);

    }

    //Start method is used to switch views
    public void start(Stage stage) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/Settings.fxml"));
        Parent root = loader.load();
        SettingsMenuController controller = loader.getController();
        controller.initialize(stage);
        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Settings Page");
        stage.show();
    }

    public void clickButton(ActionEvent event) {
    }

    public void initialize(Stage stage) {
        this.stage = stage;
    }

}
