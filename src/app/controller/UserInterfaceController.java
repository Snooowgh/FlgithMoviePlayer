package app.controller;

import app.data.I18N;
import app.data.SystemData;
import app.model.DBManager;
import app.model.Movie;
import app.model.MovieSystem;
import app.view.movieDetailView.MovieDetailView;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
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
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;


/**
 * Description: class Category
 * category of video means actions, comedy and so on
 *
 * @author Ben
 */
class Category {
    final Image icon;
    final String categoryName;
    static final String[] canHandelCategory = {
    	"ACTION","ADVENTURE","DRAMA","HORROR","CRIME","ANIMATION","ROMANCE","COMEDY","DETECTIVE","MUSICAL","SI-FI"	
    };
    public static Set<String> canHandelCategorySet = new TreeSet<>(); 
    public static Set<String> cannotHandelCategorySet = new TreeSet<>();
    /**
     * @param imageRelativePath e.g.  "../../pictures/Movie.JPG"
     * @param categoryname
     */
    public Category(String imageRelativePath, String categoryname) {
        this.icon = new Image(getClass().getResource(imageRelativePath).toExternalForm());
        this.categoryName = categoryname;
    }

    public static  HashMap<String,Set<String>> removeCanntHandelToOthers(HashMap<String,Set<String>> hm){
    	HashMap<String,Set<String>> newhm = new HashMap<>();
    	for(String chc: canHandelCategory)
    		canHandelCategorySet.add(chc);
    	for(String s :hm.keySet()){
    		if(canHandelCategorySet.contains(s.toUpperCase())){
    			newhm.put(s, hm.get(s));
    		}else{
    			cannotHandelCategorySet.add(s);
    			System.out.println("cannot\t"+s);
    			if(newhm.containsKey("Others"))
    				newhm.get("Others").add(s);
    			else
    				newhm.put("Others", hm.get(s));
    		}
    	}
    	return newhm;
    }
    
    /**
     * Icon address will be automatically initialized as "../../pictures/"+categoryname+".JPG"
     *
     * @param categoryname
     */
    public Category(String categoryname) {
        this.categoryName = categoryname;
        System.out.println(categoryname);
        this.icon = new Image(getClass().getResource("../../pictures/" + categoryname + ".JPG").toExternalForm());
    }
}

/**
 * Description: class UserInterfaceController
 * class that
 * 1.dynamically loading videos,
 * including dynamically loading its categories and languages
 * 2.handle mouse click even
 * 3.load basic widgets
 *
 * @author Ben, Snow
 * @2018年6月10日上午1:48:13
 */
public class UserInterfaceController implements Initializable {
    public static Scene mainScene;
    /**
     * choose system language here
     */
    @FXML
    public ChoiceBox<String> languageChoiceBox;
    @FXML
    public ComboBox<Color> skinChoiceBox;
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
     * icon
     * text
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
    private String videoLanguageChoose = "English";
    private String categoryChoose = "Action";
    private HashMap<String, Set<String>> categoryCountryHashMap;
    private HashMap<String, TabPane> categoryTabPaneHashMap;
    private ObservableList<String> languages = SystemData.getSupportedLanguage();
    private HashMap<String, Set<Movie>> otherMovies = new HashMap<>();
    /**
     * number of language switches.
     */
    private Integer numSwitches = 0;
    private Label lastChoosenLabel = null;

