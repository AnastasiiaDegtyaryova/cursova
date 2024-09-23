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

public class BoardsApiTests extends BaseApiTest {

    private int projectId;

    @BeforeClass
    @Parameters({"environment"})
    public void setup(@Optional("internet")String environment) {
        super.setup(environment);
    }

    public int createBoard() {
        String requestBody = "{\n" +
                "  \"jsonrpc\": \"2.0\",\n" +
                "  \"method\": \"createProject\",\n" +
                "  \"id\": 1,\n" +
                "  \"params\": {\n" +
                "    \"name\": \"TestBoard\"\n" +
                "  }\n" +
                "}";

        Response response = given()
                .auth()
                .basic(basicAuthUsername, basicAuthToken)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post(baseUrl);

        Assert.assertEquals(response.getStatusCode(), 200, "Статус відповіді не відповідає очікуваному.");

        JsonPath jsonPathEvaluator = response.jsonPath();
        projectId = jsonPathEvaluator.getInt("result");

        System.out.println("Created Project ID: " + projectId);
        response.prettyPrint();
        return projectId;
    }


    @Test
    public void testCreateBoard() {
        projectId = createBoard();
    }


    @Test(dependsOnMethods = "testCreateBoard")
    public void testRemoveBoard() {

        String requestBody = "{\n" +
                "  \"jsonrpc\": \"2.0\",\n" +
                "  \"method\": \"removeProject\",\n" +
                "  \"id\": 1,\n" +
                "  \"params\": {\n" +
                "    \"project_id\": " + projectId + "\n" +
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
