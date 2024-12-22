package com.shashankkumar.modules;

import com.google.gson.Gson;
import com.shashankkumar.pojos.*;

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


    public Booking getResponseFromJSON(String getResponse){
    gson = new Gson();
    Booking booking = gson.fromJson(getResponse, Booking.class);
    return booking;
    }

    // -------------- TOKEN ---------------------
    // Java to Json
    public String setAuthPayload(){
        Auth auth = new Auth();
        auth.setUsername("admin");
        auth.setPassword("password123");
        System.out.println(auth);
        gson =  new Gson();
        String jsonStringPayload = gson.toJson(auth);
        System.out.println(auth);
        return  jsonStringPayload;
    }


    //Json to Java
    public String  getTokenFromJSON(String tokenResponse){
    gson = new Gson();
        TokenResponse tokenResponse1 = gson.fromJson(tokenResponse,TokenResponse.class);
        return tokenResponse1.getToken(); // here only one field is there. So directly return token

    }


    public String fullUpdatePayloadAsString(){

        Booking booking = new Booking();
        booking.setFirstname("Pramod");
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
}
