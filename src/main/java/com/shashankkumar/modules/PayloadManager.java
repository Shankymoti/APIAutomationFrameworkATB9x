package com.shashankkumar.modules;

import com.google.gson.Gson;
import com.shashankkumar.pojos.Booking;
import com.shashankkumar.pojos.BookingResponse;
import com.shashankkumar.pojos.Bookingdates;

public class PayloadManager {

    //Converting JAVA Object to String

Gson gson;
public String createPayloadBookingAsString(){
    Booking booking = new Booking();
    booking.setFirstname("Shashank");
    booking.setLastname("Singh");
    booking.setTotalprice(1234);
    booking.setDepositpaid(true);
    Bookingdates bookingdates = new Bookingdates();
    bookingdates.setCheckin("2024-02-01");
    bookingdates.setCheckout("2024-02-01");
    booking.setBookingdates(bookingdates);
    booking.setAdditionalneeds("Breakfast");

    System.out.println(booking);

    gson =  new Gson();
    String jsonStringPayload = gson.toJson(booking);
    System.out.println(jsonStringPayload);
    return jsonStringPayload;
}


    //Converting String to Java Object

    public BookingResponse bookingResponseJava(String responseString){
    gson =new Gson();
    BookingResponse bookingResponse = gson.fromJson(responseString, BookingResponse.class);
    return bookingResponse;
    }
}
