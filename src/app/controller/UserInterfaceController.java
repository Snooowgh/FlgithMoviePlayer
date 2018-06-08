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
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;
import java.net.URL;
import java.util.*;
import javafx.event.ActionEvent;
import app.data.SystemData;


class Category{
    Image imageURL;
    String describe;
    public Category(String imageURL,String describe){
        this.imageURL = new Image(getClass().getResource(imageURL).toExternalForm()) ;
        this.describe = describe ;
    }
}

public class UserInterfaceController implements Initializable {

    @FXML
    public ChoiceBox<String> languageChoiceBox;
    @FXML
    public Label timeLabel;
    @FXML
    public Label languageLabel;
    @FXML
    public Label timeLeft;
    @FXML
    public Pane flightPane;
    @FXML
    public Button categoryL;
    @FXML
    public Button categoryR;
    @FXML
    public HBox category;
    @FXML
    public HBox movielist;


    private MovieSystem movieSystem;

    //by default show English movie
    private String countryChoose = "English";
    private String categoryChoose = "Action";
    private HashMap<Category,String[]> globalCategoryCountries;
    private HashMap<String, TabPane> categoryTabPaneHashMap ;
    //private HashMap<String[],TilePane> moviesReadyToShow;//String[] is a String[2], the first is category, the second is video language
    // TODO initialize second TabPane, do not hard code
    private ObservableList<String> languages = FXCollections.observableArrayList("简体中文", "English", "日本语");


    //TODO parameter will be Category s instead of void, then it will be not longer hard code
    private HashMap<Category,String[]> category_nits_countries(){
        HashMap<Category,String[]> hm = new HashMap<>();
        hm.put(new Category("../../pictures/littlePlane.png","Comedy"),new String[]{"English","简体中文"});
        hm.put(new Category("../../pictures/littlePlane.png","Movie"),new String[]{"English","简体中文","日本语"});
        hm.put(new Category("../../pictures/littlePlane.png","Action"),new String[]{"English","简体中文"});
        hm.put(new Category("../../pictures/littlePlane.png","a"),new String[]{"English","简体中文"});
        hm.put(new Category("../../pictures/littlePlane.png","b"),new String[]{"English","简体中文"});
        hm.put(new Category("../../pictures/littlePlane.png","c"),new String[]{"English","简体中文"});
        hm.put(new Category("../../pictures/littlePlane.png","d"),new String[]{"English","简体中文"});
        hm.put(new Category("../../pictures/littlePlane.png","e"),new String[]{"English","简体中文"});
        hm.put(new Category("../../pictures/littlePlane.png","f"),new String[]{"English"});

        return hm;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Load the movies from CSV

        movieSystem = new MovieSystem();
        movieSystem.loadMoviesFromCSV(getClass().getResource("../../movie-list.csv"));
        languageChoiceBox.setItems(languages);
        languageChoiceBox.getSelectionModel().select(SystemData.language);
		languageChoiceBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SystemData.setDefaultLanguage(languageChoiceBox.getValue().toString());
				System.out.println("changed to "+languageChoiceBox.getValue().toString());
			}
			});
        setCategory();

