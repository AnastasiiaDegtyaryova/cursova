package api.tests;

import base.BaseApiTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import static io.restassured.RestAssured.given;

public class UserApiTests extends BaseApiTest {

    private int userId;

    @BeforeClass
    @Parameters({"environment"})
    public void setup(@Optional("internet") String environment) {
        super.setup(environment);
    }

    public int createUser() {

        String requestBody = "{\n" +
                "  \"jsonrpc\": \"2.0\",\n" +
                "  \"method\": \"createUser\",\n" +
                "  \"id\": 15188630341,\n" +
                "  \"params\": {\n" +
                "    \"username\": \"TestUser\",\n" +
                "    \"password\": \"123456\"\n" +
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
        userId = jsonPathEvaluator.getInt("result");  // Поле "result" містить ID користувача

        System.out.println("Created User ID: " + userId);
        response.prettyPrint();

        return userId;
    }

    @Test
    public void testCreateUser() {
        userId = createUser();
    }

    @Test
    public void testRemoveUser() {

        String requestBody = "{\n" +
                "  \"jsonrpc\": \"2.0\",\n" +
                "  \"method\": \"removeUser\",\n" +
                "  \"id\": 15188630341,\n" +
                "  \"params\": {\n" +
                "    \"user_id\": " + userId + "\n" +
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


