package rocks.painless.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileHelper {
    private String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

    public String getTestFile(String filename) {

        File file = new File(rootPath + filename);
        String txt = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String add;
            while ((add = br.readLine()) != null) {
                txt += add;
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        return txt;
    }
}
