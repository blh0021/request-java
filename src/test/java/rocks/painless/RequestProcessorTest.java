package rocks.painless;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.*;

class RequestProcessorTest {

    @Test
    void execute() {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.out.println(rootPath);
        File file = new File(rootPath + "/test.json");
        String txt = "";
        String actual=null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String add;
            while ((add = br.readLine()) != null) {
                txt += add;
            }
            System.out.println(txt);
            Request req = new Request(txt);
            actual = req.process();
        } catch(Exception e) {
            System.out.println(e);
        }


        assertTrue(actual.contains("userId"));
    }
}