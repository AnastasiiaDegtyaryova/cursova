package ui.tests;

import base.BaseUiTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Parameters;
import java.time.Duration;


public class ProjectCreationTest extends BaseUiTest {

    @Test
    @Parameters({"browser", "environment"})
    public void createProjectTest(@Optional("chrome") String browser, @Optional("local") String environment) {

        driver.get(baseUrl + "/login");

        driver.findElement(By.id("form-username")).sendKeys("admin1");
        driver.findElement(By.id("form-password")).sendKeys("admin1");
        driver.findElement(By.xpath("//button[text()='Sign in']")).click();

        WebElement newProjectButton = driver.findElement(By.xpath("(//a[@href='/project/create'])[2]"));
        newProjectButton.click();

        WebElement projectNameField = driver.findElement(By.id("form-name"));
        projectNameField.sendKeys("NewTestProject");

        WebElement projectIdentifierField = driver.findElement(By.id("form-identifier"));
        projectIdentifierField.sendKeys("NewTestProject");

        WebElement taskLimitCheckbox = driver.findElement(By.name("per_swimlane_task_limits"));
        if (!taskLimitCheckbox.isSelected()) {
            taskLimitCheckbox.click();
        }

        WebElement taskLimitField = driver.findElement(By.id("form-task_limit"));
        taskLimitField.clear();
        taskLimitField.sendKeys("2");

        WebElement saveButton = driver.findElement(By.xpath("//button[@type='submit' and text()='Save']"));
        saveButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement projectTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".title")));

        assertTrue(projectTitle.getText().contains("NewTestProject"), "Project title should contain 'NewTestProject'");
        assertTrue(projectTitle.getText().contains("2"), "Task limit should be '2'");
    }
}
