package com.example.demoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PlayGameActivity extends AppCompatActivity {

    TextView unm;
    TextView winAmt;
    TextView batAmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_play_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String batAmount = intent.getStringExtra("amount");

        unm = findViewById(R.id.tvPlayGameActUsername);
        winAmt = findViewById(R.id.tvPlayGameActWinningAmt);
        batAmt = findViewById(R.id.tvPlayGameActBatAmt);

        unm.setText(username);
        winAmt.setText("0");
        batAmt.setText(batAmount);

    }
}