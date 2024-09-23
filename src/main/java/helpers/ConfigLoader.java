package helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    private Properties properties = new Properties();

    public ConfigLoader() {
        try (FileInputStream input = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getApiUrl(String environment) {
        return properties.getProperty(environment + ".apiUrl");
    }

    public String getBaseUrl(String environment) {
        return properties.getProperty(environment + ".baseUrl");
    }
    public String getAuthUsername(String environment) {
        return properties.getProperty(environment + ".basicAuthUsername");
    }

    public String getAuthToken(String environment) {
        return properties.getProperty(environment + ".basicAuthToken");
    }
}
