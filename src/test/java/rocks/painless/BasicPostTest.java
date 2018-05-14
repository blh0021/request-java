package rocks.painless;

import org.junit.jupiter.api.Test;
import rocks.painless.helpers.FileHelper;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BasicPostTest {
    private FileHelper fh = new FileHelper();

    @Test
    void PostFormUrlEncodedTest() {
        String txt = fh.getTestFile("/post_form_url_encoded.json");
        String actual = null;
        Request req = new Request(txt);
        try {
            actual = req.process();
        } catch(Exception e ) {
            System.out.println(e);
            e.printStackTrace();
        }
        System.out.println(actual);
        assertTrue(actual.contains("grant_type"));
    }
}
