package pk_MyNotes;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Logout_User {
	static String token;
	@BeforeTest
	public static void Login() throws IOException, ParseException {
		token=LoginAsExistingUser.before();
	}
	@Test
	public static void Logout() {
	RestAssured.baseURI = "https://practice.expandtesting.com";
	RequestSpecification request = RestAssured.given();
	request.header("Content-Type", "application/json");
	request.header("x-auth-token", token);
	Response response = request.request(Method.DELETE, "/notes/api/users/logout");
	// Response response = request.request(Method.POST,"/spree_oauth/token");
	response.prettyPrint();
	int statusCode = response.getStatusCode();
	Assert.assertEquals(statusCode, 200);}

}
