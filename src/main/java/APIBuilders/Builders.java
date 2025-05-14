package APIBuilders;

import Utils.ReadDataFromPropFile;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class Builders {
    static RequestSpecification requestSpecification;
    static ResponseSpecification responseSpecification;
    public static RequestSpecification RequestBuilder() throws IOException {
        FileOutputStream fos = new FileOutputStream("log.txt");
        PrintStream printStream = new PrintStream(fos);
        requestSpecification = new RequestSpecBuilder().
                setBaseUri(ReadDataFromPropFile.AccessPropFile("BaseURL")).
                setContentType(ContentType.JSON)
                .addFilter(RequestLoggingFilter.logRequestTo(printStream))
                .addFilter(ResponseLoggingFilter.logResponseTo(printStream))
                .build();
        return requestSpecification;
    }

    public static ResponseSpecification ResponseBuilder(){
        responseSpecification = new ResponseSpecBuilder().
                expectStatusCode(200).build();
        return responseSpecification;
    }
}
