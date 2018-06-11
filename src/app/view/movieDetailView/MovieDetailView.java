package app.view.movieDetailView;

import app.model.Movie;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MovieDetailView  extends AnchorPane {
    private static movieDetailController controller;
    private static MovieDetailView movieDetailView;
    public MovieDetailView(Movie movie){
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("language.information",Locale.CHINESE);
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("mediaDetail.fxml"),bundle);
            Parent root = fxmlloader.load();   
            controller = fxmlloader.getController();
            this.getChildren().add(root);  
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void showDetail(Movie movie){
        movieDetailView = new MovieDetailView(movie);
        controller.start(movie);
        Scene scene = new Scene(movieDetailView);
        Stage primaryStage = new Stage();
        primaryStage.setTitle(movie.getTitle());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
