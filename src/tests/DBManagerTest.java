package tests;

import app.model.DBManager;
import app.model.Movie;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class DBManagerTest {

    @Test
    public void testWriteToCSV() throws IOException {
        fail();
        DBManager dbManager = new DBManager(getClass().getResource("movie-list-test.csv"));
        Movie movie = new Movie();
        movie.setTitle("Test Write");
        movie.setMovieFileName("test-write.mp4");
        movie.setReleaseDate("2000");
        movie.setCategories(Arrays.asList("Drama", "Action", "Thriller"));
        movie.setLanguages(Arrays.asList("English", "Spanish"));

        File csv = new File(getClass().getResource("movie-list-test.csv").getFile());

//        dbManager.writeMovieToCSV(movie, getClass().getResource("movie-list-test.csv"));

        dbManager.getMoviesFromCSV();
        System.out.println();

        FileWriter fr = new FileWriter(csv);
        fr.close();

    }

    @Test
    public void testReadCSV() throws IOException {
        DBManager dbManager = new DBManager(getClass().getResource("movie-list-test.csv"));
        List<Movie> movies = new ArrayList<>();

        movies = dbManager.getMoviesFromCSV();

        assertEquals(2, movies.size());
        assertEquals("My cool movie", movies.get(0).getTitle());
        assertEquals("1998", movies.get(0).getReleaseDate());
        assertEquals(Arrays.asList("Action", "Drama", "Thriller", "Double Category"), movies.get(0).getCategories());
        assertEquals("mymovie.jpg", movies.get(0).getImageURL());
        assertEquals(Arrays.asList("English"),movies.get(0).getLanguages());
        assertEquals(Arrays.asList("English", "Spanish"), movies.get(1).getLanguages());
        assertTrue(movies.get(0).isLoaded());
        assertFalse(movies.get(1).isLoaded());
    }

}