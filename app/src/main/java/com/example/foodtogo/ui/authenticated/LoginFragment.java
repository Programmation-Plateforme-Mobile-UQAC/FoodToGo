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
import com.example.foodtogo.data.model.Favorite;
import com.example.foodtogo.data.model.User;
import com.example.foodtogo.data.service.Authenticated;
import com.example.foodtogo.data.viewmodel.MyFragment;
import com.example.foodtogo.databinding.ActivityLoginBinding;
import com.example.foodtogo.databinding.ActivityMainBinding;
import com.example.foodtogo.ui.AddFragment;
import com.example.foodtogo.ui.ChatFragment;
import com.example.foodtogo.ui.FavoriteFragment;
import com.example.foodtogo.ui.HomeFragment;
import com.example.foodtogo.ui.ProfilFragment;
import com.example.foodtogo.utils.Util;

public class LoginFragment extends MyFragment {

    ActivityLoginBinding binding;
    ActivityMainBinding mainBinding;
    HomeFragment fragNextStepHome;
    RegisterFragment fragNextStepRegister;
    int redirectionPageId;

    public LoginFragment(){
        redirectionPageId = -1;
    }

    public LoginFragment(int redirectionPageId){
        this.redirectionPageId = redirectionPageId;
    }

    public LoginFragment(int redirectionPageId, ActivityMainBinding mainBinding){
        this.redirectionPageId = redirectionPageId;
        this.mainBinding = mainBinding;
    }


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
        fragNextStepRegister = new RegisterFragment(redirectionPageId);

        if(getService().user_authenticated != null){
            fragNextStepHome.setService(getService());
            if (redirectionPageId == -1)
                Util.navigateTo(view, fragNextStepHome);
            else{
                MyFragment fragment = Util.createFragmentFromId(this.redirectionPageId, getService());
                Util.navigateTo(view, fragment);
                /*if (this.redirectionPageId == R.id.add){
                    AddFragment fragment = new AddFragment(mainBinding);
                    fragment.setService(getService());
                    Util.navigateTo(view, fragment);
                } else if (this.redirectionPageId == R.id.favorite){
                    FavoriteFragment fragment = new FavoriteFragment();
                    fragment.setService(getService());
                    Util.navigateTo(view, fragment);
                }else if (this.redirectionPageId == R.id.profile){
                    ProfilFragment fragment = new ProfilFragment();
                    fragment.setService(getService());
                    Util.navigateTo(view, fragment);
                } else if (this.redirectionPageId == R.id.message){
                    ChatFragment fragment = new ChatFragment();
                    fragment.setService(getService());
                    Util.navigateTo(view, fragment);
                }*/
            }

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
                    if (redirectionPageId == -1)
                        Util.navigateTo(view, fragNextStepHome);
                    else{
                        MyFragment fragment = Util.createFragmentFromId(redirectionPageId, getService());
                        Util.navigateTo(view, fragment);
                        /*if (redirectionPageId == R.id.add){
                            AddFragment fragment = new AddFragment(mainBinding);
                            fragment.setService(getService());
                            Util.navigateTo(view, fragment);
                        } else if (redirectionPageId == R.id.favorite){
                            FavoriteFragment fragment = new FavoriteFragment();
                            fragment.setService(getService());
                            Util.navigateTo(view, fragment);
                        }else if (redirectionPageId == R.id.profile){
                            ProfilFragment fragment = new ProfilFragment();
                            fragment.setService(getService());
                            Util.navigateTo(view, fragment);
                        } else if (redirectionPageId == R.id.message){
                            ChatFragment fragment = new ChatFragment();
                            fragment.setService(getService());
                            Util.navigateTo(view, fragment);
                        }*/
                    }

                }catch (Exception e){
                    Toast.makeText(getContext(), "Authentification échoue", Toast.LENGTH_SHORT).show();
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