    /**
     * 1. load movies from MovieSystem
     * 2. load language choiceBox, deal with handler
     * 3. load category, deal with handler
     * 4. based on default category, load movies to the scene, deal with handler
     * 5. deal with previous page and next page buttons' click on handler
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            movieSystem = new MovieSystem();


            // Write new movies to CSV if any
            // TODO: Uncomment the two lines below to use the new CSV file format to save the movies correctly
            // TODO: If the movie data is loaded from the internet with the below func the categories break
            // TODO: as there is no image for some categories. Provide a default image!
            DBManager dbManager = new DBManager(getClass().getResource("../../movie-list.csv"));
            dbManager.writeNewMoviesDataToCSV();

            // TODO: Remove this line!!!
            //DBManager dbManager = new DBManager(getClass().getResource("../../movie-list.csv.bak"));

            // Set the movies the system from CSV
            movieSystem.setMovies(dbManager.getMoviesFromCSV());
            languageChoiceBox.setItems(languages);
            languageChoiceBox.getSelectionModel().select(SystemData.language);
            languageChoiceBox.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    SystemData.setDefaultLanguage(languageChoiceBox.getValue().toString());
                    numSwitches++;

                }
            });

            skinChoiceBox.getItems().addAll(SystemData.supportedColor);
            skinChoiceBox.setCellFactory(new Callback<ListView<Color>, ListCell<Color>>() {
                @Override
                public ListCell<Color> call(ListView<Color> p) {
                    return new ListCell<Color>() {
                        private final Rectangle rectangle;

                        {
                            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                            rectangle = new Rectangle(10, 10);
                        }

                        @Override
                        protected void updateItem(Color item, boolean empty) {
                            super.updateItem(item, empty);

                            if (item == null || empty) {
                                setGraphic(null);
                            } else {
                                rectangle.setFill(item);
                                setGraphic(rectangle);
                            }
                        }
                    };
                }
            });
            skinChoiceBox.getSelectionModel().selectFirst();
            skinChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Color>() {
                @Override
                public void changed(ObservableValue<? extends Color> observable,
                                    Color oldValue, Color newValue) {
                    if (!oldValue.equals(newValue)) {
                        if (mainScene != null) {
                            mainScene.getStylesheets().clear();
                            try {
                                mainScene.getStylesheets().add(getClass().getResource("../../styles/Style_" + SystemData.transColor(newValue.toString()) + ".css").toExternalForm());
                            } catch (Exception e) {
                                System.out.println("style conflict ignored");
                            }
                        }
                    }

                }
            });
            initializeMovieListScene();

            prePage.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (currentPageNum > 1) {
                        currentPageNum--;

                        showMovieWhenClickTab();
                    }
                }
            });
            nextPage.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (currentPageNum < maxPageNum) {
                        currentPageNum++;
                        showMovieWhenClickTab();
                    }
                }
            });

            createFlightLineView(SystemData.flightTime);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * based on the movie we have initialize category
     */

    private void initializeCategory() {
        ObservableList<VBox> ob = FXCollections.observableArrayList();
        for (String c : categoryCountryHashMap.keySet()) {
        	if(!c.equals("Others"))
        		ob.add(createSingleCategory(new Category(c)));
        }
        if(categoryCountryHashMap.keySet().contains("Others"))
        	ob.add(createSingleCategory(new Category("Others")));
        category.getChildren().addAll(ob);
    }

