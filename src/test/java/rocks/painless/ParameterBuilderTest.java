package rocks.painless;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParameterBuilderTest {

    @Test
    void getParameterStringEmpty() {
        Map<String, String> tmp = new HashMap();
        assertEquals(ParameterBuilder.getParameterString(tmp), "");
    }

    @Test
    void getParameterString() {
        Map<String, String> tmp = new HashMap();
        tmp.put("test", "false");
        assertEquals(ParameterBuilder.getParameterString(tmp), "?test=false");
    }

    @Test
    void getParameterStringMultiple() {
        Map<String, String> tmp = new HashMap();
        tmp.put("test", "false");
        tmp.put("junk", "stuff");
        assertEquals(ParameterBuilder.getParameterString(tmp), "?test=false&junk=stuff");
    }

    @Test
    void getJsonObjectParameterStringEmpty() {
        JSONObject jo = new JSONObject();
        assertEquals(ParameterBuilder.getParameterString(jo), "");
    }

    @Test
    void getJsonObjectParameterString() {
        JSONObject jo = new JSONObject();
        jo.put("test", "false");
        assertEquals(ParameterBuilder.getParameterString(jo), "?test=false");
    }

    @Test
    void getJsonObjectParameterStringMultiple() {
        JSONObject jo = new JSONObject();
        jo.put("test", "false");
        jo.put("junk", "stuff");
        assertEquals(ParameterBuilder.getParameterString(jo), "?test=false&junk=stuff");
    }
}