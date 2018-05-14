package rocks.painless;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Authentication {

    final static Logger logger = Logger.getLogger(Authentication.class);

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
        basic.put("username", auth.getString("clientId"));
        basic.put("password", auth.getString("clientSecret"));
        return ath;
    }

    private String findValue(JSONObject json, String key, String altKey) {
        if (json.has(key)) {
            return json.getString(key);
        }
        if (json.has(altKey)) {
            return json.getString(altKey);
        }
        return null;
    }

    protected JSONObject clientCredentialsConfig(JSONObject auth) {
        JSONObject requestTokenConfig = new JSONObject();
        JSONObject parameters = new JSONObject();
        JSONObject headers = new JSONObject();
        headers.put("content-type", "application/x-www-form-urlencoded");

        requestTokenConfig.put("host", auth.getString("accessTokenUrl"));
        requestTokenConfig.put("method", "POST");

        if (auth.getString("clientAuthentication").equals("header")) {
            JSONObject ath = buildBasicAuthConfig(auth);
            requestTokenConfig.put("auth", ath);
        } else {
            parameters.put("client_id", auth.getString("clientId"));
            parameters.put("client_secret", auth.getString("clientSecret"));
        }
        requestTokenConfig.put("parameterType", "x-www-form-urlencoded");
        parameters.put("grant_type", "client_credentials");

        requestTokenConfig.put("parameters", parameters);
        requestTokenConfig.put("headers", headers);
        return requestTokenConfig;
    }

    protected KeyValuePair OAuth2(JSONObject auth) throws JSONException {
        JSONObject requestTokenConfig = new JSONObject();
        KeyValuePair map = new KeyValuePair();
        switch (auth.getString("grantType")) {
            case "client_credentials":
                requestTokenConfig = clientCredentialsConfig(auth);
        }
        Request req = new Request(requestTokenConfig);
        String data = null;
        try {
            data = req.process();
            logger.info(data);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        JSONObject tdata = new JSONObject(data);
        map.key = "Authorization";
        map.value = String.format("Bearer %s", findValue(tdata, "access_token", "accessToken"));
        return map;
    }

    protected KeyValuePair process() {
        KeyValuePair map = new KeyValuePair();
        switch (authConfig.getString("type")) {
            case "basic":
                JSONObject basic = authConfig.getJSONObject("basic");
                map = BasicAuthentication(basic.getString("username"), basic.getString("password"));
                break;
            case "oauth2":
                JSONObject oauth2 = authConfig.getJSONObject("oauth2");
                map = OAuth2(oauth2);
                break;
        }
        return map;
    }
}
