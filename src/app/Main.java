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
            Parent root = FXMLLoader.load(getClass().getResource("User_Interface.fxml"));
            primaryStage.setTitle("Flight Entertainment System");
            primaryStage.getIcons().add(new Image(getClass().getResource("../pictures/logo.jpg").toExternalForm()));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("../styles/tabStyle1.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        // SimpleMediaPlayer.popup(getClass().getResource("TestMedia.MP4").toString());
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
