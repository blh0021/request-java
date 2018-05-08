package rocks.painless;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationTest {
    private String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

    private String getTestFile(String filename) {

        File file = new File(rootPath + filename);
        String txt = "";
        String actual=null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String add;
            while ((add = br.readLine()) != null) {
                txt += add;
            }

            Request req = new Request(txt);
            actual = req.process();
        } catch(Exception e) {
            System.out.println(e);
        }
        return actual;
    }

    @Test
    void BasicAuthTest(){
        String actual = getTestFile("/basicAuth.json");
        assertTrue(actual.contains("content"));
    }

    @Test
    void OAuth2() {
        String actual = getTestFile("/oauth2.json");
        assertTrue(actual.contains("content"));
    }
}