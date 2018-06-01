package tests;

import app.model.Movie;
import org.junit.Test;

import static org.junit.Assert.*;

public class MovieTest {

    @Test
    public void getMovieFileURL() {
        Movie movie = new Movie("TestMovie");
        // TODO: This is going to look at the default resource directory instead of a test directory. Consider changing that
        movie.setMovieFileName("TestMedia2.mp4");

        assertNotNull(movie.getMovieFileURL());
    }

    @Test
    public void getMovieImageURL(){
        Movie movie = new Movie("Test Movie");
        movie.setImageFileName("topImage.jpg");

        assertNotNull(movie.getImageFileURL());
    }
}