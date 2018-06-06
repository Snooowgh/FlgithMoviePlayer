package app.controller;

import app.model.Movie;
import app.model.MovieSystem;
import app.view.movieDetailView.MovieDetailView;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

public class UserInterfaceController implements Initializable {
    @FXML
    public TabPane firTab;
    @FXML
    public ChoiceBox languageChoiceBox;
    @FXML
    public Label timeLabel;
    @FXML
    public Label languageLabel;
    @FXML
    public Label timeLeft;
    @FXML
    public Pane flightPane;

    private MovieSystem movieSystem;

    private HashMap<Tab, TabPane> firTabAnditsSecTab = new HashMap<Tab, TabPane>();

    // TODO: Load this from movie system
    private String country_Category[] = { "简体中文", "English", "日本语" };

    // initialize second tabpane
    private ObservableList<String> languages = FXCollections.observableArrayList("简体中文", "English", "日本语");

    private void setCategory() {
        // please deal with languages
        languageChoiceBox.setItems(languages);
        languageChoiceBox.getSelectionModel().selectFirst();

        // Load the category from the movie list
        for (String m : movieSystem.getUniqueCategories()) {
            Tab t = new Tab();
            t.setText(m);
            t.setStyle("");
            // prefHeight="540.0"
            // prefWidth="725.0"
            // side="LEFT"
            // tabClosingPolicy="UNAVAILABLE"
            // AnchorPane.bottomAnchor="0.0"
            // AnchorPane.leftAnchor="0.0"
            // AnchorPane.rightAnchor="0.0"
            // AnchorPane.topAnchor="0.0"
            TabPane tmp = new TabPane();
            tmp.prefHeight(540.0);
            tmp.prefWidth(725.0);
            tmp.setSide(Side.valueOf("LEFT"));
            tmp.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
            for (String c : country_Category) {
                Tab t2 = new Tab();
                t2.setText(c);
                tmp.getTabs().add(t2);
            }
            tmp.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    showMovieWhenClickTab(tmp);
                }
            });
            // AnchorPane.leftAnchor="0.0"
            // AnchorPane.rightAnchor="0.0"
            // AnchorPane.topAnchor="0.0"
            t.setContent(tmp);
            firTabAnditsSecTab.put(t, tmp);
            // System.out.println(tmp.toString());//
            firTab.getTabs().add(t);
        }
    }

    private void tapHandle() {
        firTab.setOnMouseClicked(event -> showMovieWhenClickTab(firTabAnditsSecTab.get(firTab.getSelectionModel().getSelectedItem())));
    }

    private void showMovieWhenClickTab(TabPane secTab) {
            // TODO: Modify that to get the movies you are interested in
            List<Movie> ms = movieSystem.getMovieByTwocategory( firTab.getSelectionModel().getSelectedItem().getText(),
                                                                secTab.getSelectionModel().getSelectedItem().getText() );

            // by default show the first page within 12 videos
            // TODO: Why are you using iterators instead of a for loop?
            Iterator<Movie> ims = ms.iterator();
            TilePane tp = new TilePane();

            for (int i = 0; i < 12; i++) {
                if (ims.hasNext()) {
                    Movie tmpm = ims.next();

                    tp.getChildren().add(createSingleMovie(tmpm));
                } else
                    break;
            }

            secTab.getSelectionModel().getSelectedItem().setContent(tp);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Load the movies from CSV
        movieSystem = new MovieSystem();
        movieSystem.loadMoviesFromCSV(getClass().getResource("../../movie-list.csv"));
        setCategory();
        firTab.getSelectionModel().selectFirst();
        TabPane secTabi1 = firTabAnditsSecTab.get(firTab.getSelectionModel().getSelectedItem());
        secTabi1.getSelectionModel().selectFirst();
        showMovieWhenClickTab(secTabi1);
        tapHandle();
//      fill flight time in second here
        createFlightLineView(10);
    }

    private void createFlightLineView(int flightTime){
        ImageView imageView = new ImageView(new Image(getClass().getResource("../../pictures/littlePlane.jpg").toExternalForm()));
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);
        Rectangle rect = new Rectangle(0, 0, 40, 40);
        rect.setArcHeight(5);
        rect.setArcWidth(5);
        rect.setFill(Color.ORANGE);
        Path path = new Path(new MoveTo(30, 25),new LineTo(40,25),new LineTo(260,25));
        path.setStroke(Color.DODGERBLUE);
        path.getStrokeDashArray().setAll(5d, 5d);
        PathTransition transition = new PathTransition(Duration.seconds(flightTime), path, imageView);
        transition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        transition.setCycleCount(Timeline.INDEFINITE);
        transition.setAutoReverse(true);
        flightPane.getChildren().add(path);
        flightPane.getChildren().add(imageView);
        transition.play();
    }

    private VBox createSingleMovie(Movie movie) {
        ImageView imageView = new ImageView(new Image(movie.getImageFileURL().toString()));
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
//        imageView.setOnMouseClicked(event -> SimpleMediaPlayer.popup(movie.getMovieFileURL().toString()));
        imageView.setOnMouseClicked(event -> MovieDetailView.showDetail(movie));
        VBox hBox = new VBox();
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setAlignment(Pos.BOTTOM_CENTER);
        hBox.getChildren().add(imageView);
        hBox.getChildren().add(new Label(movie.getTitle()));
        hBox.setId("movieView");
        return hBox;
    }
}
