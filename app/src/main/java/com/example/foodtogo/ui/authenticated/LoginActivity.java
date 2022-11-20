package com.example.foodtogo.ui.authenticated;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodtogo.R;
import com.example.foodtogo.data.service.Authenticated;
public class LoginActivity extends AppCompatActivity {

    public Authenticated service;
    EditText i_email;
    EditText i_password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.service = new Authenticated();
    }

    public void Login(View view){
        i_email = findViewById(R.id.email);
        i_password = findViewById(R.id.password);
        String email = i_email.getText().toString();
        String password = i_password.getText().toString();
        boolean authenticated = this.service.login(email,password);
        if(authenticated){
            this.showLoginSuccess();
        }else{
            this.showLoginFailed();
        }
    }

    private void showLoginSuccess() {
        String welcome = getString(R.string.welcome) + this.service.user_authenticated.firstName;
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed() {
        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
    }
}