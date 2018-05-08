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

    protected KeyValuePair BasicAuthentication(String user, String pass) {
        String auth = String.format("%s:%s", user, pass);
        String encoded = Base64.getEncoder().encodeToString(auth.getBytes());
        KeyValuePair map = new KeyValuePair("Authorization", String.format("Basic %s", encoded));
        return map;
    }


    private JSONObject buildBasicAuthConfig(JSONObject auth) {
        JSONObject ath = new JSONObject();
        JSONObject basic = new JSONObject();
        ath.put("basic", basic);
        ath.put("type", "basic");
        basic.put("username", auth.getString("username"));
        basic.put("password", auth.getString("password"));
        return ath;
    }

    protected KeyValuePair OAuth2(JSONObject auth) {
        JSONObject requestTokenConfig = new JSONObject();
        KeyValuePair map = new KeyValuePair();
        if (auth.getString("grantType").equals("Client Credentials")) {
            requestTokenConfig.put("host", auth.getString("accessTokenUrl"));
            requestTokenConfig.put("method", "POST");
            JSONObject ath = buildBasicAuthConfig(auth);

            requestTokenConfig.put("auth", ath);
            Request req = new Request(requestTokenConfig);
            try {
                String data = req.process();
                JSONObject tdata = new JSONObject(data);
                map.key = "Authorization";
                map.value = String.format("Bearer %s", tdata.getString("access_token"));
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return map;
    }

    protected KeyValuePair process() {
        KeyValuePair map = new KeyValuePair();
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
