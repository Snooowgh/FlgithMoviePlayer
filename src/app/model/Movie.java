package app.model;

import java.util.List;

/**
 * Movie represents a single movie
 */
public class Movie {
    private int id;
    private String title;
    private String releaseDate;
    private List<String> categories;
    private String fileName;

    public Movie(String title, String releaseDate, List<String> categories, String fileName) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.categories = categories;
        this.fileName = fileName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getId() {
        return id;
    }
}
