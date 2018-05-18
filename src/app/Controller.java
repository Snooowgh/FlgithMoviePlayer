package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

/**
 * Controller is responsible for managing the all the communication between
 * the model and the GUI in the MVC design pattern.
 */
public class Controller extends Application {
    public void run(String[] args){
        launch(args);
        String MovieSource_Path = "./MovieSource";
        File f = new File(MovieSource_Path);
        if (f.isDirectory()){
            File[] fs = f.listFiles();
            for (int i = 0; i < fs.length; i++) {
                System.out.println(fs[i].getName());
            }
        } else{
            f.mkdir();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

}
