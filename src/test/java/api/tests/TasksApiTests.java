package api.tests;

import base.BaseApiTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class TasksApiTests extends BaseApiTest {

    private int taskId;

    @BeforeClass
    @Parameters({"environment"})
    public void setup(@Optional("local") String environment) {
        super.setup(environment);
    }

    @Test
    public void testCreateTask() {

        String requestBody = "{\n" +
                "  \"jsonrpc\": \"2.0\",\n" +
                "  \"method\": \"createTask\",\n" +
                "  \"id\": 1423501287,\n" +
                "  \"params\": {\n" +
                "    \"title\": \"TestTask\",\n" +
                "    \"project_id\": 25\n" +
                "  }\n" +
                "}";

        Response response = given()
                .auth()
                .basic(basicAuthUsername, basicAuthToken)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post(baseUrl);

        Assert.assertEquals(response.getStatusCode(), 200, "Статус відповіді не відповідає очікуваному.");
        response.prettyPrint();

        JsonPath jsonPathEvaluator = response.jsonPath();
        taskId = jsonPathEvaluator.getInt("result");

        System.out.println("Created Task ID: " + taskId);
        response.prettyPrint();
    }

    @Test
    public void testRemoveTask() {

        String requestBody = "{\n" +
                "  \"jsonrpc\": \"2.0\",\n" +
                "  \"method\": \"removeTask\",\n" +
                "  \"id\": 1423501287,\n" +
                "  \"params\": {\n" +
                "    \"task_id\": " + taskId + "\n" +
                "  }\n" +
                "}";

        Response response = given()
                .auth()
                .basic(basicAuthUsername, basicAuthToken)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post(baseUrl);

        Assert.assertEquals(response.getStatusCode(), 200, "Статус відповіді не відповідає очікуваному.");
        response.prettyPrint();
    }

}

