package rocks.painless;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;

public class ConfigSchema {
    private Schema schema;
    private String errors;

    public ConfigSchema() {
        try {
            schemaLoader();
        } catch(IOException ioe) {
            System.out.println(ioe);
        }
    }

    private void schemaLoader() throws IOException{
        try (InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("configSchema.json")){
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            schema = SchemaLoader.load(rawSchema);
        } catch(ValidationException ve) {
            System.out.println(ve);
        }
    }

    protected boolean validate(JSONObject data) {
        try {
            schema.validate(data);
            return true;
        } catch(ValidationException ve) {
            errors=ve.getMessage();
            System.out.println(ve.getMessage());
            ve.getCausingExceptions().stream()
                    .map(ValidationException::getMessage)
                    .forEach(System.out::println);
            return false;
        }
    }

    protected String getErrors() {
        return errors;
    }
}
