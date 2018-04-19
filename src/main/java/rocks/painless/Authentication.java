package rocks.painless;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Authentication {

    protected Map<String, String> BasicAuthentication(String user, String pass) {
        String auth = String.format("%s:%s", user, pass);
        String encoded = Base64.getEncoder().encodeToString(auth.getBytes());
        HashMap<String, String> map = new HashMap();
        map.put("Authorization", String.format("Basic %s", encoded));
        return map;
    }
}
