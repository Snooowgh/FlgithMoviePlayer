
package app;

import app.data.I18N;
import app.data.SystemData;
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
            SystemData.init();
            Parent root = FXMLLoader.load(getClass().getResource("User_Interface.fxml"));
            primaryStage.titleProperty().bind(I18N.createStringBinding("title"));
            primaryStage.getIcons().add(new Image(getClass().getResource("../pictures/logo.png").toExternalForm()));
            Scene scene = new Scene(root);
            String defaultStyle;
            switch (SystemData.prop.getProperty("userInterface")){
                case "red":
                    defaultStyle = "Style_red.css";
                    break;
                case "black":
                    defaultStyle = "Style_black.css";
                    break;
                default:
                     defaultStyle= "tabStyle1.css";
            }
            scene.getStylesheets().add(getClass().getResource("../styles/"+defaultStyle).toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setResizable(false) ;
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
