package app.view.movieDetailView;

import app.model.Movie;
import app.view.simpleMediaPlayer.SimpleMediaPlayer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.net.URL;
import java.util.ResourceBundle;

public class movieDetailController{
    @FXML
    Label directorLabel;
    @FXML
    Label actorsLabel;
    @FXML
    Label releasedLabel;


    @FXML
    Label directorInfoLabel;
    @FXML
    Label actorsInfoLabel;
    @FXML
    Label releasedInfoLabel;
    @FXML
    TextArea  detailText;
    @FXML
    ImageView imageView;


    public void start(Movie movie) {
        directorInfoLabel.setText("John Reese");
        actorsInfoLabel.setText("abc askldj askdl asd ");
        releasedInfoLabel.setText("1992");
        detailText.setText("a man go up");
        imageView.setImage(new Image(movie.getImageFileURL().toString()));
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                SimpleMediaPlayer.popup(movie.getMovieFileURL().toString());
            }
        });
    }
}