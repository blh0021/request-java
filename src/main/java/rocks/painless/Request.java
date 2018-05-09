package rocks.painless;

import org.apache.log4j.Logger;
import org.json.JSONObject;

public class Request {

    final static Logger logger = Logger.getLogger(Request.class);

    private JSONObject request;
    private RequestProcessor rp = new RequestProcessor();
    private ConfigSchema schema = new ConfigSchema();

    public Request(JSONObject req) {
        request = req;
    }

    public Request(String text) {
        request = new JSONObject(text);
    }

    public String process() throws Exception {
        if (schema.validate(request)) {
            rp.loadConfig(request);
            rp.buildQueryString();
            rp.authenticate();
            rp.buildHeaders();
            String tmp = "";
            tmp = rp.execute();
            return tmp;
        } else {
            logger.warn(schema.getErrors());
        }
        return null;
    }

    public Long responseTime() {
        return rp.getResponseTime();
    }

    public int responseCode() {
        return rp.getResponseCode();
    }

    public JSONObject responseHeaders() {
        return rp.getResponseHeadersJson();
    }
}
