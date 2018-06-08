package app.model;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
        //loadMoviesFromCSV(System.getProperty("user.dir") + "\\movie-list.csv");
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

    /**
     * @return a list of all the unique categories
     */
    public List<String> getUniqueCategories(){
        List<String> categories = new ArrayList<>();

        for (Movie m : movies){
            for (String cat : m.getCategories()){
                if (!categories.contains(cat))
                    categories.add(cat);
            }
        }

        return categories;
    }

    public List<Movie> getMovieByTwocategory(String firstCategory, String secondCategory){
        List<Movie> mov = new ArrayList<>();
        for (Movie m : movies){
            if (m.getCategories().contains(firstCategory)&&m.getCategories().contains(secondCategory))
                mov.add(m);
        }
        return mov;
    }

    public List<Movie> getMoviesByCategory(String category){
        List<Movie> mov = new ArrayList<>();

        for (Movie m : movies){
            if (m.getCategories().contains(category))
                mov.add(m);
        }

        return mov;
    }



    public List<Movie> getMovies() {
        return movies;
    }

    /**
     * Loads the movie list from a CSV file
     * @param path the path to the CSV
     */
    public void loadMoviesFromCSV(URL path){
        //System.out.println(path);
        String csvSplit = "\",";
        int titleCol = -1;
        int filenameCol = -1;
        int relDtCol = -1;
        int catsCol = -1;
        int langsCol = -1;
        try {
            BufferedReader br = new BufferedReader(new FileReader(path.getFile()));
            String line = "";
            int lineNum = 0;
            while ((line = br.readLine()) != null){
                String[] movieFields = line.split(csvSplit);
                // Remove the double quote
                for (int i = 0; i < movieFields.length; i++) {
                    movieFields[i] = movieFields[i].replace("\"", "").trim();
                }

                if (lineNum == 0){
                    for (int i = 0; i < movieFields.length; i++){
                        switch (movieFields[i]){
                            case "Title":
                                titleCol = i;
                                break;
                            case "File Name":
                                filenameCol = i;
                                break;
                            case "Release Date":
                                relDtCol = i;
                                break;
                            case "Categories":
                                catsCol = i;
                                break;
                            case "Laguages":
                                langsCol = i;
                                break;
                        }
                    }
                }
                // Don't initialize a movie with no title
                if (!movieFields[0].equals("") && lineNum > 0){
                    Movie m = new Movie();
                    m.setTitle(movieFields[titleCol].trim());
                    m.setReleaseDate(movieFields[relDtCol].trim());
                    m.setMovieFileName(movieFields[filenameCol].trim());

                    m.setImageFileName(movieFields[filenameCol].trim().split("\\.")[0] + ".jpg");

                    // Init the categories
                    String[] cats = movieFields[catsCol].split(",");
                    // Trim the whitespaces from the column in the csv
                    for (int i = 0; i < cats.length; i++){
                        cats[i] = cats[i].trim();
                    }
                    m.setCategories(Arrays.asList(cats));

                    movies.add(m);
                }

                lineNum++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeMovieToCSV(Movie movie, URL CSVpath) throws IOException {
            PrintWriter pw = new PrintWriter(new FileWriter(CSVpath.getFile(), true));

            String line = String.format("\"%s\", \"%s\", \"%s\", \"%s\", \"%s\"%n",
                    movie.getTitle(),
                    movie.getMovieFileName(),
                    movie.getReleaseDate(),
                    movie.getCategories().toString().replace("[", "").replace("]", ""),
                    movie.getLanguages().toString().replace("[", "").replace("]", ""));

            pw.write(line);
            pw.close();
    }
}
