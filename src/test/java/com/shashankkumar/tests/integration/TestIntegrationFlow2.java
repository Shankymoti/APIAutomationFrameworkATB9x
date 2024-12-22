package com.shashankkumar.tests.integration;

import com.shashankkumar.base.BaseTest;
import com.shashankkumar.endpoints.APIConstants;
import com.shashankkumar.pojos.BookingResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TestIntegrationFlow2 extends BaseTest {

    // Create Booking -> Delete it -> Verify

    @Test(groups = {"qa"},priority = 1)
    @Owner("Shashank")
    @Description("TC#INT1 - Step 1. Verify that the Booking can be Created")
    public void testCreationBooking(ITestContext iTestContext){

    requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
    response = RestAssured.given(requestSpecification).body(payloadManager.createPayloadBookingAsString())
            .when().log().all().post();

    validatableResponse = response.then().log().all();
    validatableResponse.statusCode(200);

        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());
        assertThat(bookingResponse.getBookingid()).isNotNull().isPositive();

       iTestContext.setAttribute("bookingid",bookingResponse.getBookingid());

    }

    @Test(groups = {"qa"}, priority = 2)
    @Owner("Shashank")
    @Description("TC#INT2 - Step 2. Delete the Booking by ID")
    public void testDeleteBooking(ITestContext iTestContext){

        Integer bookingId = (Integer) iTestContext.getAttribute("bookingid");
        System.out.println(bookingId);
        String token = getToken();
        System.out.println(token);

        String basePathDelete = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingId;
        System.out.println(basePathDelete);

        requestSpecification.basePath(basePathDelete).log().all();

        response = RestAssured.given(requestSpecification).cookie("token", token).when().log().all().delete();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(201);

    }
}
