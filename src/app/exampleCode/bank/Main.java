package app.exampleCode.bank;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
	private static String UI = "UI.fxml";

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource(UI));
		Scene frame = new Scene(root);
		primaryStage.isResizable();
		primaryStage.setTitle("Bank Business");
		primaryStage.setScene(frame);
		primaryStage.centerOnScreen();
		primaryStage.show();
		Thread.sleep(1000);
		Stage ss = new Stage();
        ss.setScene(new Scene(createContent()));
        ss.show();
//        primaryStage.hide();
	}

    public Parent createContent() {
        //create a button for initializing our new stage
        Button button = new Button("Create a Stage");
        button.setStyle("-fx-font-size: 24;");
        button.setDefaultButton(true);
        button.setOnAction((ActionEvent t) -> {
            final Stage stage = new Stage();

            //create root node of scene, i.e. group
            Group rootGroup = new Group();

            //create scene with set width, height and color
            Scene scene = new Scene(rootGroup, 200, 200, Color.WHITESMOKE);

            //set scene to stage
            stage.setScene(scene);

            //set title to stage
            stage.setTitle("New stage");

            //center stage on screen
            stage.centerOnScreen();

            //show the stage
            stage.show();

            //add some node to scene
            Text text = new Text(20, 110, "JavaFX");
            text.setFill(Color.DODGERBLUE);
            text.setEffect(new Lighting());
            text.setFont(Font.font(Font.getDefault().getFamily(), 50));

            //add text to the main root group
            rootGroup.getChildren().add(text);
        });
        return button;
    }
	public static void main(String[] args) {
		launch(args);
	}
}
