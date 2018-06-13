package app.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Properties;

public class SystemData {
    public static String language;
    public static int flightTime = 10;
    public static Properties prop;
    public static Color[] supportedColor = {Color.BLACK, Color.RED};

    public static void init() {
        SystemData.initializeProperties("user.properties");
        flightTime = Integer.parseInt(prop.getProperty("flightTime"));
        language = getSupportedLanguage().contains(prop.getProperty("defaultLanguage")) ? prop.getProperty("defaultLanguage") : "English";
    }

    /**
     * sets the given Locale in the I18N class and keeps count of the number of switches.
     *
     * @param value the new local to set
     */
    public static void setDefaultLanguage(String value) {
        switch (value) {
            case "English":
                I18N.setLocale(Locale.ENGLISH);
                break;
            case "Franzï¿½sisch":
                I18N.setLocale(Locale.FRENCH);
                break;
            case "Deutsche":
                I18N.setLocale(Locale.GERMAN);
                break;
            default:
                I18N.setLocale(Locale.ENGLISH);
        }
    }

    /**
     * Read properties file
     *
     * @param fileName
     */
    public static void initializeProperties(String fileName) {
        try {
            InputStream in = SystemData.class.getResourceAsStream("/" + fileName);
            BufferedReader bf = new BufferedReader(new InputStreamReader(in),1024);
            prop = new Properties();
            prop.load(bf);
            bf.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getDefaultStyle() {
        String defaultStyle;
        switch (SystemData.prop.getProperty("userInterface")) {
            case "red":
                defaultStyle = "Style_red.css";
                break;
            case "black":
                defaultStyle = "Style_black.css";
                break;
            default:
                defaultStyle = "tabStyle1.css";
        }
        return defaultStyle;
    }

    public static ObservableList<String> getSupportedLanguage() {
        return FXCollections.observableArrayList("English", "Französisch", "Deutsche");
    }


    public static String transColor(String s) {
        String chooseColor;
        switch (s) {
            //Style_red.css
            case "0xff0000ff":
                chooseColor = "red";
                break;
            case "0x000000ff":
                chooseColor = "black";
                break;
            default:
                chooseColor = "black";
        }
        return chooseColor;
    }
}
