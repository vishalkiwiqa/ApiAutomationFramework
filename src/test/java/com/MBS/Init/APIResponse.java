package com.MBS.Init;

import io.restassured.response.Response;

public class APIResponse {

    private Response response;

    private APIResponse() {
    }

    private static APIResponse apiResponse = new APIResponse();

    public static APIResponse getInstance() {
        return apiResponse;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    /*public Response getResponse(Response get) {
        return response;

    }*/

}
