package app.model;

import java.util.Date;
import java.util.List;

/**
 * Movie represents a single movie
 */
public class Movie {
    private int mID;
    private String mTitle;
    private String mReleaseDate;
    private List<String> mCategories;
    private String mFileName;

    public Movie(String title, String releaseDate, List<String> categories, String fileName) {
        mTitle = title;
        mReleaseDate = releaseDate;
        mCategories = categories;
        mFileName = fileName;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public List<String> getCategories() {
        return mCategories;
    }

    public void setCategories(List<String> categories) {
        mCategories = categories;
    }

    public String getFileName() {
        return mFileName;
    }

    public void setFileName(String fileName) {
        mFileName = fileName;
    }

    public int getID() {
        return mID;
    }
}
