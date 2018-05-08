package rocks.painless;

import org.json.JSONObject;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Authentication {

    private JSONObject authConfig;

    public Authentication(JSONObject ac) {
        authConfig = ac;
    }

    protected Map<String, String> BasicAuthentication(String user, String pass) {
        String auth = String.format("%s:%s", user, pass);
        String encoded = Base64.getEncoder().encodeToString(auth.getBytes());
        HashMap<String, String> map = new HashMap();
        map.put("Authorization", String.format("Basic %s", encoded));
        return map;
    }

    protected Map<String, String> OAuth2(JSONObject auth) {
        JSONObject requestTokenConfig = new JSONObject();
        HashMap<String, String> map = new HashMap();
        if (auth.getString("grantType").equals("Client Credentials")) {
            requestTokenConfig.put("host", auth.getString("accessTokenUrl"));
            requestTokenConfig.put("method", "POST");
            JSONObject ath = new JSONObject();
            JSONObject basic = new JSONObject();
            basic.put("username", auth.getJSONObject("basic").getString("username"));
            basic.put("password", auth.getJSONObject("basic").getString("password"));
            ath.put("basic", basic);
            requestTokenConfig.put("auth", ath);
            Request req = new Request(requestTokenConfig);
            try {
                String data = req.process();
                JSONObject tdata = new JSONObject(data);
                map.put("Authorization", String.format("Bearer %s", tdata.getString("access_token")));
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return map;
    }

    protected Map<String, String> process() {
        Map<String, String> map = null;
        switch (authConfig.getString("type")) {
            case "basic":
                JSONObject basic = authConfig.getJSONObject("basic");
                map=BasicAuthentication(basic.getString("username"), basic.getString("password"));
                break;
            case "oauth2":
                JSONObject oauth2 = authConfig.getJSONObject("oauth2");
                map=OAuth2(oauth2);
                break;
        }
        return map;
    }
}
