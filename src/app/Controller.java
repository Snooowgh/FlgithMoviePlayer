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
        Button popup2 = new Button("Popup small");
        hbox.getChildren().addAll(popup,popup2);

        SimpleMediaPlayer player = SimpleMediaPlayer.newInstance(getClass().getResource("TestMedia.MP4").toString());
        pane.setCenter(player);
        pane.setAlignment(player,Pos.CENTER);


        //测试弹窗式调用
        popup.setOnAction((ActionEvent e)->{
            SimpleMediaPlayer.popup(getClass().getResource("TestMedia.MP4").toString());
        });
        popup2.setOnAction((ActionEvent e)->{
            SimpleMediaPlayer.popup(getClass().getResource("TestMedia.MP4").toString(),550,400);
        });


        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();
    }

    private String getFirstFileName(){
        String MovieSource_Path = "./MovieSource";
        File f = new File(MovieSource_Path);
        String name = "";
        if (f.isDirectory()){
            File[] fs = f.listFiles();
            for (int i = 0; i < fs.length; i++) {
                System.out.println(fs[i].getName());
                name = fs[i].getName();
            }
        }else{
            f.mkdir();
        }
        return name;
    }

}
