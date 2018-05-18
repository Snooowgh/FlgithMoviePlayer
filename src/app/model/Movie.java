package app.model;

import java.util.Date;
import java.util.List;

/**
 * Movie represents a single movie
 */
public class Movie {
    private int mID;
    private String mTitle;
    private Date mReleaseDate;
    private List<String> mCategories;
    private String mFileName;

    public Movie(String title, Date releaseDate, List<String> categories, String fileName, int ID) {
        mTitle = title;
        mReleaseDate = releaseDate;
        mCategories = categories;
        mFileName = fileName;
        mID = ID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
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
