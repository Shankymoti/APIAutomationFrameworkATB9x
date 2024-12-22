package com.shashankkumar.tests.integration;

import com.shashankkumar.base.BaseTest;
import com.shashankkumar.endpoints.APIConstants;
import com.shashankkumar.pojos.BookingResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;

public class TestIntegrationFlow4 extends BaseTest {
  //  Create Booking -> Update it -> Try to Delete

    @Test(groups = {"qa"}, priority = 1)
    @Owner("Shashank")
    @Description("TC#INT1 Step 1: Create Booking")
    public void testCreateBooking(ITestContext iTestContext){
        String basePathPOST = APIConstants.CREATE_UPDATE_BOOKING_URL;

        requestSpecification.basePath(basePathPOST);

        response = RestAssured.given(requestSpecification).body(payloadManager.createPayloadBookingAsString())
                .when().log().all().post();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());

        assertThat(bookingResponse.getBookingid()).isNotNull().isPositive().isNotZero();
        iTestContext.setAttribute("bookingid", bookingResponse.getBookingid());
    }

    @Test(groups = {"qa"}, priority = 2)
    @Owner("Shashank")
    @Description("TC#INT2 Step 2 : Update Booking")
    public void testUpdateBooking(ITestContext iTestContext){
        Integer bookingID = (Integer)iTestContext.getAttribute("bookingid");
        String token = getToken();
        iTestContext.setAttribute("token",token);

        String basePathPUT = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingID;

        requestSpecification.basePath(basePathPUT);

        response = RestAssured.given(requestSpecification).cookie("token",token)
                .body(payloadManager.fullUpdatePayloadAsString())
                .when().log().all().put();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);


    }

    @Test(groups = {"qa"}, priority = 3)
    @Owner("Shashank")
    @Description("TC#INT3 Step 3 : Delete Booking")
    public void testDeleteBooking(ITestContext iTestContext){
        String token = (String) iTestContext.getAttribute("token");
        Integer bookingID = (Integer) iTestContext.getAttribute("bookingid");

        String basePathDELETE =  APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingID;

        requestSpecification.basePath(basePathDELETE);
        response = RestAssured.given().spec(requestSpecification).cookie("token", token)
                .when().log().all().delete();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(201);


    }
}
