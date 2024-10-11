package pk_HotelBooking;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CreateBooking {

    @Test
    public void createBooking() throws IOException, ParseException {
        // Create json object of JSONParser class to parse the JSON data
        JSONParser jsonParser = new JSONParser();
        // Create object for FileReader class, which help to load and read JSON file
        FileReader reader = new FileReader(".\\TestData\\CreateBooking_MultipleData.json");
        // Returning/assigning to Java Object
        Object obj = jsonParser.parse(reader);
        // Convert Java Object to JSON Array, JSONArray is typecast here
        JSONArray bookingArray = (JSONArray) obj;

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        // Iterate through each booking in the array
        for (Object bookingObject : bookingArray) {
            JSONObject bookingData = (JSONObject) bookingObject;
            createSingleBooking(bookingData);
        }
    }

    private void createSingleBooking(JSONObject bookingData) {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(bookingData.toJSONString());

        // POST the Response
        Response response = request.request(Method.POST, "/booking");
        response.prettyPrint();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);

        // To get the first name from JSON Response
        JsonPath jsonPathEvaluator = response.getBody().jsonPath();
        String firstName = jsonPathEvaluator.get("booking.firstname").toString();
        System.out.println("First Name is => " + firstName);
        Assert.assertEquals(bookingData.get("firstname"), firstName);
    }
}