package rocks.painless;

import org.json.JSONObject;

public class Request {

    private JSONObject request;
    private RequestProcessor rp = new RequestProcessor();

    public Request(JSONObject req) {
        request = req;
    }

    public Request(String text) {
        request = new JSONObject(text);
    }

    public String process() throws Exception {
        rp.loadConfig(request);
        rp.buildQueryString();
        rp.buildHeaders();
        String tmp = "";
        tmp = rp.execute();
        return tmp;
    }
}
