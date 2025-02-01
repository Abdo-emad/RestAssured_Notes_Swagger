package Tests;

import APIBuilders.Builders;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.io.IOException;

import static Tests.P01_LoginAPITest_GenerateAccessToken.token;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class P04_DeleteTestUsingITestContextToGetNoteIdFromAnotherTestClass {

    //Deleting Note By ID Test Method
    @Severity(SeverityLevel.CRITICAL)
    @Description("Validate Deleting Note By Id")
    @Test()
    public void DeleteNoteById(ITestContext context) throws IOException {
        //Using ITestContext To get id from another class
      //  int id= (int) context.getSuite().getAttribute("noteId"); for test suite tests in testng file
       int id= (int) context.getAttribute("noteId");
        given().relaxedHTTPSValidation().spec(Builders.RequestBuilder())
                .header("x-auth-token",token)
                .when().delete("/notes/api/notes/"+ id).then().
                spec(Builders.ResponseBuilder()).assertThat().
                body("message",equalTo("Note successfully deleted")).
                extract().response();
    }
}
