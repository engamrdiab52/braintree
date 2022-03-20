package com.amrabdelhamiddiab.braintree1;

import retrofit.RequestInterceptor;

public class ApiClientRequestInterceptor implements RequestInterceptor {

    @Override
    public void intercept(RequestFacade request) {
        request.addHeader("User-Agent", "braintree/android-demo-app/" + "6.0.3-SNAPSHOT");
        request.addHeader("Accept", "application/json");
    }
}
