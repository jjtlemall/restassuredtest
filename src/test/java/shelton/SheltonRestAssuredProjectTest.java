package shelton;

import org.junit.Test;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import org.testng.annotations.Test;
import org.testng.Assert;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class SheltonRestAssuredProjectTest {

    String port = "8090";
    String lastId ="";

    @Test
    public void testGetAll() {
        Response response = get("https://api.open-meteo.com/v1/forecast?latitude=38.6270&longitude=90.1994&hourly=temperature_2m,relativehumidity_2m,windspeed_10m");
        System.out.println(response.asString());

        int statusCode = response.getStatusCode();

        Assert.assertEquals(statusCode, 200);
        System.out.println("Get all passed");
    }
    @Test
    public void testPost () {
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
                        .post("https://api.open-meteo.com/v1/forecast?latitude=38.6270&longitude=90.1994&hourly=temperature_2m,relativehumidity_2m,windspeed_10m")
                        .then()
                        .extract().response();
        Assert.assertEquals(response.statusCode(), 204);
        lastId = response.jsonPath().getString("id");

            System.out.println("last ID after post: " + lastId);
            System.out.println("Post passed");
    }

    @Test
    public void testPut () {

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
                        .put("https://api.open-meteo.com/v1/forecast?latitude=38.6270&longitude=90.1994&hourly=temperature_2m,relativehumidity_2m,windspeed_10m")
                        .then()
                        .extract().response();
            Assert.assertEquals(response.statusCode(), 200);
            lastId= response.jsonPath().getString("id");
            System.out.println("last ID after put: " + lastId);
            System.out.println("Put passed");

    }

    @Test
    public void testDeleteById() {
        Response response = 
            given() 
                    .header("Content-type", "application/json")
                    .when()
                    .delete("https://api.open-meteo.com/v1/forecast?latitude=38.6270&longitude=90.1994&hourly=temperature_2m,relativehumidity_2m,windspeed_10m")
                    .then()
                    .extract().response();
        int statusCode = response.statusCode();

        boolean validResult = (statusCode == 200) || (statusCode == 204);
        Assert.assertEquals(validResult, true);

        System.out.println("Delete by id passed");
    }
    
}
