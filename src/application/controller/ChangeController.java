package application.controller;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ChangeController {

    @FXML Label oldLabel;
    @FXML TextField changeTextField;

    public void setScene(String oldValue){
        oldLabel.setText(oldValue);
    }

}
