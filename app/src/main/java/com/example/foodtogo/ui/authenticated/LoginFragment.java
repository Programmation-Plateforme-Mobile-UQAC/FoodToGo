package com.example.foodtogo.ui.authenticated;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodtogo.R;
import com.example.foodtogo.data.model.User;
import com.example.foodtogo.data.service.Authenticated;
import com.example.foodtogo.data.viewmodel.MyFragment;
import com.example.foodtogo.data.viewmodel.UserViewModel;
import com.example.foodtogo.databinding.ActivityLoginBinding;
import com.example.foodtogo.ui.HomeFragment;

public class LoginFragment extends MyFragment {

    ActivityLoginBinding binding;
    HomeFragment fragNextStepHome;
    RegisterFragment fragNextStepRegister;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding = ActivityLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragNextStepHome = new HomeFragment();
        fragNextStepRegister = new RegisterFragment();
        Authenticated auth = getService();

        if(getService().user_authenticated != null){
            fragNextStepHome.setService(getService());
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, fragNextStepHome).commit();
        }
        binding.confirmButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email = binding.email.getText().toString();
                String password = binding.password.getText().toString();

                try {
                    User authenticated = getService().login(email,password);
                    getService().user_authenticated = authenticated;
                    String welcome = getString(R.string.welcome) + getService().user_authenticated.firstName;
                    Toast.makeText(getContext(), welcome, Toast.LENGTH_LONG).show();
                    fragNextStepHome.setService(getService());
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout,fragNextStepHome).commit();

                }catch (Exception e){
                    Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                fragNextStepRegister.setService(getService());
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout,fragNextStepRegister).commit();
            }
        });

    }


}
