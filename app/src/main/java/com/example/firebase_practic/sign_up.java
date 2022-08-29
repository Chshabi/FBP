package com.example.firebase_practic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class sign_up extends AppCompatActivity {

    TextInputEditText ed_email, ed_password, ed_username;
    Button btn_register;
    TextView txt_signin;
    ProgressBar progressbar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(sign_up.this, R.color.yellow));

        ed_email = findViewById(R.id.ed_email);
        txt_signin = findViewById(R.id.txt_sign_in);
        ed_password = findViewById(R.id.ed_password);
        ed_username = findViewById(R.id.ed_username);
        btn_register = findViewById(R.id.btn_register);

        mAuth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = ed_email.getText().toString().trim();
                String password = ed_password.getText().toString().trim();

                if (email.isEmpty()) {
                    ed_email.setError("Email is empty!");
                    ed_email.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    ed_email.setError("Enter the valid email address");
                    ed_email.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    ed_password.setError("Enter the password");
                    ed_password.requestFocus();
                    return;
                }
                if (password.length() < 8) {
                    ed_password.setError("Length of the password should be more than 6");
                    ed_password.requestFocus();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Intent i = new Intent(sign_up.this,signIn.class);
                            startActivity(i);
                            Toast.makeText(sign_up.this,"You are successfully Registered", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(sign_up.this,"You are not Registered! Try again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        txt_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(sign_up.this,signIn.class);
                startActivity(i);
            }
        });

    }
}