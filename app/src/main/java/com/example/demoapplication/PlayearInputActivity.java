package com.example.demoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PlayearInputActivity extends AppCompatActivity {

    EditText username;
    EditText batAmt;
    Button play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_playear_input);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        username = findViewById(R.id.edtPlyearDataUsername);
        batAmt = findViewById(R.id.edtPlyearDataBatAmt);
        play = findViewById(R.id.btnPlyearDataPlay);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String unm = username.getText().toString();
                String amt = batAmt.getText().toString();

                Intent intent = new Intent(getApplicationContext(), PlayGameActivity.class);
                intent.putExtra("username", unm);
                intent.putExtra("amount", amt);
                startActivity(intent);
            }
        });
    }
}