package com.example.proiectafacerielectronice;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proiectafacerielectronice.mysql.RestApi;
import com.example.proiectafacerielectronice.mysql.UserService;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalTime;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAccountActivity extends AppCompatActivity {

    private TextInputEditText tietName;
    private TextInputEditText tietEmail;
    private TextInputEditText tietPassword;
    private TextInputEditText tietAddress;
    private Button btnNext;
    private Button btnLogin;
    private EditText etError;
    private UserService myApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        myApi = RestApi.getRetrofitInstance().create(UserService.class);
        initializeComponents();
        btnNext.setOnClickListener(nextStep());
        btnLogin.setOnClickListener(goToLogin());
    }

    private View.OnClickListener nextStep() {
        return new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    User user = generateUserFromWidgets();
                    //insert user in database
                    registerUser(user);
                }
            }
        };
    }

    private View.OnClickListener goToLogin() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

            }
        };
    }

    private User generateUserFromWidgets() {
        String name, email, pass, address;
        name = tietName.getText().toString().trim();
        email = tietEmail.getText().toString().trim();
        pass = tietPassword.getText().toString();
        address = tietAddress.getText().toString();
        return new User(name, email, pass, address);
    }

    private void initializeComponents() {
        tietName = findViewById(R.id.signup_tiet_name);
        tietEmail = findViewById(R.id.signup_tiet_email);
        tietPassword = findViewById(R.id.signup_tiet_password);
        tietAddress = findViewById(R.id.signup_tiet_address);
        btnNext = findViewById(R.id.signup_btn_next);
        btnLogin = findViewById(R.id.signup_btn_login);
        etError = findViewById(R.id.signup_tv_error);
    }

    private boolean emailIsValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String pass) {
        if (!pass.equals(pass.toLowerCase()) && !pass.equals(pass.toUpperCase()) && pass.matches(".*\\d+.*")) {
            return true;
        }
        return false;
    }

    private boolean validateData() {
        if (tietName.getText() == null || tietName.getText().toString().trim().isEmpty()) {
            etError.setTextColor(getResources().getColor(R.color.error));
            etError.setText(R.string.name_err);
            return false;
        }
        if (tietEmail.getText() == null || tietEmail.getText().toString().trim().isEmpty() || emailIsValid(tietEmail.getText().toString().trim()) == false) {
            etError.setTextColor(getResources().getColor(R.color.error));
            etError.setText(R.string.email_err);
            return false;
        }
        if (tietPassword.getText() == null || tietPassword.getText().toString().trim().isEmpty() || isPasswordValid(tietPassword.getText().toString()) == false) {
            etError.setTextColor(getResources().getColor(R.color.error));
            etError.setText(R.string.pass_err);
            return false;
        }
        if (tietAddress.getText() == null || tietAddress.getText().toString().trim().isEmpty()) {
            etError.setTextColor(getResources().getColor(R.color.error));
            etError.setText(R.string.address_err);
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void registerUser(final User user) {
        Toast.makeText(getApplicationContext(), getString(R.string.reg_user), Toast.LENGTH_LONG).show();

        User detailedUser = new User(LocalTime.now().toString(), user.getName(), user.getEmail(), user.getPassword(), user.getAddress());

        Log.i("user id", detailedUser.getId());
        myApi.insertUser(detailedUser).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String message = response.message();
                int status = response.code();
                Log.i("mysql", "status : " + status);
                Toast.makeText(CreateAccountActivity.this, "Created User Successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(MainActivity.MAIN_ACTIVITY_USER_KEY, user);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
        //login-ul
    }
}