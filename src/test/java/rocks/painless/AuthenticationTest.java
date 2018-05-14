package rocks.painless;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import rocks.painless.helpers.FileHelper;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationTest {
    private FileHelper fh = new FileHelper();

    @Test
    void BasicAuthTest(){
        String txt = fh.getTestFile("/basicAuth.json");
        String actual = null;
        Request req = new Request(txt);
        try {
            actual = req.process();
        } catch(Exception e ) {
            System.out.println(e);
            e.printStackTrace();
        }
        //System.out.println(actual);
        assertTrue(actual.contains("content"));
    }

    @Test
    void OAuth2_Header_Grant_Type() {
        String txt = fh.getTestFile("/oauth2.json");
        String actual = null;
        Request req = new Request(txt);
        try {
            actual = req.process();
        } catch(Exception e ) {
            System.out.println(e);
            e.printStackTrace();
        }
        //System.out.println(actual);
        assertTrue(actual.contains("content"));
    }

    //needs to be client credentials sent through body no grantType.
    @Test @Disabled
    void OAuth2_Body_Grant_Type() {
        String txt = fh.getTestFile("/oauth2_param_grantType.json");
        String actual = null;
        Request req = new Request(txt);
        try {
            actual = req.process();
        } catch(Exception e ) {
            System.out.println(e);
            e.printStackTrace();
        }
        //System.out.println(actual);
        assertTrue(actual.contains("content"));
    }
}