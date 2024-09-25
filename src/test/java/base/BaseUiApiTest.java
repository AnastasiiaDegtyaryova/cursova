package base;

import helpers.ConfigLoader;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseUiApiTest extends BaseUiTest {
    protected String apiBaseUrl;
    protected String basicAuthUsername;
    protected String basicAuthToken;
    protected String environment;

    @BeforeClass
    @Parameters({"environment", "browser"})
    public void setUp(@Optional("local") String environment, @Optional("chrome") String browser) {
        super.setUp(environment, browser);
        this.environment = environment;

        ConfigLoader configLoader = new ConfigLoader();
        this.apiBaseUrl = configLoader.getApiUrl(environment);
        this.basicAuthUsername = configLoader.getAuthUsername(environment);
        this.basicAuthToken = configLoader.getAuthToken(environment);
    }

    @AfterClass
    public void tearDown() {
        super.tearDown();
    }

}

