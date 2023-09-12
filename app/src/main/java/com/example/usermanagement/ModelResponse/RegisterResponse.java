package com.example.usermanagement.ModelResponse;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse
{
    String error;
    /* If u want to change ur original string name then use this :-
    @SerializedName("message")
    String msg;
    */
    String message;

    public RegisterResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