    /**
     * each category has a TabPane
     * TabPanes are different based on the video we have
     * e.g. if we have the video of HongKong Action
     * then the TabPane of "Action" will has a tab called HongKong
     * and this video will be show in the content of this tab
     */
    private void initializeCountryAndLoadMovieList() {
        Tab t;
        for (String c : categoryCountryHashMap.keySet()) {
            TabPane tmp = new TabPane();
            tmp.setSide(Side.valueOf("TOP"));
            tmp.setPrefSize(1235, 470);
            tmp.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
            categoryTabPaneHashMap.put(c, tmp);
            for (String s : categoryCountryHashMap.get(c)) {
                t = new Tab();
                t.setText(s);

                tmp.getTabs().add(t);
            }

            tmp.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (!videoLanguageChoose.equals(tmp.getSelectionModel().getSelectedItem().getText())) {
                        videoLanguageChoose = tmp.getSelectionModel().getSelectedItem().getText();
                        currentTabPane = tmp;
                        prepareForAnotherMovieList();
                        showMovieWhenClickTab();
                    }
                }
            });
        }
    }

    /**
     * load videos to the scene based on the default setting
     */
    private void initializeMovieListScene() {


        categoryCountryHashMap = movieSystem.getCategoriesCountriesHashMap();
        categoryCountryHashMap = Category.removeCanntHandelToOthers(categoryCountryHashMap);
        
        //System.out.println(categoryCountryHashMap.keySet().toString());

        initializeCategory();


        categoryTabPaneHashMap = new HashMap<>();
        initializeCountryAndLoadMovieList();
        
        Set<Movie> ms = new TreeSet<>();
        for(String cannot:Category.canHandelCategorySet){
        	ms.addAll(movieSystem.getMoviesByCategory(cannot));
        }
        if(categoryCountryHashMap.get("Others")!=null)
        	for(String otherTab: categoryCountryHashMap.get("Others")){
            	for(Movie m: ms){
            		if(m.getCategories().contains(otherTab)){
            			if(otherMovies.containsKey(otherTab)){
            				otherMovies.get(otherTab).add(m);
            			}else{
            				Set<Movie> ts = new TreeSet<>();
            				ts.add(m);
            				otherMovies.put(otherTab,ts);
            			}
            		}
            	}
            }
        
        if(categoryCountryHashMap.keySet().contains(categoryChoose))
        	currentTabPane = categoryTabPaneHashMap.get(categoryChoose);
        else
        	currentTabPane = categoryTabPaneHashMap.get(categoryCountryHashMap.keySet().iterator().next());

        currentTabPane.getSelectionModel().selectFirst();

        for (Tab t : currentTabPane.getTabs()) {
            if (t.getText().equals(videoLanguageChoose)) {
                currentTabPane.getSelectionModel().select(t);
                break;
            }
        }
        String choosen = currentTabPane.getSelectionModel().getSelectedItem().getText();
        
        if(choosen.equals("Others")){
        	currentMovies.clear();
        	currentMovies.addAll(otherMovies.get(videoLanguageChoose));
        }
        else
        	currentMovies = (ArrayList<Movie>) movieSystem.getMovieByTwocategory(
                categoryChoose,
                choosen
        			);
        
        maxPageNum = (currentMovies.size() - 1) / 15;
        if (maxPageNum <= 0) {
            maxPageNum = 1;
        }

        currentPageNum = 1;
        showMovieWhenClickTab();
    }

    /**
     * 15 movies are in a HBox each time
     * actually 5 movies in a HBox h1,
     * 2 h1 in a VBox v1,
     * v1 is in a HBox in order to displays better
     */
    private void showMovieWhenClickTab() {


        VBox vb = new VBox();

        page.setText(Integer.toString(currentPageNum));

        int which = (currentPageNum - 1) * 15;


        for (int i = 0; i < 3; i++) {
            HBox h = new HBox();
            for (int j = 0; j < 5; j++) {
                if (which < currentMovies.size()) {
                    Movie tmpm = currentMovies.get(which++);
                    h.getChildren().add(createSingleMovie(tmpm));
                } else
                    break;
            }

            vb.getChildren().add(h);

        }


        vb.setAlignment(Pos.TOP_LEFT);
        currentTabPane.getSelectionModel().getSelectedItem().setContent(vb);
        movielist.getChildren().clear();
        movielist.getChildren().add(currentTabPane);
        movielist.setAlignment(Pos.TOP_CENTER);
    }


    /**
     * a plane and a path's pendant,
     * shows the percentage of how much far from the beginning and the ending
     *
     * @param flightTime the roughly time needed for arrival
     */
    private void createFlightLineView(int flightTime) {
        ImageView imageView = new ImageView(new Image(getClass().getResource("../../pictures/littlePlane.png").toExternalForm()));
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);
        Rectangle rect = new Rectangle(0, 0, 40, 40);
        rect.setArcHeight(5);
        rect.setArcWidth(5);
        rect.setFill(Color.ORANGE);
        Path path = new Path(new MoveTo(30, 25), new LineTo(40, 25), new LineTo(550, 25));
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

    /*
     * when click on a tab of counties or click on a category
     * need to get corresponding movies from the movieSystem
     * and do some preparation
     */
    private void prepareForAnotherMovieList() {
    	if(videoLanguageChoose.equals("Others")){
        	currentMovies.clear();
        	currentMovies.addAll(otherMovies.get(videoLanguageChoose));
        }
        else
        currentMovies = (ArrayList<Movie>) movieSystem.getMovieByTwocategory(categoryChoose, videoLanguageChoose);
        currentPageNum = 1;
        maxPageNum = (currentMovies.size() - 1) / 15;
        if (maxPageNum <= 0)
            maxPageNum = 1;
    }

    /**
     * @param c including an image and text
     * @return a VBox that
     * the  first child is an image, which is a icon
     * the second child is text, which represents the category
     */
    private VBox createSingleCategory(Category c) {
    	//System.out.println("create "+c.categoryName);
        ImageView imageView = new ImageView(c.icon);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setAlignment(Pos.BOTTOM_CENTER);
        vBox.getChildren().add(imageView);
        Label l1 = I18N.labelForValue(() -> I18N.get("label." + c.categoryName, c.categoryName));
        vBox.getChildren().add(l1);
        vBox.setId("movieCategory");
        vBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!categoryChoose.equals(c.categoryName)) {
                    if (lastChoosenLabel != null) {
                        lastChoosenLabel.setTextFill(Color.BLACK);
                    }
                    lastChoosenLabel = l1;
                    l1.setTextFill(Color.GREEN);
                    categoryChoose = c.categoryName;
                    currentTabPane = categoryTabPaneHashMap.get(c.categoryName);
                    videoLanguageChoose = categoryCountryHashMap.get(c.categoryName).iterator().next();
                    currentTabPane.getSelectionModel().selectFirst();
                    prepareForAnotherMovieList();
                    showMovieWhenClickTab();
                }
            }
        });
        
        return vBox;
    }

    /**
     * @param movie
     * @return a VBox that
     * the  first child is an image, which is something like a poster of the video
     * the second child is text, which is the video name
     */
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