package app.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Movie represents a single movie
 */
public class Movie implements Comparable<Movie> {
    private int id;
    private String title;
    private String releaseDate;
    private List<String> categories;
    private String movieFileName;
    private String imageFileName;

    public Movie(String title, String releaseDate, List<String> categories, String movieFileName) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.categories = categories;
        this.movieFileName = movieFileName;
    }

    public Movie(String title){
        this.title = title;
        releaseDate = "";
        categories = new ArrayList<>();
        movieFileName = "";
    }

    // for test
    public Movie(String title, String imageFileName, String movieURL) {
        this.title = title;
        this.imageFileName = imageFileName;
        this.movieFileName = movieURL;
    }

    public Movie(){
        title = "";
        releaseDate = "";
        categories = new ArrayList<>();
        movieFileName = "";
    }

    /**
     * Ensure that the movie file is set correctly and the file is under /resources/movie-list/
     * @return The URL of the movie in the resources folder.
     */
    public URL getMovieFileURL(){
        return getClass().getResource("../../movie-files/" + movieFileName);
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

    public int getId() {
        return id;
    }

    @Override
    public String  toString(){
        return String.format("{%s, %s, %s}", title, releaseDate , categories);
    }

    @Override
    public int compareTo(Movie o) {
        return this.title.compareTo(o.title);    }
}
