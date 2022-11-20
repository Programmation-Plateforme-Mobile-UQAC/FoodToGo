package com.example.foodtogo.ui.authenticated;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.foodtogo.R;


import androidx.appcompat.app.AppCompatActivity;

import com.example.foodtogo.data.service.Authenticated;

public class RegisterActivity extends AppCompatActivity
{
    EditText i_email,i_password,i_firstName,i_lastName;
    Button btn_register;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Authenticated service = new Authenticated();

        i_email = findViewById(R.id.email);
        i_password = findViewById(R.id.password);
        i_firstName = findViewById(R.id.firstName);
        i_lastName = findViewById(R.id.lastName);
        btn_register = findViewById(R.id.btn_register);


        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email = i_email.getText().toString();
                String password = i_password.getText().toString();
                String firstName = i_firstName.getText().toString();
                String lastName = i_lastName.getText().toString();

                boolean checkIsExist = service.isExist(email);
                if(checkIsExist){
                    Toast.makeText(getApplicationContext(),"User exist",Toast.LENGTH_SHORT).show();
                }else{
                    boolean registered = service.register(firstName,lastName,email,password);
                    if(registered){
                        Toast.makeText(getApplicationContext(),"Register success",Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(getApplicationContext(), "Register Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
