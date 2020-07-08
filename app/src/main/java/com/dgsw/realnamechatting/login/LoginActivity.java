package com.dgsw.realnamechatting.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.dgsw.realnamechatting.databinding.ActivityLoginBinding;
import com.dgsw.realnamechatting.manager.ActivityLoadManager;
import com.dgsw.realnamechatting.manager.FirebaseManager;
import com.dgsw.realnamechatting.main.MainActivity;
import com.dgsw.realnamechatting.singup.SingupStartActivity;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseManager firebaseManager;

    private ActivityLoadManager activityLoadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        setting();

        autoLogin();

        init();
    }

    private void setting() {
        activityLoadManager = ActivityLoadManager.getInstance();
        firebaseManager = FirebaseManager.getInstance();
    }


    private void autoLogin() {
        if(loginCheck()) {
            loadMainActivity();
        }
    }

    private boolean loginCheck() {
        FirebaseUser firebaseUser = firebaseManager.getLoginUser();

        if(firebaseUser != null) {
            return true;
        }
        return false;
    }

    private void loadMainActivity() {
        activityLoadManager.loadMainActivity(this);
    }

    private void init() {

        binding.buttonLogin.setOnClickListener(v -> {
            login();
        });

        binding.buttonSingup.setOnClickListener(v -> {
            Intent intent = new Intent(this, SingupStartActivity.class);
            startActivity(intent);
        });
    }

    private void login() {
        String email = binding.editTextEmail.getText().toString().trim();
        String password = binding.editTextPassword.getText().toString().trim();

        if(email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "공백이 있습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean result = firebaseManager.login(email, password, LoginActivity.this);

        if(result) {
            loadMainActivity();
        } else {
            Toast.makeText(LoginActivity.this, "없는 계정입니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
        }

//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//
//
//
//
//
//        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()) {
//                    loadMainActivity();
//                } else {
//
//                }
//            }
//        });
    }
}