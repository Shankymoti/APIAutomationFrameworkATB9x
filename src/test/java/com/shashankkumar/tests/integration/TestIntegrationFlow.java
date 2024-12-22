package com.shashankkumar.tests.integration;

import com.shashankkumar.base.BaseTest;
import com.shashankkumar.endpoints.APIConstants;
import com.shashankkumar.pojos.Booking;
import com.shashankkumar.pojos.BookingResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestIntegrationFlow extends BaseTest {


    @Test(groups = "qa", priority = 1)
    @Owner("Shashank")
    @Description("TC#INT1 - Step 1. Verify that the Booking can be Created")
    public void testCreateBooking(ITestContext iTestContext){
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response = RestAssured.given(requestSpecification)
                .when().body(payloadManager.createPayloadBookingAsString()).log().all().post();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());
        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(),"Shashank");
        System.out.println(bookingResponse.getBookingid());

        iTestContext.setAttribute("bookingid",bookingResponse.getBookingid());

    }

    @Test(groups = "qa", priority = 2)
    @Owner("Shashank")
    @Description("TC#INT1 - Step 2. Verify that the Booking By ID")
    public void testVerifyBookingId(ITestContext iTestContext){
        System.out.println(iTestContext.getAttribute("bookingid"));

        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");

        //Get Request : Verify that the first name after creation is Shashank
        String basePathGet = APIConstants.CREATE_UPDATE_BOOKING_URL + "/"  + bookingid;
        System.out.println(basePathGet);

        requestSpecification.basePath(basePathGet);
        response = RestAssured.given(requestSpecification).log().all()
                .when().get();

        validatableResponse =response.then().log().all();
        validatableResponse.statusCode(200);

        Booking booking = payloadManager.getResponseFromJSON(response.asString());
        assertThat(booking.getFirstname()).isNotEmpty().isNotBlank().isNotNull();
        assertThat(booking.getFirstname()).isEqualTo("Shashank");
    }

    @Test(groups = "qa", priority = 3)
    @Owner("Shashank")
    @Description("TC#INT1 - Step 3. Verify Updated Booking by ID")
    public void testUpdateBookingByID(ITestContext iTestContext){
        System.out.println(iTestContext.getAttribute("bookingid"));
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        System.out.println(bookingid);
        String token = getToken();
        iTestContext.setAttribute("token",token);
        String basePathPUTPATCH = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        System.out.println(basePathPUTPATCH);
        requestSpecification.basePath(basePathPUTPATCH);
        response = RestAssured.given(requestSpecification).
                cookie("token", token)
                .when().body(payloadManager.fullUpdatePayloadAsString()).log().all().put();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        Booking booking = payloadManager.getResponseFromJSON(response.asString());
      assertThat(booking.getFirstname()).isNotNull().isNotBlank().isNotEmpty();
      assertThat(booking.getFirstname()).isEqualTo("Pramod");
      assertThat(booking.getLastname()).isEqualTo("Singh");

    }

    @Test(groups = "qa", priority = 4)
    @Owner("Shashank")
    @Description("TC#INT1 - Step 4. Delete the Booking by ID")
    public void testDeleteBookingById(ITestContext iTestContext){
        System.out.println(iTestContext.getAttribute("bookingid"));
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        System.out.println(bookingid);
        String token = (String) iTestContext.getAttribute("token");
        System.out.println(token);
        String basePathDELETE = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        System.out.println(basePathDELETE);

        requestSpecification.basePath(basePathDELETE);
        validatableResponse = RestAssured.given().spec(requestSpecification).cookie("token",token)
                .when().delete().then().log().all();
        validatableResponse.statusCode(201);

    }
}
