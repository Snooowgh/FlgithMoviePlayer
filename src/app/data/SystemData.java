package app.data;

import java.util.Locale;

public class SystemData {
    public static String language = "English";
    public static int FlightTime = 10;
    public static void setDefaultLanguage(String value){
        switch (value){
            case "English":
                Locale.setDefault(Locale.ENGLISH);
                break;
            case "简体中文":
                Locale.setDefault(Locale.CHINA);
                break;
            case "日本语":
                Locale.setDefault(Locale.JAPAN);
                break;
            default:
                Locale.setDefault(Locale.ENGLISH);
        }
    }
}
