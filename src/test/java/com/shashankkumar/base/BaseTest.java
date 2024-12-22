package com.shashankkumar.base;

import com.shashankkumar.asserts.AssertActions;
import com.shashankkumar.endpoints.APIConstants;
import com.shashankkumar.modules.PayloadManager;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeTest;

public class BaseTest {
    // Common To ALL
    // BASE URL and contentType - json - common

    public RequestSpecification requestSpecification;
    public AssertActions assertActions;  // User Defined class
    public PayloadManager payloadManager; // User Defined class
    public JsonPath jsonPath;
    public Response response;
    public ValidatableResponse validatableResponse;

    @BeforeTest
    public void setUp(){
        payloadManager = new PayloadManager();
        assertActions =  new AssertActions();

//        requestSpecification = RestAssured
//                .given()
//                .basePath(APIConstants.BASE_URL)
//                .contentType(ContentType.JSON)
//                .log().all();
        // either we can  use  above way or below way

        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(APIConstants.BASE_URL)
                .addHeader("Content-Type", "application/json")
                .build().log().all();



    }


    // getToken() is common to most of the Request. so we write in BaseTest class
    public String getToken(){
        requestSpecification = RestAssured.given()
                .baseUri(APIConstants.BASE_URL)
                .basePath(APIConstants.AUTH_URL);

        // Setting the payload
        String payload = payloadManager.setAuthPayload();

        //Get the token
        response = requestSpecification.contentType(ContentType.JSON).body(payload).when().post();

        //Extract the token
        String token = payloadManager.getTokenFromJSON(response.asString());
        return token;
    }
}
