package com.amrabdelhamiddiab.braintree1;

import com.google.gson.annotations.SerializedName;

public class Transaction {

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }
}
