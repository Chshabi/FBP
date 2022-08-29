package com.example.firebase_practic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signIn extends AppCompatActivity {
    TextView txt_register;
    EditText ed_email, ed_password;
    FirebaseAuth mAuth;
    Button btn_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(signIn.this, R.color.yellow));

        txt_register = findViewById(R.id.txt_regsiter);
        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signIn.this,sign_up.class));
                finish();
            }
        });

        ed_email = findViewById(R.id.ed_email);
        ed_password = findViewById(R.id.ed_password);
        btn_login = findViewById(R.id.btn_login);

        mAuth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = ed_email.getText().toString().trim();
                String password = ed_password.getText().toString().trim();
                if (email.isEmpty()) {
                    ed_email.setError("Email is empty");
                    ed_email.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    ed_email.setError("Enter the valid email");
                    ed_email.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    ed_password.setError("Password is empty");
                    ed_password.requestFocus();
                    return;
                }
                if (password.length() < 8) {
                    ed_password.setError("Length of password is more than 8");
                    ed_password.requestFocus();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(signIn.this,home.class));
                            finish();
                            Toast.makeText(signIn.this, "You are successfully Registered", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(signIn.this, "You are not Registered! Try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}