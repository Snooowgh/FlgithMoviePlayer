package app.controller;

import app.model.Movie;
import app.model.MovieSystem;
import app.simpleMediaPlayer.SimpleMediaPlayer;
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

    HashMap<Tab, TabPane> hm = new HashMap<Tab, TabPane>();

    // **********************************************************************************
    String movie_Category[] = { "Home", "Action", "Comedy", "Documentary", "Romance", "VLog" };
    String country_Category[] = { "All", "America", "China", "French", "Japan", "Russia", "Others" };
    // ***********************************************************************************

    // initialize second tabpane
    ObservableList<String> languages = FXCollections.observableArrayList("简体中文", "English", "日本语");

    private void setCategory() {
        // please deal with languages
        languageChoiceBox.setItems(languages);
        languageChoiceBox.getSelectionModel().selectFirst();
        for (String m : movie_Category) {
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
            hm.put(t, tmp);
            // System.out.println(tmp.toString());//
            firTab.getTabs().add(t);
        }
    }

    private TreeSet<Movie> returnMovies(String firstChoice, String secondChoice) {
        // daniel write this or write it in MovieSystem class
        // public Movie(String title,String imageURL,String movieURL, String
        // fileName)
        // "English Movies","../data/pictures/topImage.jpg","../TestMedia.MP4"
        Movie m = new Movie("English Movies", "../data/pictures/topImage.jpg",
                "../TestMedia.mp4"
                //"C:\\Users\\asus\\Desktop\\FlgithMoviePlayer-master\\src\\app\\TestMedia.mp4"
        );
        TreeSet<Movie> tm = new TreeSet<Movie>();
        tm.add(m);
        return tm;
    }

    private void tapHandle() {
        firTab.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showMovieWhenClickTab(hm.get(firTab.getSelectionModel().getSelectedItem()));

            }
        });

    }

    private void showMovieWhenClickTab(TabPane secTab) {

        TreeSet<Movie> ms = returnMovies(firTab.getSelectionModel().getSelectedItem().getText(),
                secTab.getSelectionModel().getSelectedItem().getText());
        // by default show the first page within 12 videos
        Iterator<Movie> ims = ms.iterator();
        TilePane tp = new TilePane();

        for (int i = 0; i < 12; i++) {
            if (ims.hasNext()) {
                Movie tmpm = ims.next();
                // System.out.printf("%s\n%s\n%s",tmpm.getTitle(),
                // tmpm.getImageURL(),//here, Daniel provide a imageUrl and
                // movieUrl
                // tmpm.getFileName());
                tp.getChildren().add(createSingleMovie(tmpm.getTitle(), tmpm.getImageURL(), // here,
                        // Daniel
                        // provide
                        // a
                        // imageUrl
                        // and
                        // movieUrl
                        tmpm.getFileName()));
            } else
                break;
        }

        secTab.getSelectionModel().getSelectedItem().setContent(tp);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        try {
//            URL url = new URL("./movie-list.csv");
//        } catch (MalformedURLException e) {
//
//        }
//        File csvFile = new File("movie-list.csv");
//        if (csvFile.exists())
//            movieSystem.loadMoviesFromCSV();
        // initialize category list
        setCategory();
        firTab.getSelectionModel().selectFirst();
        TabPane secTabi1 = hm.get(firTab.getSelectionModel().getSelectedItem());
        secTabi1.getSelectionModel().selectFirst();
        showMovieWhenClickTab(secTabi1);
        tapHandle();
//      fill flight time in second here
        createFlightLineView(10);
    }

    private void createFlightLineView(int flightTime){
        ImageView imageView = new ImageView(new Image(getClass().getResource("../littlePlane.jpg").toExternalForm()));
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

    private VBox createSingleMovie(String movieName, String imageUrl, String movieUrl) {
        java.net.URL url = getClass().getResource(imageUrl);
        ImageView imageView = new ImageView(new Image(url.toString()));
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                SimpleMediaPlayer.popup(getClass().getResource(movieUrl).toString());
            }
        });
        VBox hBox = new VBox();
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setAlignment(Pos.BOTTOM_CENTER);
        hBox.getChildren().add(imageView);
        hBox.getChildren().add(new Label(movieName));
        hBox.setId("movieView");
        return hBox;
    }
}
