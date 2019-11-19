package application.controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeController {

    private Stage stage;
    @FXML Label oldLabel;
    @FXML TextField changeTextField;

    public void start(Stage stage, String labelName) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/ControllChange.fxml"));
        Parent root = loader.load();
        oldLabel.setText(labelName);
        //This part is loader to controller
        ChangeController controller = loader.getController();
        controller.initialize(stage);
        stage.setScene(new Scene(root, 300, 200));
        stage.setTitle("Controller Change");
        stage.show();
    }

    public void setScene(String oldValue){
        oldLabel.setText(oldValue);
    }

    public void initialize(Stage stage){ this.stage = stage; }
}
