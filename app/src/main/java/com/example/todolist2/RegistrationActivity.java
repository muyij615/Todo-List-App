package com.example.todolist2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private EditText RegEmail, RegPwd;
    private Button RegBtn;
    private TextView RegQn;
    private FirebaseAuth mAuth;

    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);

        toolbar = findViewById(R.id.RegistrationToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        mAuth = FirebaseAuth.getInstance();
        loader = new ProgressDialog(this);

        RegEmail = findViewById(R.id.RegistrationEmail);
        RegPwd = findViewById(R.id.RegistrationPassword);
        RegBtn = findViewById(R.id.RegistrationButton);
        RegQn = findViewById(R.id.RegistrationPageQuestion);


        RegQn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = RegEmail.getText().toString().trim();
                String password = RegPwd.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    RegEmail.setError("A valid email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    RegPwd.setError("A valid password is required");
                    return;
                } else{
                    loader.setMessage("Registration in progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            } else{
                                String error = task.getException().toString();
                                Toast.makeText(RegistrationActivity.this, "Registration failed" + error, Toast.LENGTH_SHORT).show();
                            }
                            loader.dismiss();
                        }
                    });
                }

            }
        });
    }
}