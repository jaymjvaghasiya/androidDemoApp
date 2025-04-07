package com.example.demoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SignupActivity extends AppCompatActivity {

    EditText firstname;
    EditText email;
    EditText password;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firstname = findViewById(R.id.edtSignupFnm);
        email = findViewById(R.id.edtSignupEmail);
        password = findViewById(R.id.edtSignupPassword);
        signup = findViewById(R.id.btnSignupSubmit);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String unm = firstname.getText().toString();
                String uemail = email.getText().toString();
                String upass = password.getText().toString();
                Boolean isError = false;
                if(unm.isBlank()) {
                    isError = true;
                    firstname.setError("Please Enter firstname.");
                }
                if(uemail.isBlank()) {
                    isError = true;
                    email.setError("Please Enter Email.");
                }
                if(upass.isBlank()) {
                    isError = true;
                    password.setError("Please Enter password.");
                }

                if(isError) {
                    Toast.makeText(getApplicationContext(), "Please Fill required fields.", Toast.LENGTH_LONG).show();
                } else {
                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    Future<Integer> ftStatus = executorService.submit(new Callable<Integer>() {
                        @Override
                        public Integer call() throws Exception {
                           return userSignup(unm, uemail, upass);
                        }
                    });

                    try {
                        Integer status = ftStatus.get();
                        Log.i("API", " : " + status);
                        if(status == 201) {
                            Log.i("API","success");

                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {

                        }
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public Integer userSignup(String firstname, String email, String password) {
        Map<String, Object> data = new HashMap<>();
        data.put("firstname", firstname);
        data.put("lastname", "NA");
        data.put("email", email);
        data.put("password", password);

        Gson gson = new Gson();
        String dataStr = gson.toJson(data);
        try {
            URL url = new URL("http://diamondgame.onrender.com/api/auth/signup");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            os.write(dataStr.getBytes());

            return connection.getResponseCode();

        } catch(Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}