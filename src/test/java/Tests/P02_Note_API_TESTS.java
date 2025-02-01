package Tests;

import APIBuilders.Builders;
import Classes_Serialization.P02_CreateNewNote;
import Classes_Serialization.P03_UpdateStatusOfNote;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class P02_Note_API_TESTS extends P01_LoginAPITest_GenerateAccessToken{
    P02_CreateNewNote p02_createNewNote;
    P03_UpdateStatusOfNote p03_updateStatusOfNote;
    String noteId;
    Faker faker= new Faker();
  List<String> category = new ArrayList<>();

 //Create New Note Test Method
    @Severity(SeverityLevel.CRITICAL)
    @Description("validate creating New Note")
    @Test
    public void CreateNewNote() throws IOException {
        p02_createNewNote = new P02_CreateNewNote();
        category.add("Home");
        category.add("Work");
        category.add("Personal");
        Random random = new Random();
        int randomIndex = random.nextInt(category.size());
        p02_createNewNote = new P02_CreateNewNote();
        p02_createNewNote.title=faker.name().title();
        p02_createNewNote.description=faker.name().name();
        p02_createNewNote.category=category.get(randomIndex);
       String CreateNoteResponse=  given().relaxedHTTPSValidation().
               spec(Builders.RequestBuilder()).header("x-auth-token",token)
                .body(p02_createNewNote)
                .when().post("/notes/api/notes").then().spec(Builders.ResponseBuilder()).extract().response().asString();
                JsonPath jsonPath = new JsonPath(CreateNoteResponse);
        Assert.assertEquals("Note successfully created",jsonPath.getString("message"));
        System.out.println("Success Message: "+jsonPath.getString("message"));
        noteId = jsonPath.getString("data.id");

    }

    //Getting Note By ID Test Method
    @Severity(SeverityLevel.CRITICAL)
    @Description("validate Getting Note By Id")
    @Test(dependsOnMethods = {"CreateNewNote"})
    public void GetNoteById() throws IOException {
        Response res= given().relaxedHTTPSValidation().spec(Builders.RequestBuilder())
                .header("x-auth-token",token)
                .when().get("/notes/api/notes/"+ noteId).then().
                  spec(Builders.ResponseBuilder()).assertThat().
                  body("message",equalTo("Note successfully retrieved")).
                  extract().response();
        String message= res.path("message.status");
        System.out.println(message);
    }

    //Updating Note By ID Test Method
    @Severity(SeverityLevel.CRITICAL)
    @Description("validate Updating The Complete Status Of A Note")
    @Test(dependsOnMethods = {"GetNoteById"})
    public void UpdateTheCompleteStatusOfNote() throws IOException {
       p03_updateStatusOfNote = new P03_UpdateStatusOfNote();
       p03_updateStatusOfNote.completed=true;
        given().relaxedHTTPSValidation().spec(Builders.RequestBuilder())
                .body(p03_updateStatusOfNote)
                .header("x-auth-token",token)
                .when().patch("/notes/api/notes/"+ noteId).then().
                spec(Builders.ResponseBuilder()).assertThat().
                body("message",equalTo("Note successfully Updated")).
                extract().response();
    }

    //Deleting Note By ID Test Method
    @Severity(SeverityLevel.CRITICAL)
    @Description("Validate Deleting Note By Id")
    @Test(dependsOnMethods = {"UpdateTheCompleteStatusOfNote"})
    public void DeleteNoteById() throws IOException {

        given().relaxedHTTPSValidation().spec(Builders.RequestBuilder())
                .header("x-auth-token",token)
                .when().delete("/notes/api/notes/"+ noteId).then().
                spec(Builders.ResponseBuilder()).assertThat().
                body("message",equalTo("Note successfully deleted")).
                extract().response();
    }

}
