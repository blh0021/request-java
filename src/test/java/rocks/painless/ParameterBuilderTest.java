package rocks.painless;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ParameterBuilderTest {

    @org.junit.jupiter.api.Test
    void getParameterStringEmpty() {
        Map<String, String> tmp = new HashMap();
        assertEquals(ParameterBuilder.getParameterString(tmp), "");
    }

    @org.junit.jupiter.api.Test
    void getParameterString() {
        Map<String, String> tmp = new HashMap();
        tmp.put("test", "false");
        assertEquals(ParameterBuilder.getParameterString(tmp), "?test=false");
    }

    @org.junit.jupiter.api.Test
    void getParameterStringMultiple() {
        Map<String, String> tmp = new HashMap();
        tmp.put("test", "false");
        tmp.put("junk", "stuff");
        assertEquals(ParameterBuilder.getParameterString(tmp), "?test=false&junk=stuff");
    }

    @org.junit.jupiter.api.Test
    void getJsonObjectParameterStringEmpty() {
        JSONObject jo = new JSONObject();
        assertEquals(ParameterBuilder.getParameterString(jo), "");
    }

    @org.junit.jupiter.api.Test
    void getJsonObjectParameterString() {
        JSONObject jo = new JSONObject();
        jo.put("test", "false");
        assertEquals(ParameterBuilder.getParameterString(jo), "?test=false");
    }

    @org.junit.jupiter.api.Test
    void getJsonObjectParameterStringMultiple() {
        JSONObject jo = new JSONObject();
        jo.put("test", "false");
        jo.put("junk", "stuff");
        assertEquals(ParameterBuilder.getParameterString(jo), "?test=false&junk=stuff");
    }
}