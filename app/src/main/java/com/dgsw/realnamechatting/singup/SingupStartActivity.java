package com.dgsw.realnamechatting.singup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.dgsw.realnamechatting.R;
import com.dgsw.realnamechatting.databinding.ActivitySingupStartBinding;

public class SingupStartActivity extends AppCompatActivity {

    ActivitySingupStartBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySingupStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonSingup.setOnClickListener(v -> {
            singupInit();
        });

    }

    private void singupInit() {
        Intent intent = new Intent(this, SingupActivity.class);
        startActivity(intent);
        finish();
    }
}