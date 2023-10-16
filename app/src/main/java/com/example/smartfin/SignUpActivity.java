package com.example.smartfin;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText Name, Email, Password, ConfirmPassword;
    TextView login;
    TextInputLayout PasswordLayout, ConfirmPasswordLayout;
    Button SignupButton;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference usersReference;

    ImageView Google;
    SignInClient oneTapClient;
    BeginSignInRequest signUpRequest;
    private static final int REQ_ONE_TAP = 2;
    private boolean showOneTapUI = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Name = findViewById(R.id.Name);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        ConfirmPassword = findViewById(R.id.ConfirmPassword);
        SignupButton = findViewById(R.id.SignupButton);
        PasswordLayout = findViewById(R.id.PasswordLayout);
        ConfirmPasswordLayout = findViewById(R.id.ConfirmPasswordLayout);

        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        Google = findViewById(R.id.google_logo);
        oneTapClient = Identity.getSignInClient(this);
        signUpRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.web_client_id))
                        // Show all accounts on the device.
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .build();

        ActivityResultLauncher<IntentSenderRequest> activityResultLauncher =
                registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            try {
                                SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(result.getData());
                                String idToken = credential.getGoogleIdToken();
                                if (idToken !=  null) {
                                    String email = credential.getId();
                                    Toast.makeText(SignUpActivity.this, "Registration successful with " + email, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            } catch (ApiException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
        Google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oneTapClient.beginSignIn(signUpRequest)
                        .addOnSuccessListener(SignUpActivity.this, new OnSuccessListener<BeginSignInResult>() {
                            @Override
                            public void onSuccess(BeginSignInResult result) {
                                IntentSenderRequest intentSenderRequest =
                                        new IntentSenderRequest.Builder(result.getPendingIntent().getIntentSender()).build();
                                activityResultLauncher.launch(intentSenderRequest);

                            }
                        })
                        .addOnFailureListener(SignUpActivity.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // No Google Accounts found. Just continue presenting the signed-out UI.
                                Log.d("TAG", e.getLocalizedMessage());
                            }
                        });
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        usersReference = database.getReference("users");

        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = Name.getText().toString().trim();
                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();
                String confirmPassword = ConfirmPassword.getText().toString().trim();

                Name.setError(null);
                Email.setError(null);
                Password.setError(null);
                ConfirmPassword.setError(null);

                boolean hasError = false;

                if (name.isEmpty()) {
                    Name.setError("Name is required");
                    hasError = true;
                }

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
                }else {
                    PasswordLayout.setEndIconVisible(true);
                }

                if (confirmPassword.isEmpty()) {
                    ConfirmPassword.setError("Confirm Password is required");
                    ConfirmPasswordLayout.setEndIconVisible(false);
                    hasError = true;
                }else if (confirmPassword.length() < 6) {
                    ConfirmPassword.setError("Password must be at least 6 characters");
                    ConfirmPasswordLayout.setEndIconVisible(false);
                    hasError = true;
                }else {
                    ConfirmPasswordLayout.setEndIconVisible(true);
                }

                if (!password.equals(confirmPassword)) {
                    ConfirmPassword.setError("Passwords do not match");
                    hasError = true;
                }

                if (hasError) {
                    Toast.makeText(SignUpActivity.this, "Please enter valid user information.", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        FirebaseUser user = firebaseAuth.getCurrentUser();

                                        String userId = user.getUid();
                                        HelperClass helperClass = new HelperClass(name, email);
                                        usersReference.child(userId).setValue(helperClass);

                                        Toast.makeText(SignUpActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                        updateLoggedInStatus(true);

                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
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
