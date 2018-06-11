package tests;

import app.model.Movie;
import app.model.MovieSystem;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MovieSystemTest {
    private MovieSystem getTestMovieSystem(){
        MovieSystem movieSystem = new MovieSystem();
        Movie m1 = new Movie("Movie1");
        m1.setCategories(Arrays.asList("Action", "Thriller", "Double Category"));
        Movie m2 = new Movie("Movie2");
        m2.setCategories(Arrays.asList("Comedy", "Double Category"));
        Movie m3 = new Movie("Movie3");
        m3.setCategories(Arrays.asList("Drama"));
        Movie m4 = new Movie("Movie4");
        m4.setCategories(Arrays.asList("Drama", "Action"));
        movieSystem.addMovie(m1);
        movieSystem.addMovie(m2);
        movieSystem.addMovie(m3);
        movieSystem.addMovie(m4);

        return movieSystem;
    }

    @Test
    public void testAddMovie() {
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

    @Test
    public void testMovieCategories(){
        MovieSystem movieSystem = getTestMovieSystem();

        List<Movie> action = movieSystem.getMoviesByCategory("Action");
        List<Movie> doubleCateg = movieSystem.getMoviesByCategory("Double Category");

        assertEquals(2, action.size());
        for (Movie m : action){
            assertTrue(m.getTitle().equals("Movie1") || m.getTitle().equals("Movie4"));
        }
        assertEquals(2, doubleCateg.size());
        for (Movie m : doubleCateg){
            assertTrue(m.getTitle().equals("Movie1") || m.getTitle().equals("Movie2"));
        }
    }

    @Test
    public void testUniqueCategories(){
        MovieSystem movieSystem = getTestMovieSystem();

        List<String> categories = movieSystem.getUniqueCategories();

        List<String> testCats = Arrays.asList("Action", "Thriller", "Comedy", "Drama", "Double Category");
        for (String cat : categories){
            assertTrue(testCats.contains(cat));
        }
    }


}