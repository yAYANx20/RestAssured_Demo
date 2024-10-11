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


public class Update_User {
	String outh_token;

	@BeforeTest
	public void before_test() {
		outh_token=BaseClass.createToken("ayanakash936@gmail.com","Epsilon@123");
	}
	
	@Test
	public void UpdateMyUser() {
		BaseClass.Update_UserName("Yojendra", outh_token);
	}

}
