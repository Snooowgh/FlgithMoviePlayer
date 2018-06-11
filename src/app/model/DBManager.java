package app.model;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBManager {
    private static final String CSV_SPLIT = "\",";
    private URL CSVpath;

    private int titleCol, filenameCol, relDtCol, catsCol, langsCol, loadedCol;

    /**
     * @param CSVpath the path to the CSV file
     */
    public DBManager(URL CSVpath){
        this.CSVpath = CSVpath;
    }

    private void loadCols() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(CSVpath.getFile()));
        String line = "";

        line = br.readLine();
        String[] movieFields = line.split(CSV_SPLIT);

        // Remove the double quote
        for (int i = 0; i < movieFields.length; i++) {
            movieFields[i] = movieFields[i].replace("\"", "").trim();
        }
        for (int i = 0; i < movieFields.length; i++) {
            switch (movieFields[i]) {
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
                case "Languages":
                    langsCol = i;
                    break;
                case "Loaded":
                    loadedCol = i;
                    break;
            }
        }
    }

    /**
     * Loads the movie list from a CSV file
     */
    public List<Movie> getMoviesFromCSV() throws IOException {
        //System.out.println(path);
        List<Movie> movies = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(CSVpath.getFile()));

        loadCols();

        String line = "";
        int lineNum = 0;
        while ((line = br.readLine()) != null) {
            String[] movieFields = line.split(CSV_SPLIT);
            // Remove the double quote
            for (int i = 0; i < movieFields.length; i++) {
                movieFields[i] = movieFields[i].replace("\"", "").trim();
            }
            // Don't initialize a movie with no title
            if (!movieFields[0].equals("") && lineNum > 0) {
                Movie m = new Movie();
                m.setTitle(movieFields[titleCol].trim());
                m.setReleaseDate(movieFields[relDtCol].trim());
                m.setMovieFileName(movieFields[filenameCol].trim());

                // TODO: Provide default image
                m.setImageFileName(movieFields[filenameCol].trim().split("\\.")[0] + ".jpg");

                // Init the categories
                String[] cats = movieFields[catsCol].split(",");
                // Trim the whitespaces from the column in the csv
                for (int i = 0; i < cats.length; i++) {
                    cats[i] = cats[i].trim();
                }
                m.setCategories(Arrays.asList(cats));

                if (langsCol >= 0) {
                    // Init the languages
                    String[] langs = movieFields[langsCol].split(",");
                    for (int i = 0; i < langs.length; i++) {
                        langs[i] = langs[i].trim();
                    }
                    m.setLanguages(Arrays.asList(langs));
                }

                if (loadedCol >= 0) {
                    // Check if the movie has been loaded from the Internet
                    if (movieFields[loadedCol].trim().equals("Y")) {
                        m.setLoaded(true);
                    }
                }

                movies.add(m);
            }

            lineNum++;
        }

        return movies;
    }

    private String getCSVlineY(String title, String fileName, String relDate, String categories, String langs){
        String line = String.format("\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"Y\"%n",
                title,
                fileName,
                relDate,
                categories,
                langs);

        return line;
    }

    public void writeNewMoviesDataToCSV() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(CSVpath.getFile()));
        StringBuilder out  = new StringBuilder();

        loadCols();

        String line = "";
        int lineNum = 0;
        while ((line = br.readLine()) != null) {
            String movieFields[] = line.split(CSV_SPLIT);
            // Remove the double quote
            for (int i = 0; i < movieFields.length; i++) {
                movieFields[i] = movieFields[i].replace("\"", "").trim();
            }

            if (movieFields[loadedCol].equals("Y")){
                //out.append(getCSVlineY());
            }

            lineNum++;
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
