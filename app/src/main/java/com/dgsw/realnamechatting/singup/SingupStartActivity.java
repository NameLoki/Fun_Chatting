package com.dgsw.realnamechatting.singup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.dgsw.realnamechatting.R;
import com.dgsw.realnamechatting.databinding.ActivitySingupStartBinding;
import com.dgsw.realnamechatting.manager.ActivityLoadManager;

public class SingupStartActivity extends AppCompatActivity {

    private ActivitySingupStartBinding binding;
    private ActivityLoadManager activityLoadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySingupStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activityLoadManager = ActivityLoadManager.getInstance();

        binding.buttonSingup.setOnClickListener(v -> {
            singupInit();
        });

    }

    private void singupInit() {
        activityLoadManager.loadSingup(this);
    }
}