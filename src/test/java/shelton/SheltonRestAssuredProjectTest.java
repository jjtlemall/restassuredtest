package shelton;

import org.junit.Test;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import org.testng.annotations.*;
import org.testng.Assert;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class SheltonRestAssuredProjectTest {

    String port = "8090";
    String lastId ="";

    @Test
    void testGetAll() {
        Response response = get("http://localhost:" + port + "/api/reviews");
        System.out.println(response.asString());

        int statusCode = response.getStatusCode();

        Assert.assertEquals(statusCode, 204);
        System.out.println("Get all passed");
    }
    @Test
    void testPost () {
        String requestBody = "{\n" +
                "  \"stars\": 3, \n" +
                "  \"productDescription\": \"very very good\", \n" +
                "  \"reviewComments\": \"oh ok\", \n" +
                "  \"contactPhone\": \"12345678\", \n" +
                "  \"contactEmail\": \"email@email.com\", \n" +
                "  \"actionNeeded\": \"false\", \n}";
        Response response = 
                given() 
                        .header("Content-type", "application/json")
                        .and()
                        .body(requestBody)
                        .when()
                        .post("http://localhost:" + port + "/api/reviews/")
                        .then()
                        .extract().response();
        Assert.assertEquals(response.statusCode(), 201);
        lastId = response.jsonPath().getString("id");

            System.out.println("last ID after post: " + lastId);
            System.out.println("Post passed");
    }

    @Test
    void testPut () {

        String requestBody = "{\n" +
                "  \"stars\": 3, \n" +
                "  \"productDescription\": \"very very good\", \n" +
                "  \"reviewComments\": \"oh ok\", \n" +
                "  \"contactPhone\": \"12345678\", \n" +
                "  \"contactEmail\": \"email@email.com\", \n" +
                "  \"actionNeeded\": \"true\", \n}";
        Response response = 
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(requestBody)
                        .when()
                        .put("http://localhost:" + port + "/api/reviews/" + lastId)
                        .then()
                        .extract().response();
            Assert.assertEquals(response.statusCode(), 200);
            lastId= response.jsonPath().getString("id");
            System.out.println("last ID after put: " + lastId);
            System.out.println("Put passed");

    }

    @Test
    void testDeleteById() {
        Response response = 
            given() 
                    .header("Content-type", "application/json")
                    .when()
                    .delete("http://localhost:" + port + "/api/reviews/" + lastId)
                    .then()
                    .extract().response();
        int statusCode = response.statusCode();

        boolean validResult = (statusCode == 200) || (statusCode == 204);
        Assert.assertEquals(validResult, true);

        System.out.println("Delete by id passed");
    }
    
}
