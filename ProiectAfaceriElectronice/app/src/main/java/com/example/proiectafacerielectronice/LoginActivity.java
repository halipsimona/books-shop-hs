package com.example.proiectafacerielectronice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.proiectafacerielectronice.mysql.RestApi;
import com.example.proiectafacerielectronice.mysql.UserService;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText tietMail;
    private TextInputEditText tietPassword;
    private Button btnNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeComponents();
        btnNext.setOnClickListener(login());
    }

    private View.OnClickListener login() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInUser();
            }
        };
    }

    private void initializeComponents() {
        tietMail=findViewById(R.id.login_tiet_email);
        tietPassword=findViewById(R.id.login_tiet_password);
        btnNext=findViewById(R.id.login_btn_next);
    }

    private void logInUser(){
        UserService userService= RestApi.getRetrofitInstance().create(UserService.class);
        userService.getUserByEmail(tietMail.getText().toString().trim()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = (User)(Parcelable)response.body();
                assert user != null;
                if(user.getPassword().equals(tietPassword.getText().toString().trim())) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra(MainActivity.MAIN_ACTIVITY_USER_KEY, (Parcelable) response.body());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),R.string.auth_failed,
                            Toast.LENGTH_LONG).show();
                    tietPassword.setText("");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}