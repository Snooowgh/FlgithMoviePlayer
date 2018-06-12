package app.controller;

import app.data.I18N;
import app.data.SystemData;
import app.model.Movie;
import app.model.MovieSystem;
import app.view.movieDetailView.MovieDetailView;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;


/**
 * 
 * Description: class Category
 * 				category of video means actions, comedy and so on
 */
class Category{
    final Image icon;
    final String categoryName;
    
    /**
     * 
     * @param imageRelativePath e.g.  "../../pictures/Movie.JPG"
     * @param categoryname
     */
    public Category(String imageRelativePath,String categoryname){
        this.icon = new Image(getClass().getResource(imageRelativePath).toExternalForm()) ;
        this.categoryName = categoryname ;
    }
    /**
     * Icon address will be automatically initialized as "../../pictures/"+categoryname+".JPG" 
     * @param categoryname
     */
    public Category(String categoryname){
    	this.categoryName = categoryname ;
    	this.icon = new Image(getClass().getResource("../../pictures/"+categoryname+".JPG").toExternalForm()) ;
    }
}
/**
 * Description: class UserInterfaceController
 * 				class that 
 * 				  1.dynamically loading videos, 
 * 				    including dynamically loading its categories and languages
 * 				  2.handle mouse click even
 * 				  3.load basic widgets  
 * @author Ben, Snow
 * @2018年6月10日上午1:48:13
 */
public class UserInterfaceController implements Initializable {

    @FXML
    public ChoiceBox<String> languageChoiceBox;

    @FXML
    public Pane flightPane;
    /**
     * click on this, get movies of previous page  
     */
    @FXML
    public Button prePage;
    /**
     * click on this, get movies of next page  
     */
    @FXML
    public Button nextPage;
    /**
     * show the current page of movies
     */
    @FXML
    public Label page;
    /**
     * include some categories, every category is a imageView with Text
     * category example:
     * 		icon
     * 		text
     */
    @FXML
    public HBox category;
    /**
     * what ever category, video language, pages number the user choose
     * movies will add to this HBox with a max magnitude of 15
     */
    @FXML
    public HBox movielist;


    private int currentPageNum;
    /**
     * computed as movie size
     */
    private int maxPageNum;
    private TabPane currentTabPane;
    private ArrayList<Movie> currentMovies;

    private MovieSystem movieSystem;

    //by default show English Action movie
    private String videoLanguageChoose = "English";
    private String categoryChoose = "Action";
 
    private HashMap<String,Set<String>> categoryCountryHashMap;
    private HashMap<String, TabPane> categoryTabPaneHashMap ;

    private ObservableList<String> languages = SystemData.getSupportedLanguage();
    
//    private final Background movieListBackgroundBlack = 
//    		new Background(new BackgroundFill(Color.web("#000000"),CornerRadii.EMPTY,Insets.EMPTY));
//    private final Background movieListBackgroundWhite = 
//    		new Background(new BackgroundFill(Color.web("#F4F4F4"),CornerRadii.EMPTY,Insets.EMPTY));
    public boolean debug = true;

