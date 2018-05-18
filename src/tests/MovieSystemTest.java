package tests;

import app.model.Movie;
import app.model.MovieSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MovieSystemTest {


    @org.junit.Test
    public void addMovie() {
        MovieSystem movieSystem = new MovieSystem();
        List<Movie> testMovies = new ArrayList<>();
        Movie theAfricaQueen = new Movie(
                "The African Queen",
                "1951-02-02",
                Arrays.asList("Comedy"),
                "TheAfricanQueen_us_1951.mp4"
        );
        testMovies.add(theAfricaQueen);

        movieSystem.addMovie(theAfricaQueen);

        assertEquals(testMovies, movieSystem.getMovies());
    }
}