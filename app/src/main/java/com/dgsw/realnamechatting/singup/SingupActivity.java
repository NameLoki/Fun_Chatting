package com.dgsw.realnamechatting.singup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dgsw.realnamechatting.R;
import com.dgsw.realnamechatting.data.User;
import com.dgsw.realnamechatting.databinding.ActivitySingupBinding;
import com.dgsw.realnamechatting.login.LoginActivity;
import com.dgsw.realnamechatting.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.AccessToken;
import com.siot.IamportRestClient.response.IamportResponse;

import java.io.IOException;
import java.util.regex.Pattern;

public class SingupActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{8,22}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[가-힣]{2,4}$");

    private EditText email;
    private EditText password;
    private EditText rePassword;
    private EditText name;
    private EditText phone;
    private Button singup;

    ActivitySingupBinding binding;
    IamportClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySingupBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

//        client = new IamportClient("5978210787555892", "9e75ulp4f9Wwj0i8MSHlKFA9PCTcuMYE15Kvr9AHixeCxwKkpsFa7fkWSd9m0711dLxEV7leEAQc6Bxv");

//        getUserToken();

        init();

    }

    private void init() {
        email = binding.editTextEmail;
        password = binding.editTextPassword;
        rePassword = binding.editTextRePassword;
        name = binding.editTextName;
        phone = binding.editTextPhone;
        singup = binding.buttonSingup;

        phoneSetting();
        buttonSetting();
    }

    private void buttonSetting() {
        singup.setOnClickListener(v -> {
            User user = new User();
            user.setEmail(email.getText().toString());
            user.setProfileMessage("태스트");
            user.setPhone(phone.getText().toString());
            user.setName(name.getText().toString());
            user.setPassword(password.getText().toString());
            createUser(user);
        });
    }

    private void createUser(User user) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(SingupActivity.this, "가입 성공", Toast.LENGTH_SHORT).show();

                    firebaseAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(SingupActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();

                                DatabaseReference myRef = database.getReference("users/" + firebaseAuth.getCurrentUser().getUid());

                                user.setUid(firebaseAuth.getCurrentUser().getUid());

                                myRef.child("/info").push().setValue(user);

                                Intent intent = new Intent(SingupActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(SingupActivity.this, "알 수 없는 오류 입니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(SingupActivity.this, "가입 실패", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void phoneSetting() {
        phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }


    private void isEmail() {
        if(email.getText().toString().isEmpty()) {

        } else if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {

        }
    }

    private void isPassword() {
        if(password.getText().toString().isEmpty()) {

        } else if (!PASSWORD_PATTERN.matcher(password.getText().toString()).matches()) {

        }
    }

    private void isName() {
        if(name.getText().toString().isEmpty()) {

        } else if(!NAME_PATTERN.matcher(name.getText().toString()).matches()) {

        }
    }

    private void getUserToken() {
        try {
            IamportResponse<AccessToken> auth = client.getAuth();
//            binding.textViewRes.setText(auth.getResponse().toString());
//            binding.textViewToken.setText(auth.getResponse().getToken().toString());


        } catch (IamportResponseException e) {

            switch (e.getHttpStatusCode()) {
                case 401:
                    break;
                case 500:
                    break;
            }
        } catch (IOException e) {
            //서버 연결 실패
        }
    }
}