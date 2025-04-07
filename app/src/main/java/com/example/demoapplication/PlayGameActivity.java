package com.example.demoapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashSet;

public class PlayGameActivity extends AppCompatActivity {

    TextView unm;
    TextView winAmt;
    TextView batAmt;

    HashSet<Integer> bomArray;
    ImageButton imageButton[] = new ImageButton[15];

    Integer batAmount;
    Integer winningAmount;


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

        unm = findViewById(R.id.tvPlayGameActUsername);
        winAmt = findViewById(R.id.tvPlayGameActWinningAmt);
        batAmt = findViewById(R.id.tvPlayGameActBatAmt);

        batAmount = 500;
        winningAmount = 0;

        SharedPreferences sp = getSharedPreferences("dimond123", MODE_PRIVATE);
        String firstName = sp.getString("firstName","USER");

        unm.setText(firstName);
        winAmt.setText(winningAmount);
        batAmt.setText(batAmount);

        imageButton[0] = findViewById(R.id.imgBtn0);
        imageButton[1] = findViewById(R.id.imgBtn1);
        imageButton[2] = findViewById(R.id.imgBtn2);
        imageButton[3] = findViewById(R.id.imgBtn3);
        imageButton[4] = findViewById(R.id.imgBtn4);
        imageButton[5] = findViewById(R.id.imgBtn5);
        imageButton[6] = findViewById(R.id.imgBtn6);
        imageButton[7] = findViewById(R.id.imgBtn7);
        imageButton[8] = findViewById(R.id.imgBtn8);
        imageButton[9] = findViewById(R.id.imgBtn9);
        imageButton[10] = findViewById(R.id.imgBtn10);
        imageButton[11] = findViewById(R.id.imgBtn11);
        imageButton[12] = findViewById(R.id.imgBtn12);
        imageButton[13] = findViewById(R.id.imgBtn13);
        imageButton[14] = findViewById(R.id.imgBtn14);
        imageButton[15] = findViewById(R.id.imgBtn15);

        bomArray = new HashSet<>();

        while(bomArray.size() != 4) {
            Integer r = (int) (Math.random()*15);
            bomArray.add(r);
        }

        for(int i = 0; i < imageButton.length; i++) {
            int index = i;
            imageButton[i].setOnClickListener(v -> gamePlay(index));
        }
    }

    private void gamePlay(int index) {
        Log.i("GamePlay",index+"");

        Log.i("GamePlay",imageButton[index].getBackground().toString());

        if(bomArray.contains(index)) {
            imageButton[index].setBackground(getDrawable(R.drawable.boom));
        } else {
            if(imageButton[index].getBackground().toString().contains("RippleDrawable")) {
                imageButton[index].setBackground(getDrawable(R.drawable.diamond));
                winningAmount += batAmount;
                winAmt.setText(winningAmount);
            }
        }
    }
}