package app.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Movie represents a single movie
 *
 * @author Daniel Babbev
 */
public class Movie implements Comparable<Movie> {
    private int id;
    private String title;
    private String releaseDate;
    private List<String> categories;
    private String movieFileName;
    private String imageFileName;
    private String rating;
    private String plot;
    private List<String> languages;
    private List<String> directors;
    private List<String> actors;
    private List<String> countries;
    private boolean loaded;

    public Movie(String title, String releaseDate, List<String> categories, String movieFileName) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.categories = categories;
        this.movieFileName = movieFileName;
        init();
    }

    public Movie(String title){
        this.title = title;
        releaseDate = "";
        categories = new ArrayList<>();
        movieFileName = "";
        init();
    }

    public Movie(){
        title = "";
        releaseDate = "";
        categories = new ArrayList<>();
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
        categories.add(category);
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

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public void setMovieFileName(String movieFileName) {
        this.movieFileName = movieFileName;
    }


    @Override
    public String  toString(){
        return String.format("{%s, %s, %s}", title, releaseDate , categories);
    }

    @Override
    public int compareTo(Movie o) {
        return this.title.compareTo(o.title);    }
}