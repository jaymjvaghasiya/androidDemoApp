package com.example.demoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddProductActivity extends AppCompatActivity {

    EditText edtProductName;
    EditText edtProductPrice;
    EditText edtProductQty;
    Button showBillBtn;
    TextView tvTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        binding
        edtProductName = findViewById(R.id.edtAddProductProduceName);
        edtProductPrice = findViewById(R.id.edtAddProductProductPrice);
        edtProductQty = findViewById(R.id.edtAddProductProductQty);
        showBillBtn = findViewById(R.id.btnAddProductShowBill);
        tvTotalPrice = findViewById(R.id.tvAddProductTotalBill);

//        logic
        showBillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtProductName.getText().toString();
                Double price = Double.parseDouble(edtProductPrice.getText().toString());
                Integer qty = Integer.parseInt(edtProductQty.getText().toString());
                Double totalPrice = price * qty;

                Log.i("Product", name);
                Log.i("product", String.valueOf(price));
                Log.i("product", String.valueOf(qty));
                Log.i("product", String.valueOf(totalPrice));

                tvTotalPrice.setText("Total Price: " + totalPrice);
            }
        });

    }

}