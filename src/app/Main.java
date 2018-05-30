package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 创建测试窗口

        try {
            Parent root = FXMLLoader.load(getClass().getResource("./User_Interface.fxml"));
            primaryStage.setTitle("Title");
            primaryStage.getIcons().add(new Image(getClass().getResource("./logo.jpg").toExternalForm()));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("./tabStyle1.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        // SimpleMediaPlayer.popup(getClass().getResource("TestMedia.MP4").toString());
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
