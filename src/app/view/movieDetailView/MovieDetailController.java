package app.view.movieDetailView;

import app.model.Movie;
import app.model.MovieInfoLoader;
import app.view.simpleMediaPlayer.SimpleMediaPlayer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class MovieDetailController {
    @FXML
    Label directorLabel;
    @FXML
    Label actorsLabel;
    @FXML
    Label releasedLabel;
    @FXML
    Label categoryLabel;

    @FXML
    Label directorInfoLabel;
    @FXML
    Label actorsInfoLabel;
    @FXML
    Label releasedInfoLabel;
    @FXML
    Label categoryInfoLabel;
    @FXML
    TextArea detailText;
    @FXML
    ImageView imageView;


    public void start(Movie movie) {
        if (movie == null)
            return;
        Movie loadMovie = MovieInfoLoader.loadMovieInfo(movie);
        directorInfoLabel.setText(loadMovie.getTitle());
        actorsInfoLabel.setText(loadMovie.getActors().toString());
        releasedInfoLabel.setText(loadMovie.getReleaseDate());
        detailText.setText(loadMovie.getPlot());
        categoryInfoLabel.setText(movie.getCategories().toString());
        Image image = new Image(movie.getImageFileURL().toString());
        imageView.setImage(image);
        imageView.setFitHeight(297);
        imageView.setFitWidth(413);

        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                SimpleMediaPlayer.popup(movie.getMovieFileURL().toString());
            }
        });
    }
}
