package com.example.foodtogo.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.foodtogo.R;
import com.example.foodtogo.data.model.Order;
import com.example.foodtogo.databinding.ActivityAddOrderBinding;
import com.example.foodtogo.databinding.ActivityMainBinding;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.util.UUID;

public class AddFragment extends Fragment {
    ActivityAddOrderBinding binding;
    ActivityMainBinding mainBinding;

    Order order = null;
    boolean cameraIsGranted = false;
    boolean externalStorageIsGranted = false;

    //Intent result Handler
    private final ActivityResultLauncher<Intent> intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK){
                    assert result.getData() != null;

                    int requestCode = result.getData().getExtras().getInt("REQUEST_CODE");

                    Log.w("ACTIVITY_LAUNCHER", result.getData().getAction());
                    Log.w("ACTIVITY_LAUNCHER", String.valueOf(requestCode));
                    //Log.w("ACTIVITY_LAUNCHER", result.getData().getExtras().getParcelable());

                    binding.imageButton1.setImageURI(result.getData().getData());
                }
    });

    //Permission response Handler
    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted){
            Log.w("REQUEST_PERMISSION", "ACCESS");
            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intentActivityResultLauncher.launch(camera);
        } else {
            Log.w("REQUEST_PERMISSION", "DENIED");
        }
    });


    public AddFragment(){

    }

    public AddFragment(ActivityMainBinding mainBinding){
        this.mainBinding = mainBinding;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ActivityAddOrderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.addPostButton.setOnClickListener(view1 -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
                    order = new Order(UUID.randomUUID(), UUID.randomUUID(),
                            binding.namePostEdit.getText().toString(),
                            binding.foodTypeEdit.getText().toString()
                            ,binding.productDescriptionEdit.getText().toString(),
                            "", binding.expirationDateEdit.getText().toString());
                    order.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            mainBinding.bottomNavigationView.setSelectedItemId(R.id.home);
        });

        binding.imageButton1.setOnClickListener(viewButton -> {
            requestPermission();
        });
    }

    private void requestPermission(){
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intentActivityResultLauncher.launch(camera);
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){

        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    private byte[] bitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}