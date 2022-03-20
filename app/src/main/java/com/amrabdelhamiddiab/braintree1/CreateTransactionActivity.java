package com.amrabdelhamiddiab.braintree1;

import static com.amrabdelhamiddiab.braintree1.MainActivity.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.braintreepayments.api.CardNonce;
import com.braintreepayments.api.PaymentMethodNonce;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CreateTransactionActivity extends AppCompatActivity {
    public static final String EXTRA_PAYMENT_METHOD_NONCE = "nonce";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_transaction);
        Log.d(TAG, "454545454545initialActivity"+(PaymentMethodNonce) getIntent().getParcelableExtra(EXTRA_PAYMENT_METHOD_NONCE));
        sendNonceToServer((PaymentMethodNonce) getIntent().getParcelableExtra(EXTRA_PAYMENT_METHOD_NONCE));
    }

    private void sendNonceToServer(PaymentMethodNonce nonce) {
        Callback<Transaction> callback = new Callback<Transaction>() {
            @Override
            public void success(Transaction transaction, Response response) {
                if (transaction.getMessage() != null &&
                        transaction.getMessage().startsWith("created")) {
                    Log.d(TAG, "TRANSACTION OK" + transaction.getMessage());
                } else {
                    if (TextUtils.isEmpty(transaction.getMessage())) {
                        Log.d(TAG, "TRANSACTION" + "Server response was empty or malformed");
                    } else {
                        Log.d(TAG, "TRANSACTION Error" + transaction.getMessage());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "TRANSACTION" + "Unable to create a transaction. Response Code: " +
                        error.getResponse().getStatus() + " Response body: " +
                        error.getResponse().getBody());
            }
        };
        MyApplication.getApiClient(this).createTransaction(nonce.getString(), "",
                callback);
        Log.d(TAG, "nonce" + nonce);
    }
}
