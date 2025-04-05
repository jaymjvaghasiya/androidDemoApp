package com.example.demoapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BookActivity extends AppCompatActivity {

    EditText bookName;
    EditText bookPrice;
    Spinner bookCategory;
    Button calPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        Binding
        bookName = findViewById(R.id.edtBookName);
        bookPrice = findViewById(R.id.edtBookPrice);
        String cate[] = {"Select Category", "Education: 5%", "Dev: 8%", "Enteirnment: 18%"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cate);
        bookCategory.setAdapter(arrayAdapter);

        calPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = bookName.getText().toString();

                String strPrice = bookPrice.getText().toString();
                Double price = 0d;
                Boolean flag = false;
                if(name.isBlank()) {
                    bookName.setError("Please enter book name");
                    flag = true;
                }
                if(strPrice.isBlank()) {
                    bookPrice.setError("Please enter book price");
                    flag = true;
                } else {
                    price = Double.parseDouble(bookPrice.getText().toString());
                }

                if(flag) {
                    Toast.makeText(getApplicationContext(), "This field is required", Toast.LENGTH_LONG).show();
                } else {

                }
            }
        });
    }
}