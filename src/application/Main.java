// This was just a quick and dirty prototype to hint at what's possible
// Much of what's in here should be replaced with proper abstractions
package application;

import application.controller.MenuController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	Stage scene1;
	Stage scene2;

	@Override
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("view/MenuView.fxml"));
		Parent root = loader.load();
		MenuController controller = loader.getController();
		controller.initialize(primaryStage);
		primaryStage.setTitle("Space Brawl");
		primaryStage.setScene(new Scene(root, 800, 700));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
