package app.model;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DBManager manages the reading and writing from the CSV file
 *
 * @author Daniel Babbev
 */
public class DBManager {
    private static final String CSV_SPLIT = "\",";
    private URL CSVpath;

    private int titleCol, filenameCol, relDtCol, catsCol, langsCol, loadedCol;


    /**
     * @param CSVpath the path to the CSV file
     * @throws IOException when the CSV file is not found
     */
    public DBManager(URL CSVpath) throws IOException {
        this.CSVpath = CSVpath;
        loadCols();
    }

    private void loadCols() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(CSVpath.getFile()),1024);
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
                default:
                    loadedCol = i;
            }
        }
        br.close();
    }

    /**
     * Loads the movie list from a CSV file
     *
     * @throws IOException When the CSV file is not found
     */
    public List<Movie> getMoviesFromCSV() throws IOException {
        //System.out.println(path);
        List<Movie> movies = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(CSVpath.getFile()),1024);

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
        br.close();
        return movies;
    }

    private String getCSVlineY(List<String> cols) {
        String line = String.format("\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"Y\"%n",
                cols.get(0),
                cols.get(1),
                cols.get(2),
                cols.get(3),
                cols.get(4));

        return line;
    }

    /**
     * Writes basic information about new movies to the database
     *
     * @throws IOException when the CSV file is not found
     */
    public void writeNewMoviesDataToCSV() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(CSVpath.getFile()),1024);
        StringBuilder out = new StringBuilder();

        String line;
        int lineNum = 0;
        while ((line = br.readLine()) != null) {
            String movieFields[] = line.split(CSV_SPLIT);
            // Remove the double quote
            for (int i = 0; i < movieFields.length; i++) {
                movieFields[i] = movieFields[i].replace("\"", "").trim();
            }

            if (!movieFields[loadedCol].equals("Y") && lineNum > 0) {
                Movie m = new Movie(movieFields[titleCol]);
                m = MovieInfoLoader.loadMovieInfo(m);

                List<String> cols = new ArrayList<>();
                cols.add(titleCol, m.getTitle());
                cols.add(filenameCol, movieFields[filenameCol]);
                cols.add(relDtCol, m.getReleaseDate());
                cols.add(catsCol, m.getCategories().toString().replace("[", "").replace("]", ""));
                cols.add(langsCol, m.getLanguages().toString().replace("[", "").replace("]", ""));

                out.append(getCSVlineY(cols));

            } else {
                out.append(line.trim() + "\n");
            }


            lineNum++;

        }
        br.close();
        PrintWriter pw = new PrintWriter(new FileWriter(CSVpath.getFile()));
        pw.write(out.toString());
        pw.close();
    }

}