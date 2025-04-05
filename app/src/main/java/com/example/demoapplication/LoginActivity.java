package com.example.demoapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button submit;
    TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        email = findViewById(R.id.edtLoginEmail);
        password = findViewById(R.id.edtLoginPassword);
        submit = findViewById(R.id.btnLoginSubmit);
        signup = findViewById(R.id.tvLoginSignupLink);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_email = email.getText().toString();
                String user_pass = password.getText().toString();
                Boolean isError = false;
                if(user_email.isBlank()) {
                    isError = true;
                    email.setError("Please Enter valide Email");
                }
                if(user_pass.isBlank()) {
                    isError = true;
                    password.setError("Please Enter Password");
                }

                if(isError) {
                    Toast.makeText(getApplicationContext(), "Please fill required fields", Toast.LENGTH_LONG).show();
                } else {
                    ExecutorService ex = Executors.newSingleThreadExecutor();
                    Future<Integer> ft = ex.submit(new Callable<Integer>() {
                        @Override
                        public Integer call() throws Exception {
                            return authenticate(user_email, user_pass);
                        }
                    });

                    try {
                        Integer status = ft.get();
                        Log.i("API", " : " + status);
                        if(status == 200) {
                            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalide Credential", Toast.LENGTH_LONG).show();
                        }
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    public Integer authenticate(String email, String password) {

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("email", email);
        data.put("password", password);
        Gson gson = new Gson();
        String dataStr = gson.toJson(data);

        try {
            URL url = new URL("http://diamondgame.onrender.com/api/auth/login");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            os.write(dataStr.getBytes());

            InputStream isr = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(isr));

            StringBuffer sbr = new StringBuffer();
            String str;
            while ((str = br.readLine()) != null) {
                sbr.append(str);
            }

            Log.i("api", sbr+"");
            JsonObject jo = gson.fromJson(sbr.toString(), JsonObject.class);
            String token = jo.get("token").toString();
            Log.i("api", "Token -> " + token);

            JsonObject jo2 = gson.fromJson(jo.get("user").toString(), JsonObject.class);
            String _id = jo2.get("_id").toString();
            String firstName = jo2.get("firstName").toString();
            Integer credit = jo2.get("credit").getAsInt();

            SharedPreferences sp = getSharedPreferences("dimond123", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("firstname", firstName);
            editor.putString("token", token);
            editor.putInt("credits", credit);
            editor.apply();

            Integer rscode = connection.getResponseCode();
            Log.i("api", rscode+"");

            return connection.getResponseCode();

        } catch(Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
}
