package app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * MovieSystem connects all the backed elements and logic together in
 * an easy to use API.
 *
 * The controller interacts with this object only
 */
public class MovieSystem {
    private List<Movie> movies;

    public MovieSystem() {
        movies = new ArrayList<>();
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

    public List<Movie> getMovies() {
        return movies;
    }
}
