package br.com.clubedosaplicativos.newsapp.model;

import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.ArrayList;

/**
 * Created by elifa on 22/09/2016.
 */
public class GoogleFeedResponse {
    private String responseDetails;
    private int responseStatus;
    private GoogleFeedResponseData responseData;

    public String getResponseDetails() {
        return responseDetails;
    }

    public void setResponseDetails(String responseDetails) {
        this.responseDetails = responseDetails;
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

    public GoogleFeedResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(GoogleFeedResponseData responseData) {
        this.responseData = responseData;
    }
}
