package pk_HotelBooking;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import freemarker.core.ParseException;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UpdateBooking {
	// Global Variable
	String bookingid;
	String outh_token;

	@BeforeTest
	public void before() {

		BaseClass.createToken("admin", "password123");
		bookingid = BaseClass.bookindid;
		outh_token = BaseClass.outh_token;
	}

	@Test(priority = 1)
	public void UpdateBooking() throws IOException, ParseException, org.json.simple.parser.ParseException {

		JSONObject prodjsonobj = BaseClass.ReadFile(".\\TestData\\UpdateBooking.json");

		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(prodjsonobj.toJSONString());
		// POST the Response
		Response response = request.request(Method.POST, "/booking");
		// Response response = request.request(Method.POST,"/spree_oauth/token");
		response.prettyPrint();
		int statusCode = response.getStatusCode();
		// System.out.println(response.asString());
		Assert.assertEquals(statusCode, 200);
		// To get the Token from JSON Response
		JsonPath jsonPathEvaluator = response.getBody().jsonPath();
		String fname = jsonPathEvaluator.get("booking.firstname").toString();
		System.out.println("First Name is =>  " + fname);
		Assert.assertEquals("James", fname);
		bookingid = jsonPathEvaluator.get("bookingid").toString();

	}

}
