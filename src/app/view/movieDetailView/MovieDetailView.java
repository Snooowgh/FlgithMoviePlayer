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

public class MovieDetailView  extends AnchorPane {
    private static movieDetailController controller;
    private static MovieDetailView movieDetailView;
    public MovieDetailView(Movie movie){
        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("mediaDetail.fxml"));
            Parent root = fxmlloader.load();   //将fxml节点添加到根节点中
            controller = fxmlloader.getController();
            this.getChildren().add(root);   //主类节点加入根节点
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
