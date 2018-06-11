package app.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;

public class SystemData {
    public static String language = "English";
    public static int FlightTime = 10;
    public static void setDefaultLanguage(String value){
        switch (value){
            case "English":
                Locale.setDefault(Locale.ENGLISH);
                break;
            case "��������":
                Locale.setDefault(Locale.CHINA);
                break;
            case "�ձ���":
                Locale.setDefault(Locale.JAPAN);
                break;
            default:
                Locale.setDefault(Locale.ENGLISH);
        }
    }

    public static ObservableList<String> getSupportedLanguage() {
        return FXCollections.observableArrayList("��������", "English", "�ձ���");
    }
}