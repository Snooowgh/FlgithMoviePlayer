package app;

import app.model.Movie;
import app.model.MovieSystem;
import app.simpleMediaPlayer.SimpleMediaPlayer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.Arrays;
import java.util.Date;

/**
 * Controller is responsible for managing the all the communication between
 * the model and the GUI in the MVC design pattern.
 */
public class Controller extends Application {
    private MovieSystem mMovieSystem;

    public Controller(){
        mMovieSystem = new MovieSystem();
    }

    /**
     * The main function that gets run first
     * @param args pass the arguments from main()
     */
    public void run(String[] args){
        // TODO: We should load the movies from DB automatically here
        // Create a test movie and add it to the system
        Movie theAfricaQueen = new Movie(
                "The African Queen",
                "1951-02-02",
                Arrays.asList("Comedy"),
                "TheAfricanQueen_us_1951.mp4"
                );
        mMovieSystem.addMovie(theAfricaQueen);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //创建测试窗口
        primaryStage.setTitle("Test Meida");
        Group root = new Group();
        BorderPane pane = new BorderPane();
        root.getChildren().add(pane);
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        pane.setBottom(hbox);
        Button popup = new Button("Popup");
        hbox.getChildren().addAll(popup);
        popup.setOnAction((ActionEvent e)->{
            SimpleMediaPlayer.popup(getClass().getResource("TestMedia.MP4").toString());
        });
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
