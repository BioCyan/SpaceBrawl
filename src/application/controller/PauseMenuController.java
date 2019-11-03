package application.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.SubScene;
import javafx.stage.Stage;

/**
 * a controller for the pause menu
 *
 * should be called by a KeyEvent handler in the GameController object. Will display a menu based on
 * an .fxml and will give the option to quit the game or to open the settings menu.
 */
public class PauseMenuController {


    private Stage stage;
    public PauseMenuController(Stage stage) {
        this.stage = stage;

    }


    @FXML
    void resume(ActionEvent e)
    {

    }
    @FXML
    void settings(ActionEvent e)
    {

    }
    @FXML
    void menu(ActionEvent e)
    {

    }
    @FXML
    void quit(ActionEvent e)
    {

    }



}
