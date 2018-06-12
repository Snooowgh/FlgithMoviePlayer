package tests;

import app.model.DBManager;
import app.model.Movie;
import app.model.MovieSystem;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class DBManagerTest {

    @Test
    public void testWriteNewMoviesToCSV() throws IOException, URISyntaxException {
        String testFileName = "movie-list-test-write.csv";
        byte[] encoded = Files.readAllBytes(Paths.get(getClass().getResource(testFileName).toURI()));
        String old = new String(encoded, StandardCharsets.UTF_8);

        DBManager dbManager = new DBManager(getClass().getResource(testFileName));
        dbManager.writeNewMoviesDataToCSV();

        List<Movie> movies = dbManager.getMoviesFromCSV();
        assertEquals("Men In Black", movies.get(2).getTitle());
        assertEquals("1997", movies.get(2).getReleaseDate());
        assertEquals(Arrays.asList("Adventure", "Comedy", "Family"), movies.get(2).getCategories());
        assertEquals(Arrays.asList("English", "Spanish"), movies.get(2).getLanguages());

        PrintWriter pw = new PrintWriter(new FileWriter(getClass().getResource(testFileName).getFile()));
        pw.write(old);
        pw.close();
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