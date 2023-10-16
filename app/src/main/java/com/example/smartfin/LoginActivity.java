package com.example.smartfin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {

    EditText Email, Password;
    TextView signup, forgot_pass;
    TextInputLayout PasswordLayout;
    Button LoginButton;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        LoginButton = findViewById(R.id.LoginButton);
        forgot_pass = findViewById(R.id.forgot_pass);
        PasswordLayout = findViewById(R.id.PasswordLayout);

        signup = findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();

                Email.setError(null);
                Password.setError(null);


                boolean hasError = false;

                if (email.isEmpty()) {
                    Email.setError("Email is required");
                    hasError = true;
                }else if (!isValidEmail(email)) {
                    Email.setError("Invalid email address");
                    hasError = true;
                }

                if (password.isEmpty()) {
                    Password.setError("Password is required");
                    PasswordLayout.setEndIconVisible(false);
                    hasError = true;
                }else if (password.length() < 6) {
                    Password.setError("Password must be at least 6 characters");
                    PasswordLayout.setEndIconVisible(false);
                    hasError = true;
                }else{
                    PasswordLayout.setEndIconVisible(true);
                }

                if (hasError) {
                    Toast.makeText(LoginActivity.this, "Please enter valid user information.", Toast.LENGTH_SHORT).show();
                }else {
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, task -> {
                                if (task.isSuccessful()) {

                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    updateLoggedInStatus(true);

                                    startActivity(intent);
                                    finish();
                                } else {

                                    Toast.makeText(LoginActivity.this, "Authentication failed. Check your email and password.", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = Email.getText().toString().trim();

                boolean hasError = false;

                if (email.isEmpty()) {
                    Email.setError("Email is required");
                    hasError = true;
                }else {
                    usersRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {

                                firebaseAuth.sendPasswordResetEmail(email)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(LoginActivity.this, "Email Sent", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                Toast.makeText(LoginActivity.this, "Email not found. Please sign up.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }
        });
    }
    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(regex) && (email.endsWith(".com") || email.endsWith(".in"));
    }

    private void updateLoggedInStatus(boolean loggedIn) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("hasLoggedIn", loggedIn);
        editor.apply();
    }

}