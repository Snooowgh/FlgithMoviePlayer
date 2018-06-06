
package app;

import app.data.SystemData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 创建测试窗口

        try {
            switch (SystemData.language){
                case "English":
                    Locale.setDefault(Locale.ENGLISH);
                    break;
                case "简体中文":
                    Locale.setDefault(Locale.CHINA);
                    break;
                case "日本语":
                    Locale.setDefault(Locale.JAPAN);
                    break;
                default:
                    Locale.setDefault(Locale.ENGLISH);
            }
            ResourceBundle bundle = ResourceBundle.getBundle("language.information",Locale.getDefault());
            Parent root = FXMLLoader.load(getClass().getResource("User_Interface.fxml"),bundle);
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
