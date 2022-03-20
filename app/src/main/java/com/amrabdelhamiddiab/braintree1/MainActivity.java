package com.amrabdelhamiddiab.braintree1;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.braintreepayments.api.CardNonce;
import com.braintreepayments.api.DropInClient;
import com.braintreepayments.api.DropInPaymentMethod;
import com.braintreepayments.api.DropInRequest;
import com.braintreepayments.api.DropInResult;
import com.braintreepayments.api.GooglePayCardNonce;
import com.braintreepayments.api.PayPalAccountNonce;
import com.braintreepayments.api.PaymentMethodNonce;
import com.braintreepayments.api.VenmoAccountNonce;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static String TAG = "MainActivity";
    private static final int DROP_IN_REQUEST = 100;
    private static final String SANDBOX_BASE_SERVER_URL =
            "https://braintree-sample-merchant.herokuapp.com";

    Button button;
    Button button_purchase;
    DropInClient dropInClient;
    private PaymentMethodNonce nonce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button_purchase = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                launchDropIn(view);
            }
        });
        button_purchase.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d(TAG, "Purchase button clicked");
                purchase(view);
            }
        });

        DropInRequest dropInRequest = new DropInRequest();
        dropInRequest.setMaskCardNumber(true);
        //  dropInRequest.setMaskSecurityCode(true);
        dropInClient = new DropInClient(this, "sandbox_tmxhyf7d_dcpspy2brwdjr3qn", dropInRequest);
    }

    public void purchase(View v) {
        Intent intent = new Intent(this, CreateTransactionActivity.class)
                .putExtra(CreateTransactionActivity.EXTRA_PAYMENT_METHOD_NONCE, nonce);
        startActivity(intent);

        //    purchased = true;
    }

    public void launchDropIn(View v) {
        dropInClient.launchDropInForResult(this, DROP_IN_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            DropInResult result = null;
            if (data != null) {
                result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
            }
            displayResult(result);
        } else if (resultCode != RESULT_CANCELED) {
            showDialog(Integer.parseInt(Objects.requireNonNull(((Exception) Objects.requireNonNull(data).getSerializableExtra(DropInResult.EXTRA_ERROR))
                    .getMessage())));
        }
    }

    private void displayResult(DropInResult dropInResult) {
        nonce = dropInResult.getPaymentMethodNonce();
        if (nonce != null) {
            Log.d(TAG,"nonce value = "+nonce);
        }
        DropInPaymentMethod paymentMethodType = dropInResult.getPaymentMethodType();
        if (paymentMethodType != null) {
            Log.d(TAG, "454545454545"+paymentMethodType.getLocalizedName()
                    + "........." + dropInResult.getPaymentDescription()
                    + "Deviceeeeeee" + dropInResult.getDeviceData());
        }
        String details = "";
        if (nonce instanceof CardNonce) {
            CardNonce cardNonce = (CardNonce) nonce;
            details = "Card Last Two: " + cardNonce.getLastTwo() + "\n";
            details += "3DS isLiabilityShifted: " + cardNonce.getThreeDSecureInfo().isLiabilityShifted() + "\n";
            details += "3DS isLiabilityShiftPossible: " + cardNonce.getThreeDSecureInfo().isLiabilityShiftPossible();
            Log.d(TAG, "trueeeeee" + details);
        }
    }
}