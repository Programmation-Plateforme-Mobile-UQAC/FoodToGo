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

import com.example.foodtogo.R;
import com.example.foodtogo.data.service.Authenticated;
import com.example.foodtogo.data.viewmodel.MyFragment;
import com.example.foodtogo.databinding.ActivityLoginBinding;
import com.example.foodtogo.databinding.ActivityRegisterBinding;
import com.example.foodtogo.utils.Util;

import java.util.Timer;
import java.util.TimerTask;

public class RegisterFragment extends MyFragment {

    ActivityRegisterBinding binding;
    LoginFragment fragNextStep;
    int redirectionPageId;

    public RegisterFragment(){
        this.redirectionPageId = -1;
    }

    public RegisterFragment(int redirectionPageId){
        this.redirectionPageId = redirectionPageId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding = ActivityRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.fragNextStep = new LoginFragment(redirectionPageId);
        binding.btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                fragNextStep.setService(getService());
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, fragNextStep).commit();
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            /*private Timer mTimer = new Timer();
            public TimerTask mTask = new TimerTask() {
                @Override
                public void run() {
                    fragNextStep.setService(getService());
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, fragNextStep).commit();
                }
            };*/

            @Override
            public void onClick(View view) {
                String email = binding.email.getText().toString();
                String password = binding.password.getText().toString();
                String firstName = binding.firstName.getText().toString();
                String lastName = binding.lastName.getText().toString();

                boolean checkIsExist = getService().isExist(email);
                if(checkIsExist){
                    Toast.makeText(getContext(),"Veuillez vous connecter",Toast.LENGTH_SHORT).show();
                }else{
                    boolean registered = getService().register(firstName,lastName,email,password);
                    if(registered){
                        fragNextStep.setService(getService());
                        Util.navigateTo(view, fragNextStep);
                        Toast.makeText(getContext(),"Inscription réussi !",Toast.LENGTH_SHORT).show();
                        //mTimer.scheduleAtFixedRate(mTask, 1000, 1000);

                    }else{
                        Toast.makeText(getContext(), "Enregistrement à était un échec, veuillez ressayer ultérieurement", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }


}
