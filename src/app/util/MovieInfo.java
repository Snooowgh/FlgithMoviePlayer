package app.util;

import app.model.Movie;
import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class MovieInfo {
    public static Movie getMovieInfoFromWiki(String movieName){
        String content = "";
        String murl = "https://en.wikipedia.org/wiki/";
        murl = "https://en.wikipedia.org/w/api.php?format=json&action=query&generator=images&titles=";
        for (int i=0;i<movieName.length();i++)
            if (movieName.charAt(i)==' ')
                murl+="%20";
            else
                murl+=movieName.charAt(i);
        try {
            URL url = new URL(murl);
            InputStream in =url.openStream();
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader bufr = new BufferedReader(isr);
            String str;
            while ((str = bufr.readLine()) != null) {
                content+=str;
            }
            bufr.close();
            isr.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Document doc = Jsoup.parse(content);

        System.out.println(content);
        return new Movie();
    }
}
