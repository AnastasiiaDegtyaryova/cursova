package ui.tests;

import api.tests.BoardsApiTests;
import api.tests.UserApiTests;
import base.BaseUiApiTest;
import com.codeborne.selenide.WebDriverRunner;
import io.restassured.response.Response;
import org.testng.annotations.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;

public class TaskActionsTest extends BaseUiApiTest {

    private int userId;
    private int projectId;
    private String taskId;

    private BoardsApiTests boardsApiTests;
    private UserApiTests userApiTests;

    @BeforeClass
    public void setup() {

        boardsApiTests = new BoardsApiTests();
        boardsApiTests.setup(environment);

        userApiTests = new UserApiTests();
        userApiTests.setup(environment);

        userId = userApiTests.createUser();
        projectId = boardsApiTests.createBoard();

        addUserToProject(userId, projectId);
    }

    @AfterClass
    public void teardown() {
        boardsApiTests.testRemoveBoard();
        userApiTests.testRemoveUser();
        super.tearDown();
    }

    public void addUserToProject(int userId, int projectId) {
        String requestBody = "{\n" +
                "  \"jsonrpc\": \"2.0\",\n" +
                "  \"method\": \"addProjectUser\",\n" +
                "  \"id\": 1,\n" +
                "  \"params\": {\n" +
                "    \"project_id\": " + projectId + ",\n" +
                "    \"user_id\": " + userId + ",\n" +
                "    \"role\": \"project-member\"\n" +
                "  }\n" +
                "}";

        Response response = given()
                .auth()
                .basic(basicAuthUsername, basicAuthToken)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post(apiBaseUrl);

        if (response.getStatusCode() == 200) {
            System.out.println("User added to project successfully.");
        } else {
            System.out.println("Failed to add user to project. Status: " + response.getStatusCode());
        }
    }

    @Test
    public void testLoginAndCreateTask() {

        open(baseUrl + "/login");
        $("#form-username").should(appear).setValue("TestUser");
        $("#form-password").should(appear).setValue("123456");
        $x("//button[text()='Sign in']").should(appear).click();

        $(byLinkText("My projects")).click();
        $(byLinkText("TestBoard")).should(appear).click();

        $("div.board-add-icon a.js-modal-large").should(appear).click();
        $("#form-title").setValue("Test Task");
        $("textarea[name='description']").setValue("This is a test task description.");
        $("input.select2-search__field").setValue("1").pressEnter();
        $("a.assign-me").should(appear).click();

        $("#form-date_due").click();
        $(byText("30")).click();
        $("button.ui-datepicker-close").click();
        $("button.btn.btn-blue").click();

        $x("//a[contains(text(), 'Test Task')]").should(appear).click();
        String currentUrl = WebDriverRunner.url(); // Отримуємо поточний URL
        taskId = currentUrl.split("/task/")[1].split("/")[0]; // Отримуємо taskId з URL
        System.out.println("Created Task ID: " + taskId);
    }

    @Test(dependsOnMethods = {"testLoginAndCreateTask"})
    public void testAddCommentToTask() {

        if (taskId == null) {
            throw new RuntimeException("Task ID was not set in the previous test.");
        }

        $x("//a[@href='/task/" + taskId + "/edit' and contains(@class, 'js-modal-large')]")
                .should(appear).click();

        $("textarea[name='description']").should(appear).setValue("This is a test comment");

        $x("(//button[contains(@class, 'btn') and contains(@class, 'btn-blue')])[2]").should(appear).click();;

        $("p").should(appear).shouldHave(text("This is a test comment"));
    }

    @Test(dependsOnMethods = {"testAddCommentToTask"})
    public void testCloseTask() {
        $x("//a[contains(@href, '/task/" + taskId + "/close')]").should(appear).click();

        $("#modal-confirm-button").should(appear).click();

        $x("//span[contains(text(), 'closed')]").should(appear);


    }
}



