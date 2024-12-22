package com.shashankkumar.pojos;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllBookingResponse {

    @SerializedName("bookingid")
    @Expose
    private Integer bookingid;

    public Integer getBookingid() {
        return bookingid;
    }

    public void setBookingid(Integer bookingid) {
        this.bookingid = bookingid;
    }

}
