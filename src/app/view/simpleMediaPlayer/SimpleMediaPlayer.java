package app.view.simpleMediaPlayer;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * Description:    Media Player
 */
public class SimpleMediaPlayer extends AnchorPane {


    private static SimpleMediaPlayer simpleMediaPlayer;
    private PlayerController controller;


    private SimpleMediaPlayer(String mediaUrl) {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("player.fxml"));
            Parent root = fxmlloader.load();
            controller = fxmlloader.getController();
            this.getChildren().add(root);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static SimpleMediaPlayer newInstance(String mediaUrl) {
        return newInstance(mediaUrl, 600, 400);
    }

    public static SimpleMediaPlayer newInstance(String mediaUrl, int width, int height) {
        simpleMediaPlayer = new SimpleMediaPlayer(mediaUrl);
        simpleMediaPlayer.getController().start(mediaUrl, false, width, height);
        return simpleMediaPlayer;
    }

    public static SimpleMediaPlayer popup(String mediaUrl) {
        return popup(mediaUrl, 800, 600);
    }

    public static SimpleMediaPlayer popup(String mediaUrl, int width, int height) {
        simpleMediaPlayer = new SimpleMediaPlayer(mediaUrl);
        simpleMediaPlayer.getController().start(mediaUrl, true, width, height);
        Scene scene = new Scene(simpleMediaPlayer, width, height);
        simpleMediaPlayer.getController().setScene(scene);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Media Player");
        primaryStage.setScene(scene);


        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                simpleMediaPlayer.getController().destroy();
            }
        });
        primaryStage.show();
        return simpleMediaPlayer;
    }

    protected PlayerController getController() {
        return this.controller;
    }

    public void setSize(int width, int height) {
        if (simpleMediaPlayer.getController().getPopup())
            return;
        simpleMediaPlayer.getController().setMediaPlayer(width, height);
    }
}
