package app.model;

import javax.swing.text.html.ListView;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * MovieSystem connects all the backed elements and logic together in
 * an easy to use API.
 *
 * The controller interacts with this object only
 *
 * @author Daniel Babbev
 */
public class MovieSystem {
    private List<Movie> movies;

    public MovieSystem() {
        movies = new ArrayList<>();
        //loadMoviesFromCSV(System.getProperty("user.dir") + "\\movie-list.csv");
    }


    public void addMovie(Movie movie){
        movies.add(movie);
    }

    public Movie getMovie(String name){
        for (Movie m : movies){
            if (m.getTitle().equals(name))
                return m;
        }

        return null;
    }

    /**
     * @return a list of all the unique categories
     */
    public List<String> getUniqueCategories(){
        List<String> categories = new ArrayList<>();

        for (Movie m : movies){
            for (String cat : m.getCategories()){
                if (!categories.contains(cat))
                    categories.add(cat);
            }
        }

        return categories;
    }

    public List<String> getCountries(){
        List<String> categories = new ArrayList<>();
        for (Movie m : movies){
            if (!categories.contains(m.getLanguage())){
                categories.add(m.getLanguage());
            }
        }
        return categories;
    }

    public List<Movie> getMovieByTwocategory(String firstCategory, String secondCategory){
        List<Movie> mov = new ArrayList<>();
        for (Movie m : movies){
            if (m.getCategories().contains(firstCategory)&&m.getCategories().contains(secondCategory))
                mov.add(m);
        }
        return mov;
    }

    public List<Movie> getMoviesByCategory(String category){
        List<Movie> mov = new ArrayList<>();

        for (Movie m : movies){
            if (m.getCategories().contains(category))
                mov.add(m);
        }

        return mov;
    }



    public List<Movie> getMovies() {
        return movies;
    }

    /**
     * Loads the movie list from a CSV file
     * @param path the path to the CSV
     */
    public void loadMoviesFromCSV(URL path) throws IOException {
        DBManager dbManager = new DBManager(path);
        movies = dbManager.getMoviesFromCSV();
    }
}
