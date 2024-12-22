package com.shashankkumar.tests.integration;

import com.shashankkumar.base.BaseTest;
import com.shashankkumar.endpoints.APIConstants;
import com.shashankkumar.pojos.Booking;
import com.shashankkumar.pojos.BookingResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestIntegrationFlow5 extends BaseTest {
    // Delete a Booking -> Try to Update it
    // We can first create booking or we can get one booking from  all booking then delete it and then try to edit it
    @Test
    public void testCreateBooking(ITestContext iTestContext) {
        String basePathPOST = APIConstants.CREATE_UPDATE_BOOKING_URL;

        requestSpecification.basePath(basePathPOST);
        response = RestAssured.given(requestSpecification).body(payloadManager.createPayloadBookingAsString())
                .when().log().all().post();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());
        assertThat(bookingResponse.getBookingid()).isNotNull();

        iTestContext.setAttribute("bookingid", bookingResponse.getBookingid());
    }

    @Test(dependsOnMethods = "testCreateBooking", priority = 1)
    @Owner("Shashank")
    @Description("TC#INT1 Steps 1 : Update Booking")
    public void testDelete(ITestContext iTestContext) {
        Integer bookingId = (Integer) iTestContext.getAttribute("bookingid");
        String token = getToken();
        iTestContext.setAttribute("token", token);
        String basePathDELETE = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingId;

        requestSpecification.basePath(basePathDELETE);
        response = RestAssured.given(requestSpecification).cookie("token", token)
                .when().log().all().delete();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(201);

    }

    @Test(dependsOnMethods = "testCreateBooking", priority = 1)
    @Owner("Shashank")
    @Description("TC#INT1 Steps 1 : Update Booking")
    public void testUpdate(ITestContext iTestContext) {
        System.out.println(iTestContext.getAttribute("bookingid"));
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        System.out.println(bookingid);

        String token = (String) iTestContext.getAttribute("token");
        String basePathPUTPATCH = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        System.out.println(basePathPUTPATCH);
        requestSpecification.basePath(basePathPUTPATCH);
        response = RestAssured.given(requestSpecification).
                cookie("token", token)
                .when().body(payloadManager.fullUpdatePayloadAsString()).log().all().put();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(405);

    }

}