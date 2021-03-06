package tests;

import app.model.Movie;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Daniel Babbev
 */
public class MovieTest {

    @Test
    public void testGetMovieFileURL() {
        Movie movie = new Movie("TestMovie");
        movie.setMovieFileName("TheThirdMan.mp4");

        // TODO: This is going to look at the default resource directory instead of a loadMovieInfo directory. Consider changing that
        assertNotNull(movie.getMovieFileURL());
    }

    @Test
    public void testGetMovieImageURL(){
        Movie movie = new Movie("Test Movie");
        movie.setImageFileName("TheThirdMan.jpg");

        // TODO: This is going to look at the default resource directory instead of a loadMovieInfo directory. Consider changing that
        assertNotNull(movie.getImageFileURL());
    }
}