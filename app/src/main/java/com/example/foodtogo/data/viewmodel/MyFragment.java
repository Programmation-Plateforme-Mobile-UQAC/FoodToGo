package com.example.foodtogo.data.viewmodel;

import androidx.fragment.app.Fragment;

import com.example.foodtogo.data.service.Authenticated;

public class MyFragment extends Fragment {

    private Authenticated service;

    public void setService(Authenticated service){
        this.service = service;
    }

    public Authenticated getService(){
        return this.service;
    }

}