package base;

import helpers.BrowserSetup;
import helpers.ConfigLoader;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseUiTest extends BrowserSetup  {
    protected String baseUrl;

    @BeforeClass
    @Parameters({"environment", "browser"})
    public void setUp(@Optional("local") String environment, @Optional("chrome")String browser) {
        ConfigLoader configLoader = new ConfigLoader();
        this.baseUrl = configLoader.getBaseUrl(environment);

        driver = initializeDriver(browser);
        driver.get(baseUrl + "/login");
    }

    @AfterClass
    public void tearDown() {
        quitDriver();
    }

}
