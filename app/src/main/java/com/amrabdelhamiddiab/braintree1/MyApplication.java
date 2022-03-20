package com.amrabdelhamiddiab.braintree1;

import static com.amrabdelhamiddiab.braintree1.MainActivity.TAG;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import retrofit.RestAdapter;

public class MyApplication extends Application {
    private static ApiClient apiClient;

    static ApiClient getApiClient(Context context) {
        if (apiClient == null) {
            apiClient = new RestAdapter.Builder()
                    .setEndpoint("https://braintree-sample-merchant.herokuapp.com")
                    .setRequestInterceptor(new ApiClientRequestInterceptor())
                    .build()
                    .create(ApiClient.class);
        }
        Log.d(TAG,"API client"+ apiClient.toString());
        return apiClient;
    }

    static void resetApiClient() {
        apiClient = null;
    }
}
