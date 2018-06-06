package app.util;

import java.io.*;

public class CSVFile {
    @SuppressWarnings("rawtypes")
    public static File createCSVFile(String outPutPath, String fileName) {
        File csvFile = null;
        BufferedWriter csvFileOutputStream = null;
        try {
            File file = new File(outPutPath);
            if (!file.exists()) {
                file.mkdir();
            }
            csvFile = File.createTempFile(fileName, ".csv", new File(outPutPath));
            csvFileOutputStream = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(csvFile), "UTF-8"),1024);
            csvFileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvFileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }
}
