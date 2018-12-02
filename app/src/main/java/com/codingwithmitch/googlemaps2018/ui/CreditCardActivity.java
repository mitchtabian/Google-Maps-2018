package com.codingwithmitch.googlemaps2018.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.codingwithmitch.googlemaps2018.R;
import com.craftman.cardform.Card;
import com.craftman.cardform.CardForm;
import com.craftman.cardform.OnPayBtnClickListner;

public class CreditCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);

        CardForm cardFrom = (CardForm) findViewById(R.id.creditcardform);
        TextView txtDes = findViewById(R.id.payment_amount);
        Button btnPay = (Button) findViewById(R.id.btn_pay);

        txtDes.setText("$5");
        btnPay.setText(String.format("Payer %s",txtDes.getText()));


        cardFrom.setPayBtnClickListner(new OnPayBtnClickListner() {
            @Override
            public void onClick(Card card) {
                Toast.makeText(CreditCardActivity.this, "Card Added", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