    /** number of language switches. */
    private Integer numSwitches = 0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Load the movies from CSV
        try {
            movieSystem = new MovieSystem();
            movieSystem.loadMoviesFromCSV(getClass().getResource("../../movie-list.csv"));
            languageChoiceBox.setItems(languages);
            languageChoiceBox.getSelectionModel().select(SystemData.language);
            languageChoiceBox.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    SystemData.setDefaultLanguage(languageChoiceBox.getValue().toString());
                    numSwitches++;
                    if(debug)
                    	System.out.println("changed to " + languageChoiceBox.getValue().toString());
                }
            });
            initializeMovieListScene();
            //set pre button and next button handler
            prePage.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(currentPageNum>1){
                    	currentPageNum--;
                    	if(debug)
                    		System.out.println("click button pre, jump to page "+currentPageNum);
                    	showMovieWhenClickTab();
                    }
                }
            });
            nextPage.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(currentPageNum<maxPageNum){
                    	currentPageNum++;
                    	if(debug)
                    		System.out.println("click button next, jump to page "+currentPageNum);
                    	showMovieWhenClickTab();
                    }
                }
            });
            //fill flight time in second here
            createFlightLineView(SystemData.FlightTime);
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }
    /**
     * based on the movie we have initialize category
     */
    //TODO deal with unknown category as Others
    private void initializeCategory(){
        ObservableList<VBox> ob = FXCollections.observableArrayList();
        for(String c: categoryCountryHashMap.keySet()){
            ob.add(createSingleCategory(new Category(c)));
        }
        //category= new HBox(globalCategoryCountries.size());
        category.getChildren().addAll(ob);

    }

    private void initializeCountryAndLoadMovieList(){
        Tab t;
        for(String c:categoryCountryHashMap.keySet()){
            TabPane tmp= new TabPane();
            tmp.setSide(Side.valueOf("TOP"));
            tmp.setPrefSize(1235, 470);
            tmp.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
            categoryTabPaneHashMap.put(c,tmp);
            for(String s: categoryCountryHashMap.get(c)){
                t = new Tab();
                t.setText(s);
                //t.getStyleClass().add(e)
                tmp.getTabs().add(t);
            }
            //tmp.getSelectionModel().selectFirst();//by default selete the first tab
            tmp.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                	if(!videoLanguageChoose.equals(tmp.getSelectionModel().getSelectedItem().getText())){
                		videoLanguageChoose = tmp.getSelectionModel().getSelectedItem().getText();
                		currentTabPane = tmp;
                		prepareForAnotherMovieList();
                		showMovieWhenClickTab();
                	}
                }
            });
        }
    }

    private void initializeMovieListScene() {
    	//for example
    	//<Comedy,{English,Chinese}>,<Horror,{Japanese,English}>
        categoryCountryHashMap = movieSystem.getCategoriesCountriesHashMap();

        //load category to the scene
        initializeCategory();
       
        //build TabPane for every category, which means each category owns a TabPane 
        categoryTabPaneHashMap = new HashMap<>();
        initializeCountryAndLoadMovieList();
        
        //by on default value(categoryChoose and videoCreated),
        //give default movie-list of page 1 to user to choose  
        currentTabPane = categoryTabPaneHashMap.get(categoryChoose);
        //If we don't have a default video language for default category
        //select first
        currentTabPane.getSelectionModel().selectFirst();
        //try to select the special video language for default category
        for(Tab t:currentTabPane.getTabs()){
        	if(t.getText().equals(videoLanguageChoose)){
        		currentTabPane.getSelectionModel().select(t);
        		if(debug)
        			System.out.printf("successfully find defualt video language(%s) of default category(%s)\n",
        							videoLanguageChoose, categoryChoose);
        		break;
        	}
        }
        String choosen = currentTabPane.getSelectionModel().getSelectedItem().getText();
        if(debug){
        	if(choosen.equals(videoLanguageChoose))
        		System.out.printf("successfully load defualt video language(%s) of default category(%s)\n",
        							videoLanguageChoose, categoryChoose);
        	else
        		System.err.printf("failed to find defualt video language(%s) of default category(%s)\n"
        				+ "choosen video language: %s\n",
						videoLanguageChoose, categoryChoose, choosen);
        }
        currentMovies = (ArrayList<Movie>) movieSystem.getMovieByTwocategory(
        		categoryChoose,
        		choosen
        		);
        maxPageNum = (currentMovies.size()-1)/15;
        if(maxPageNum<0){
        	maxPageNum = 1;
        }
        if(debug)
        	System.out.printf("get %d pages movies\n",maxPageNum);
        //show movie list
        currentPageNum =1;
        showMovieWhenClickTab();
    }

    /**
     * movies are in a HBox
     * actually 5 movies in a HBox h1, 
     * 			2 h1 in a VBox v1, 
     * 			v1 is in a HBox so that it displays better
     */
	private void showMovieWhenClickTab() {

        //by default show the first page within 15 videos
        VBox vb = new VBox();
        
        page.setText(Integer.toString(currentPageNum));
        //0-14 15-29 30-44
        int which = (currentPageNum-1)*15;
        //every page, there will be 3*5 movies, implement page function is needed
       
      //currentTabPane.getSelectionModel().getSelectedItem().
    	for (int i = 0; i < 3; i++) {
        	HBox h = new HBox();
        	for (int j = 0; j < 5; j++) {
        		if (which<currentMovies.size()) {
                    Movie tmpm = currentMovies.get(which++);
                    h.getChildren().add(createSingleMovie(tmpm));
                } else
                    break;
        	}
        	//h.setBackground(movieListBackgroundBlack);
            vb.getChildren().add(h);
           
        }	
    	   
   
        if(debug)
			System.out.printf("load %d movie for this page\n",which-(currentPageNum-1)*15);
//        AnchorPane a =new AnchorPane();
//        a.getChildren().add(vb);
        vb.setAlignment(Pos.TOP_LEFT);
        currentTabPane.getSelectionModel().getSelectedItem().setContent(vb);
        movielist.getChildren().clear();
        movielist.getChildren().add(currentTabPane);
        movielist.setAlignment(Pos.TOP_CENTER);
    }

    

    private void createFlightLineView(int flightTime){
        ImageView imageView = new ImageView(new Image(getClass().getResource("../../pictures/littlePlane.png").toExternalForm()));
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);
        Rectangle rect = new Rectangle(0, 0, 40, 40);
        rect.setArcHeight(5);
        rect.setArcWidth(5);
        rect.setFill(Color.ORANGE);
        Path path = new Path(new MoveTo(30, 25),new LineTo(40,25),new LineTo(550,25));
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
    
    private void prepareForAnotherMovieList(){
    	currentMovies = (ArrayList<Movie>) movieSystem.getMovieByTwocategory(categoryChoose,videoLanguageChoose);
        currentPageNum =1;
        maxPageNum = (currentMovies.size()-1)/15;
        if(maxPageNum<0)
        	maxPageNum = 1; 
        if(debug)
        	System.out.printf("get %d pages movies",maxPageNum);
    }
    
    private  VBox createSingleCategory(Category c){
        ImageView imageView = new ImageView(c.icon);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	if(!categoryChoose.equals(c.categoryName)){
            		categoryChoose = c.categoryName;
            		currentTabPane = categoryTabPaneHashMap.get(c.categoryName);
            		videoLanguageChoose = categoryCountryHashMap.get(c.categoryName).iterator().next();
            		currentTabPane.getSelectionModel().selectFirst();
            		prepareForAnotherMovieList();
            		showMovieWhenClickTab();
            	}
            }
        });
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setAlignment(Pos.BOTTOM_CENTER);
        vBox.getChildren().add(imageView);
        vBox.getChildren().add(I18N.labelForValue(() -> I18N.get("label."+c.categoryName, c.categoryName)));
        vBox.setId("movieCategory");
        System.out.println(c.categoryName);
        return vBox;
    }

    private VBox createSingleMovie(Movie movie) {
        ImageView imageView = new ImageView(new Image(movie.getImageFileURL().toExternalForm()));
        imageView.setFitHeight(110);
        imageView.setFitWidth(184);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(imageView);
        Label l = new Label(movie.getTitle());
        l.setAlignment(Pos.BOTTOM_CENTER);
        vBox.getChildren().add(l);
        vBox.setId("movieView");
		vBox.setPadding(new Insets(10));
		vBox.setOnMouseClicked(event -> MovieDetailView.showDetail(movie));
        return vBox;
    }



}