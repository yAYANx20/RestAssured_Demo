package pk_MyNotes;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseClass {
	public static String outh_token;

	public static String createToken(String email, String pass) {

		RestAssured.baseURI = "https://practice.expandtesting.com/notes/api/";
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject();
		requestParams.put("email", email);
		requestParams.put("password", pass);
		// Add a header stating the Request body is a JSON
		request.header("Content-Type", "application/json");
		request.body(requestParams.toJSONString());
		// POST the Response
		Response response = request.request(Method.POST, "/users/login");
		// Response response = request.request(Method.POST,"/spree_oauth/token");
		response.prettyPrint();
		int statusCode = response.getStatusCode();
		// System.out.println(response.asString());
		Assert.assertEquals(statusCode, 200);
		// To get the Token from JSON Response
		JsonPath jsonPathEvaluator = response.getBody().jsonPath();
		outh_token = jsonPathEvaluator.get("data.token").toString();
		System.out.println("oAuth Token is =>  " + outh_token);
		return outh_token;
	}

	public static void Create_New_User(String name, String email, String pass) {

		RestAssured.baseURI = "https://practice.expandtesting.com/notes/api/";
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject();
		requestParams.put("name", name);
		requestParams.put("email", email);
		requestParams.put("password", pass);
		// Add a header stating the Request body is a JSON
		request.header("Content-Type", "application/json");
		request.body(requestParams.toJSONString());
		// POST the Response
		Response response = request.request(Method.POST, "/users/register");
		
		response.prettyPrint();
		int statusCode = response.getStatusCode();
		// System.out.println(response.asString());
		Assert.assertEquals(statusCode, 201);
		// To get the Token from JSON Response
		JsonPath jsonPathEvaluator = response.getBody().jsonPath();
		String success_msg = jsonPathEvaluator.get("message").toString();
		Assert.assertEquals(success_msg, "User account created successfully");
	}
	
	public static void Update_UserName(String name, String token) {

		RestAssured.baseURI = "https://practice.expandtesting.com";
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject();
		request.header("Content-Type", "application/json");
		request.header("x-auth-token", token);
		requestParams.put("name", name);
//		requestParams.put("phone", "");
//		requestParams.put("company", "");
		// Add a header stating the Request body is a JSON
		request.body(requestParams.toJSONString());
		// POST the Response
		Response response = request.request(Method.PATCH, "/notes/api/users/profile");
		
		response.prettyPrint();
		int statusCode = response.getStatusCode();
		// System.out.println(response.asString());
		Assert.assertEquals(statusCode, 200);
		// To get the Token from JSON Response
		JsonPath jsonPathEvaluator = response.getBody().jsonPath();
		String success_msg = jsonPathEvaluator.get("message").toString();
		Assert.assertEquals(success_msg, "Profile updated successful");
	}

	public static JSONObject ReadFile(String File) throws IOException, ParseException {
		// Create json object of JSONParser class to parse the JSON data
		JSONParser jsonparser = new JSONParser();
		// Create object for FileReader class, which help to load and read JSON file
		FileReader reader = new FileReader(File);
		// Returning/assigning to Java Object
		Object obj = jsonparser.parse(reader);
		// Convert Java Object to JSON Object, JSONObject is typecast here
		JSONObject prodjsonobj = (JSONObject) obj;
		return prodjsonobj;
	}
	public void deleteAccount(String token) throws IOException, ParseException, org.json.simple.parser.ParseException 
	  {
		  RestAssured.baseURI = "https://practice.expandtesting.com";
	      RequestSpecification request = RestAssured.given();
		  request.header("Content-Type", "application/json");
		  request.header("X-Auth-Token", token);
	      Response response = request.request(Method.DELETE,"/notes/api/users/delete-account");
	      response.prettyPrint();
	      int statusCode = response.getStatusCode();
	      Assert.assertEquals(statusCode, 200);
	      System.out.println("Account deleted!");
	  }

}
