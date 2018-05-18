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
    private List<Movie> mMovies;

    public MovieSystem() {
        mMovies = new ArrayList<>();
    }

    public void addMovie(Movie movie){
        mMovies.add(movie);
    }

    public Movie getMovie(String name){
        for (Movie m : mMovies){
            if (m.getTitle().equals(name))
                return m;
        }

        return null;
    }

    public List<Movie> getMovies() {
        return mMovies;
    }
}
