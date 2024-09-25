package base;

import helpers.ConfigLoader;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseApiTest  {
    protected String baseUrl;
    protected String basicAuthUsername;
    protected String basicAuthToken;

    @BeforeClass
    @Parameters({"environment"})
    public void setup(@Optional("local") String environment) {
        ConfigLoader configLoader = new ConfigLoader();
        baseUrl = configLoader.getApiUrl(environment);
        basicAuthUsername = configLoader.getAuthUsername(environment);
        basicAuthToken = configLoader.getAuthToken(environment);
    }

}

