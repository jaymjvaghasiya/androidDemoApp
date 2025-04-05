package com.example.demoapplication;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuActivity extends AppCompatActivity {

    Button playBtn;
    Button leaderBoardBtn;
    TextView tvFirstname;
    TextView tvCredits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        playBtn = findViewById(R.id.btnMenuActPlay);
        leaderBoardBtn = findViewById(R.id.btnMenuActLeaderBoard);
        tvFirstname = findViewById(R.id.tvMenuActFirstname);
        tvCredits = findViewById(R.id.tvMenuActCredits);

        SharedPreferences sp = getSharedPreferences("dimond123", MODE_PRIVATE);
        String firstname = sp.getString("firstname", "User");
        Integer credits = sp.getInt("credits", 0);

        tvFirstname.setText(firstname);
        tvCredits.setText(credits);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
                rotateAnimator.setDuration(500); // Rotation duration in milliseconds
                rotateAnimator.start();

                new Handler().postDelayed(() -> {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }, 500);

            }
        });
    }
}