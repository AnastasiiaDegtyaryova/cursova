package ui.tests;

import base.BaseUiTest;
import org.openqa.selenium.By;
import org.testng.annotations.*;
import org.testng.Assert;

import static org.testng.Assert.assertTrue;

public class LoginTest extends BaseUiTest {

    @BeforeMethod
    public void clearLoginForm() {
        driver.findElement(By.id("form-username")).clear();
        driver.findElement(By.id("form-password")).clear();
    }

    @Test
    public void positiveLoginTest() {
        driver.findElement(By.id("form-username")).sendKeys("admin1");
        driver.findElement(By.id("form-password")).sendKeys("admin1");
        driver.findElement(By.xpath("//button[text()='Sign in']")).click();

        assertTrue(driver.findElement(By.className("title")).getText().contains("Dashboard for admin"));
    }

    @Test
    public void negativeLoginTest_InvalidUsername() {
        driver.findElement(By.id("form-username")).sendKeys("wrongUser");
        driver.findElement(By.id("form-password")).sendKeys("admin1");
        driver.findElement(By.xpath("//button[text()='Sign in']")).click();

        String errorMessage = driver.findElement(By.cssSelector("p.alert.alert-error")).getText();
        Assert.assertEquals(errorMessage, "Bad username or password", "Error message should match.");
    }

    @Test
    public void negativeLoginTest_InvalidPassword() {
        driver.findElement(By.id("form-username")).sendKeys("admin1");
        driver.findElement(By.id("form-password")).sendKeys("wrongPass");
        driver.findElement(By.xpath("//button[text()='Sign in']")).click();

        String errorMessage = driver.findElement(By.cssSelector("p.alert.alert-error")).getText();
        Assert.assertEquals(errorMessage, "Bad username or password", "Error message should match.");
    }
}
