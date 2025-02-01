package Tests;
import APIBuilders.Builders;
import Classes_Serialization.P01_LoginRequest;
import Utils.ReadDataFromPropFile;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeClass;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class P01_LoginAPITest_GenerateAccessToken {
    //User Login with Valid Credentials to get Access Token
    P01_LoginRequest p01_loginRequest;
    public  static String  token;
    @BeforeClass
    public void ValidLogin_GetAccessToken() throws IOException {
        p01_loginRequest= new P01_LoginRequest();
        p01_loginRequest.Email= ReadDataFromPropFile.AccessPropFile("Email");
        p01_loginRequest.Password=ReadDataFromPropFile.AccessPropFile("Password");
        String loginResponse=  given().relaxedHTTPSValidation()
                .spec(Builders.RequestBuilder()).body(p01_loginRequest)
                .when().post("/notes/api/users/login")
                .then().spec(Builders.ResponseBuilder()).extract().response().asString();
        JsonPath jsonPath = new JsonPath(loginResponse);
        token= jsonPath.getString("data.token");
        System.out.println("token:" + token);
    }
}
