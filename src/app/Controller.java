package app;

import app.model.Movie;
import app.model.MovieSystem;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.Arrays;

/**
 * Controller is responsible for managing the all the communication between the
 * model and the GUI in the MVC design pattern.
 */
public class Controller extends Application {
    private MovieSystem movieSystem;

    public Controller() {
        movieSystem = new MovieSystem();
    }

    /**
     * The main function that gets run first
     *
     * @param args
     *            pass the arguments from main()
     */
    public void run(String[] args) {
        // TODO: We should load the movies from DB automatically here
        // Create a test movie and add it to the system
        Movie theAfricaQueen = new Movie("The African Queen", "1951-02-02", Arrays.asList("Comedy"),
                "TheAfricanQueen_us_1951.mp4");
        movieSystem.addMovie(theAfricaQueen);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
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
