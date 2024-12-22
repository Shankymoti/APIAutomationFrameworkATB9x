package com.shashankkumar.tests.integration;

import com.shashankkumar.base.BaseTest;
import com.shashankkumar.endpoints.APIConstants;
import com.shashankkumar.pojos.AllBookingResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestIntegrationFlow3 extends BaseTest {

    //Get a Booking from Get All -> Try to Delete that booking

    @Test(groups = {"qc"}, priority = 1)
    @Owner("Shashank")
    @Description("TC#INT1 Step 1 : Get all Booking")
    public void testGetABookingFromAllBooking(ITestContext iTestContext){
        String basePathAllGet = APIConstants.CREATE_UPDATE_BOOKING_URL;

        requestSpecification.basePath(basePathAllGet);
        response = RestAssured.given(requestSpecification)
                .when().log().all().get();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        List<AllBookingResponse> allBookingResponse = payloadManager.getABooking(response.asString());

        // Print the first bookingid from the list
        if (!allBookingResponse.isEmpty()) {
            System.out.println("First Booking ID: " + allBookingResponse.get(0).getBookingid());
            iTestContext.setAttribute("bookingid",allBookingResponse.get(0).getBookingid());
        } else {
            System.out.println("No bookings available.");
        }




    }

    @Test(groups = {"qa"}, priority = 2)
    @Owner("Shashank")
    @Description("TC#INT2 Step 2: Delete Booking")
    public void testDeleteBooking(ITestContext iTestContext){
        Integer bookingId = (Integer) iTestContext.getAttribute("bookingid");
        System.out.println(bookingId);
        String token = getToken();
        System.out.println(token);

        String basePathDelete = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingId;
        requestSpecification.basePath(basePathDelete).log().all();

        response = RestAssured.given(requestSpecification).cookie("token", token).when().log().all().delete();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(201);
    }
}
