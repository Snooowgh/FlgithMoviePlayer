package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
        String MovieSource_Path = "./MovieSource";
        File f = new File(MovieSource_Path);
        if (f.isDirectory()){
            File[] fs = f.listFiles();
            for (int i = 0; i < fs.length; i++) {
                System.out.println(fs[i].getName());
            }
        }else{
            f.mkdir();
        }

    }
}
