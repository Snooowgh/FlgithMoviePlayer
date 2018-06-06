package app.model;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

public class MovieInfoLoader {
    private static final String API_KEY = "eaab2b9c";

    public static Movie loadMovieInfo(Movie movie) {
        String title = movie.getTitle().replace(" ", "+");
        String sURL = "http://www.omdbapi.com/?t=" + title + "&apikey=" + API_KEY;

        try {
            // Connect to the URL using java's native library
            URL url = new URL(sURL);

            URLConnection request = url.openConnection();
            request.connect();

            // Convert to a JSON object to print data
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
            //System.out.println(rootobj.get("Director"));

            if (!rootobj.get("Response").getAsBoolean()){
                System.out.println(rootobj.get("Error").getAsString());
                return movie;
            }

            String year = rootobj.get("Year").getAsString();
            String rated = rootobj.get("Rated").getAsString();
            String plot = rootobj.get("Plot").getAsString();
            List<String> actors = Arrays.asList(rootobj.get("Actors").getAsString().split(", "));
            List<String> directors = Arrays.asList(rootobj.get("Director").getAsString().split(", "));
            List<String> languages = Arrays.asList(rootobj.get("Language").getAsString().split(", "));
            List<String> categories = Arrays.asList(rootobj.get("Genre").getAsString().split(", "));
            List<String> country = Arrays.asList(rootobj.get("Country").getAsString().split(", "));

            if (year != null)
                movie.setReleaseDate(year);
            if (rated != null)
                movie.setRating(rated);
            if (plot != null)
                movie.setPlot(plot);

            movie.setActors(actors);
            movie.setDirectors(directors);
            movie.setLanguages(languages);
            movie.setCategories(categories);
            movie.setCountries(country);
        } catch (IOException e){
            e.printStackTrace();
        }

        return movie;
    }
}
