package com.example.foodtogo.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodtogo.R;
import com.example.foodtogo.data.service.Authenticated;
import com.example.foodtogo.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    public Authenticated service;
    EditText i_username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.service = new Authenticated();
    }

    public void Login(View view){
        i_username = findViewById(R.id.username);
        String username = i_username.getText().toString();
        boolean authenticated = this.service.login(username);
        if(authenticated){
            this.updateUiWithUser();
            Toast.makeText(getApplicationContext(),"Vous etes Authentifié mr/mme :"+ this.service.user_authenticated.username,Toast.LENGTH_SHORT).show();
        }else{
            this.showLoginFailed();
        }

    }

    public void Register(View view){
        i_username = findViewById(R.id.username);
        String username = i_username.getText().toString();
        boolean checkIsExist = this.service.isExist(username);
        if(checkIsExist){
            Toast.makeText(getApplicationContext(),"User exist",Toast.LENGTH_SHORT).show();
        }else{
            boolean registered = this.service.register(username);
            if(registered){
                Toast.makeText(getApplicationContext(),"Register success",Toast.LENGTH_SHORT).show();
            }else{
                this.showRegisterFailed();
            }
        }

    }

    private void updateUiWithUser() {
        String welcome = getString(R.string.welcome) + this.service.user_authenticated.getUsername();
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showRegisterFailed() {
        Toast.makeText(getApplicationContext(), "Register Failed", Toast.LENGTH_SHORT).show();
    }
    private void showLoginFailed() {
        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
    }
}