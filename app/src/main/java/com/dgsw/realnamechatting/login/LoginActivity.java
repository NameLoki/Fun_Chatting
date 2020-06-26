package com.dgsw.realnamechatting.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.dgsw.realnamechatting.R;
import com.dgsw.realnamechatting.chat.ChatActivity;
import com.dgsw.realnamechatting.databinding.ActivityLoginBinding;
import com.dgsw.realnamechatting.firbase.FirebaseManager;
import com.dgsw.realnamechatting.main.MainActivity;
import com.dgsw.realnamechatting.singup.SingupStartActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseManager firebaseManager = FirebaseManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        autoLogin();

        init();
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
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
        finish();
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