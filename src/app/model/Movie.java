package app.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale.Category;
import java.util.Set;
import java.util.TreeSet;

import javafx.scene.chart.CategoryAxis;

/**
 * Movie represents a single movie
 *
 * @author Daniel Babbev
 */
public class Movie implements Comparable<Movie> {
    private int id;
    private String title;
    private String releaseDate;
    private Set<String> oringinalCategory;
    private Set<String> category;
    private String movieFileName;
    private String imageFileName;
    private String rating;
    private String plot;
    private List<String> languages;
    private List<String> directors;
    private List<String> actors;
    private List<String> countries;
    private boolean loaded;
    
    //TODO it is not si-fi, it is Sci-fi
    //TODO some problem in translating system, 
    //TODO Snow should fix it: category  change from si-fi to Sci-fi
    //TODO after that, change the si-fi.JPG into Sci-fi.JPG
    final static String[] defaultCategory ={
    		"ACTION","ADVENTURE","DRAMA","HORROR","CRIME","ANIMATION","ROMANCE","COMEDY","DETECTIVE","MUSICAL","SI-FI"
    };
    private boolean inDefault(String s){
    	String cs = s.toUpperCase();
    	for(String ds:defaultCategory){
    		if(cs.equals(ds))
    			return true;
    	}
    	return false;
    }

    public Movie(String title, String releaseDate, List<String> categories, String movieFileName) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.oringinalCategory.addAll(categories);
        for(String s :this.oringinalCategory){
        	if(inDefault(s))
        		this.category.add(s);
        	else
        		this.category.add("Others");
        }
        this.movieFileName = movieFileName;
        init();
    }

    public Movie(String title){
        this.title = title;
        releaseDate = "";
        movieFileName = "";
        init();
    }

    public Movie(){
        title = "";
        releaseDate = "";
        movieFileName = "";
        init();
    }

    private void init(){
        this.rating = "";
        this.plot = "";
        this.languages = new ArrayList<>();
        this.directors = new ArrayList<>();
        this.actors = new ArrayList<>();
        this.countries = new ArrayList<>();
        this.loaded = false;
    }

    /**
     * Ensure that the movie file is set correctly and the file is under /resources/movie-list/
     * @return The URL of the movie in the resources folder.
     */
    public URL getMovieFileURL(){
        return getClass().getResource("../../movie-files/" + movieFileName);
    }
    
    public Set<String> getCategory(){
    	return category;
    }
    
    public String getMovieFileName() {
        return movieFileName;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    /**
     * Ensure that the image file is set correctly and the file is under /resources/pictures/
     * @return The URL of the image in the resources folder.
     */
    public URL getImageFileURL(){
        return getClass().getResource("../../pictures/" + imageFileName);
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public String getImageURL() {
        return imageFileName;
    }

    public void addCategory(String category){
    	if(inDefault(category))
    		oringinalCategory.add(category);
    	else
    		oringinalCategory.add("Others");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Set<String> getCategories() {
        return category;
    }
    
    public Set<String> getOringinalCategories(){
    	return this.oringinalCategory;
    }

    public void setCategories(List<String> categories) {
        this.oringinalCategory = new TreeSet<String>();
        this.oringinalCategory.addAll(categories);
        this.category = new TreeSet<String>(); 
        for(String s :this.oringinalCategory){
        	if(inDefault(s))
        		this.category.add(s);
        	else
        		this.category.add("Others");
        }
    }

    public void setMovieFileName(String movieFileName) {
        this.movieFileName = movieFileName;
    }


    @Override
    public String  toString(){
        return String.format("{%s, %s, %s}", title, releaseDate , oringinalCategory);
    }

    @Override
    public int compareTo(Movie o) {
        return this.title.compareTo(o.title);    }
}