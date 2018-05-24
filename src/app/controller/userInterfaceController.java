package app.controller;

import app.simpleMediaPlayer.SimpleMediaPlayer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class userInterfaceController implements Initializable {
    @FXML
    Tab Tab_Chinese;
    @FXML
    ImageView topImageview;
    @FXML
    ListView movieListview;
    @FXML
    ChoiceBox languageChoicebox;
    @FXML
    TilePane Tab_Chinese_Content;
    @FXML
    TilePane Tab_English_Content;
    @FXML
    TilePane Tab_Japanese_Content;

    @FXML
    public void Tab_Japanese_Content_selected(){
    }
    @FXML
    public void Tab_English_Content_selected(){

    }
    @FXML
    public void Tab_Chinese_Content_selected(){
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initialize top image
        java.net.URL url = getClass().getResource("../data/pictures/topImage.jpg");
        if (url!=null)
            topImageview.setImage(new Image(url.toString()));
        //initialize category list
        ObservableList<String> data = FXCollections.observableArrayList("Action","Commedy","Documentary","Romance","VLog");
        movieListview.setItems(data);
        movieListview.getSelectionModel().selectFirst();
        ObservableList<String> languages = FXCollections.observableArrayList("简体中文","English","日本语");
        languageChoicebox.setItems(languages);
        languageChoicebox.getSelectionModel().selectFirst();
        //initialize content
        Tab_Japanese_Content.getChildren().add(createSingleMovie("Japanese Movies","../data/pictures/topImage2.jpg","../TestMedia.MP4"));
        Tab_English_Content.getChildren().add(createSingleMovie("English Movies","../data/pictures/topImage.jpg","../TestMedia.MP4"));
        Tab_Chinese_Content.getChildren().add(createSingleMovie("样例电影","../data/pictures/topImage3.jpg","../TestMedia.MP4"));
        Tab_Chinese_Content.getChildren().add(createSingleMovie("样例电影2","../data/pictures/topImage2.jpg","../TestMedia.MP4"));
    }

    private VBox createSingleMovie(String movieName,String imageUrl,String movieUrl){
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
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setAlignment(Pos.BOTTOM_CENTER);
        hBox.getChildren().add(imageView);
        hBox.getChildren().add(new Label(movieName));
        return hBox;
    }
}