//      fill flight time in second here
		createFlightLineView(SystemData.FlightTime);
    }
    
    private void initializeCategory(){
        ObservableList<VBox> ob = FXCollections.observableArrayList();
        for(Category c: globalCategoryCountries.keySet()){
            ob.add(createSingleCategory(c));
        }
        //category= new HBox(globalCategoryCountries.size());
        category.getChildren().addAll(ob);

    }

    private void initializeCountryAndLoadMovieList(){
        Tab t;
        for(Category c:globalCategoryCountries.keySet()){
            TabPane tmp= new TabPane();
            tmp.setSide(Side.valueOf("TOP"));
            tmp.setPrefSize(1235, 470);
            tmp.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
            categoryTabPaneHashMap.put(c.describe,tmp);
            for(String s: globalCategoryCountries.get(c)){
                t = new Tab();
                t.setText(s);
                tmp.getTabs().add(t);
            }
            //tmp.getSelectionModel().selectFirst();//by default selete the first tab
            tmp.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    countryChoose = tmp.getSelectionModel().getSelectedItem().getText();
                    showMovieWhenClickTab(tmp);
                }
            });
        }
    }

    private void setCategory() {
        //TODO please deal with languages
    	ObservableList<String> countries= FXCollections.observableArrayList(movieSystem.getUniqueCategories());
       
        //TODO please return from MovieSystem rather than hard code
        globalCategoryCountries = category_nits_countries();

        //load category
        initializeCategory();


        //load countries and initialize movies in every TilePane
        categoryTabPaneHashMap = new HashMap<>();
        initializeCountryAndLoadMovieList();

        //set default TilePane
        //movielist.getChildren().clear();
        Category defaultCategory = globalCategoryCountries.keySet().iterator().next();
        categoryTabPaneHashMap.get(defaultCategory.describe).getSelectionModel().selectFirst();
        showMovieWhenClickTab(categoryTabPaneHashMap.get(defaultCategory.describe));

    }



    @SuppressWarnings("static-access")
	private void showMovieWhenClickTab(TabPane movies) {
    	
    	//TODO job1
    	//List<Movie> ms = movieSystem.getMovieByTwocategory(categoryChoose,countryChoose);
        List<Movie> ms = movieSystem.getMovieByTwocategory(categoryChoose,categoryChoose);
        // by default show the first page within 12 videos
        Iterator<Movie> ims = ms.iterator();
        VBox vb = new VBox();
        //TODO every page, there will be 2*4 movies, implement page function is needed
        for (int i = 0; i < 3; i++) {
        	HBox h = new HBox();
        	for (int j = 0; j < 5; j++) {
        		if (ims.hasNext()) {
                    Movie tmpm = ims.next();
                    h.getChildren().add(createSingleMovie(tmpm));
                    //h.setAlignment(Pos.CENTER);
                } else
                    break;
        	}
            vb.getChildren().add(h);
            vb.setAlignment(Pos.CENTER);
        }
        AnchorPane a =new AnchorPane();
        a.getChildren().add(vb);
        //a.seta
        movies.getSelectionModel().getSelectedItem().setContent(a);
        movielist.getChildren().clear();
        movielist.getChildren().add(movies);
        movielist.setAlignment(Pos.TOP_LEFT);
    }

    

    private void createFlightLineView(int flightTime){
        ImageView imageView = new ImageView(new Image(getClass().getResource("../../pictures/littlePlane.png").toExternalForm()));
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

    private  VBox createSingleCategory(Category c){
        ImageView imageView = new ImageView(c.imageURL);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	categoryChoose = c.describe;
                movielist.getChildren().clear();
                categoryTabPaneHashMap.get(c.describe).getSelectionModel().selectFirst();
                showMovieWhenClickTab(categoryTabPaneHashMap.get(c.describe));
            }
        });
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setAlignment(Pos.BOTTOM_CENTER);
        vBox.getChildren().add(imageView);
        vBox.getChildren().add(new Label(c.describe));
        vBox.setId("movieCategory");
        return vBox;
    }

    private VBox createSingleMovie(Movie movie) {
        ImageView imageView = new ImageView(new Image(movie.getImageFileURL().toExternalForm()));
        imageView.setFitHeight(110);
        imageView.setFitWidth(184);
//        imageView.setOnMouseClicked(event -> SimpleMediaPlayer.popup(movie.getMovieFileURL().toString()));
        imageView.setOnMouseClicked(event -> MovieDetailView.showDetail(movie));
        VBox vBox = new VBox();
        //hBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setAlignment(Pos.BOTTOM_CENTER);
        vBox.getChildren().add(imageView);
        vBox.getChildren().add(new Label(movie.getTitle()));
        vBox.setId("movieView");
		vBox.setPadding(new Insets(10));
		//System.out.println(vBox.getWidth()+"\t"+vBox.getHeight()+"\n");
        return vBox;
    }
}