package com.example.smartfin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GetStartedActivity extends AppCompatActivity {

    private Button loginButton, signupButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(hasUserLoggedInBefore()){

            Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainActivity);
            finish();

        }else{
            setContentView(R.layout.activity_getstarted);

            loginButton = findViewById(R.id.button_login);
            signupButton = findViewById(R.id.button_signup);

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(GetStartedActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });

            signupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(GetStartedActivity.this, SignUpActivity.class);
                    startActivity(intent);
                }
            });
        }


    }

    private boolean hasUserLoggedInBefore() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        // Check if the "hasLoggedIn" flag is set to true
        return pref.getBoolean("hasLoggedIn", false);
    }


}