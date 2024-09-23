package ui.tests;

import api.tests.BoardsApiTests;
import api.tests.UserApiTests;
import base.BaseUiApiTest;
import io.restassured.response.Response;
import org.testng.annotations.*;
import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;

public class TaskActionsTest extends BaseUiApiTest {

    private int userId;
    private int projectId;

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
        System.out.println("Opening URL: " + baseUrl + "/login");
        open(baseUrl + "/login");

        $("#form-username").setValue("TestUser");
        $("#form-password").setValue("123456");
        $x("//button[text()='Sign in']").click();

        $(byLinkText("My projects")).should(appear).click();
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

        $(byLinkText("Test Task")).should(appear).shouldHave(text("Test Task"));
    }
}
