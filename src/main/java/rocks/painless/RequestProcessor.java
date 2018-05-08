package rocks.painless;

import org.json.JSONException;
import org.json.JSONObject;

import org.apache.commons.lang3.StringEscapeUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RequestProcessor {

    private HttpURLConnection connection;
    private JSONObject config;
    private String response;
    private Long responseTime;
    private int responseCode;
    private String contentType;
    private Map<String, List<String>> responseHeaders;

    protected void loadConfig(JSONObject cfg) {
        config = cfg;
    }

    protected String getPath() {
        String path;
        try {
            path = config.getString("path");
        } catch (JSONException jse) {
            return "";
        }
        return path;
    }

    protected void buildQueryString() throws Exception {
        String params = "";
        if (!config.isNull("parameters")) {
            params = ParameterBuilder.getParameterString(config.getJSONObject("parameters"));
        }
        URL url = new URL(config.getString("host") + getPath() + params);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(config.getString("method"));
    }

    protected void authenticate() {
        if (config.isNull("auth"))
            return;
        Authentication auth = new Authentication(config.getJSONObject("auth"));
        KeyValuePair map = auth.process();
        connection.addRequestProperty(map.key, map.value);
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
        Timestamp start = new Timestamp(System.currentTimeMillis());
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String input;
        StringBuffer content = new StringBuffer();
        while((input = in.readLine()) != null) {
            content.append(input);
        }
        in.close();
        Timestamp end = new Timestamp(System.currentTimeMillis());
        responseTime = end.getTime()-start.getTime();
        response = content.toString();
        setConnectionInfo();
        return response;
    }

    private void setConnectionInfo() throws IOException {
        responseCode = connection.getResponseCode();
        responseHeaders = connection.getHeaderFields();
    }

    protected Long getResponseTime() {
        return responseTime;
    }

    protected int getResponseCode() {
        return responseCode;
    }

    protected Map<String, List<String>> getResponseHeaders() {
        return responseHeaders;
    }

    protected JSONObject getResponseHeadersJson() {
        JSONObject js = new JSONObject();
        for (Map.Entry<String, List<String>> element : responseHeaders.entrySet()) {
            String result = StringEscapeUtils.escapeJava(element.getValue().get(0));
            js.put(element.getKey(), result);
        }
        return js;
    }
}
