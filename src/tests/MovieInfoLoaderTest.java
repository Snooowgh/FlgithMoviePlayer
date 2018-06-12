package tests;

import app.model.Movie;
import app.model.MovieInfoLoader;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * @author Daniel Babbev
 */
public class MovieInfoLoaderTest {

    @Test
    public void testLoadBasicInfo() {
        Movie lebowski = MovieInfoLoader.loadMovieInfo(new Movie("The Big Lebowski"));

        List<String> lebLng = Arrays.asList("English", "German", "Hebrew", "Spanish");
        List<String> cats = Arrays.asList("Comedy", "Crime");
        List<String> dirs = Arrays.asList("Joel Coen", "Ethan Coen");
        List<String> countries = Arrays.asList("UK", "USA");

        assertEquals("1998", lebowski.getReleaseDate());
        assertEquals("\"The Dude\" Lebowski, mistaken for a millionaire Lebowski, seeks restitution for his ruined rug and enlists his bowling buddies to help get it.", lebowski.getPlot());
        for (String l : lebowski.getLanguages()){
            assertTrue(lebLng.contains(l));
        }
        for (String cat : lebowski.getCategories()){
            assertTrue(cats.contains(cat));
        }
        for (String d : lebowski.getDirectors()){
            assertTrue(dirs.contains(d));
        }
        for (String c : lebowski.getCountries()){
            assertTrue(countries.contains(c));
        }
    }

    @Test
    public void testLoadNonExistingMovie(){
        Movie mov = MovieInfoLoader.loadMovieInfo(new Movie("My cool movie"));

    }
}