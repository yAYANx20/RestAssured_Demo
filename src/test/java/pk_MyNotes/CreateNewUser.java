package pk_MyNotes;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class CreateNewUser {
	String outh_token;

	@Test
	public void before() {

		BaseClass.Create_New_User("ertyu", "wertyui@gmail.com", "Epsilon@123");
	}

}
