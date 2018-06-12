package app.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;

public class SystemData {
    public static String language = "English";
    public static int FlightTime = 10;
    /**
     * sets the given Locale in the I18N class and keeps count of the number of switches.
     *
     * @param value
     *         the new local to set
     */
    public static void setDefaultLanguage(String value){
        switch (value){
            case "English":
                I18N.setLocale(Locale.ENGLISH);
                break;
            case "Französisch":
                I18N.setLocale(Locale.FRENCH);
                break;
            case "Deutsche":
                I18N.setLocale(Locale.GERMAN);
                break;
            default:
                I18N.setLocale(Locale.ENGLISH);
        }
    }

    public static ObservableList<String> getSupportedLanguage() {
        return FXCollections.observableArrayList("English", "Französisch","Deutsche");
    }
}