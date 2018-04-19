package rocks.painless;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

public class RequestProcessor {

    private HttpURLConnection connection;
    private JSONObject config;
    private String response;

    protected void loadConfig(JSONObject cfg) {
        config = cfg;
    }

    protected void buildQueryString() throws Exception {
        String params = "";
        if (!config.isNull("parameters")) {
            params = ParameterBuilder.getParameterString(config.getJSONObject("parameters"));
        }
        URL url = new URL(config.getString("host") + params);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(config.getString("method"));
    }

    protected void buildHeaders() {
        if (config.isNull("headers")) {
            return;
        }
        JSONObject headers = config.getJSONObject("headers");
        for(Iterator<String> key = headers.keys(); key.hasNext();) {
            String next = key.next();
            connection.addRequestProperty(next, headers.getString(next));
        }
    }

    protected String execute() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String input;
        StringBuffer content = new StringBuffer();
        while((input = in.readLine()) != null) {
            content.append(input);
        }
        in.close();
        response = content.toString();
        return response;
    }
}
