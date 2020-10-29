package com.example.to_do;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private TextView registerEamil, registerPass;
    private Button registerBtn;
    private TextView goTo;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        registerEamil = findViewById(R.id.registerEmail);
        registerPass = findViewById(R.id.registerPassword);

        registerBtn = findViewById(R.id.register);

        goTo = findViewById(R.id.goToLogin);

        goTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = registerEamil.getText().toString().trim();
                String password = registerPass.getText().toString().trim();
                // validation
                if (TextUtils.isEmpty(email)){

                    registerEamil.setError("Email is required");
                    return;

                }
                if (TextUtils.isEmpty(password)){

                    registerEamil.setError("Password is required");

                } else {

                    // register
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                String error = task.getException().toString();
                                Toast.makeText(RegistrationActivity.this,"Sign up failed, "+error,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });
    }
}