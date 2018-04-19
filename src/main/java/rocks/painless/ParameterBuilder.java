package rocks.painless;

import org.json.JSONObject;

import java.util.*;

public class ParameterBuilder {
    protected static String getParameterString(Map<String, String> params) {
        if (params.isEmpty()) {
            return "";
        }
        List<String> paramList = new ArrayList<String>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            paramList.add(String.format("%s=%s", entry.getKey(), entry.getValue()));
        }
        return "?" + String.join("&", paramList);
    }

    protected static String getParameterString(JSONObject params) {
        if (params.keySet().isEmpty()) {
            return "";
        }
        List<String> paramList = new ArrayList<String>();

        for (Iterator<String> key = params.keys(); key.hasNext();) {
            String next = key.next();
            paramList.add(String.format("%s=%s", next, params.getString(next)));
        }
        return "?" + String.join("&", paramList);
    }
}
