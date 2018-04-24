package rocks.painless;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class SchemaTest {

    private String getTestFile(String filename) {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

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

    @Test
    public void Schema() {
        String json = getTestFile("test.json");
        ConfigSchema cs = new ConfigSchema();
        JSONObject js = new JSONObject(json);
        assertTrue(cs.validate(js));
    }
}
