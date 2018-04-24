package rocks.painless;

import org.json.JSONObject;

public class Request {

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
            rp.buildHeaders();
            String tmp = "";
            tmp = rp.execute();
            return tmp;
        } else {
            System.out.println(schema.getErrors());
        }
        return null;
    }
}
