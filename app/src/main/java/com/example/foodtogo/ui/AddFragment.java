package com.example.foodtogo.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.foodtogo.R;
import com.example.foodtogo.data.model.Category;
import com.example.foodtogo.data.model.Product;
import com.example.foodtogo.data.model.User;
import com.example.foodtogo.data.service.Authenticated;
import com.example.foodtogo.data.viewmodel.MyFragment;
import com.example.foodtogo.databinding.ActivityAddOrderBinding;
import com.example.foodtogo.databinding.ActivityLoginBinding;
import com.example.foodtogo.databinding.ActivityMainBinding;
import com.example.foodtogo.ui.authenticated.LoginFragment;
import com.example.foodtogo.utils.Util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class AddFragment extends MyFragment {
    ActivityAddOrderBinding binding;
    ActivityMainBinding mainBinding;
    ActivityLoginBinding loginBinding;
    PopupMenu popupMenu;
    List<Category> categories;
    Category chosenCategory;

    Product order = null;
    boolean cameraIsGranted = false;
    boolean externalStorageIsGranted = false;
    //Intent result Handler
    private final ActivityResultLauncher<Intent> intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK){
                    assert result.getData() != null;

                    int requestCode = result.getData().getExtras().getInt("REQUEST_CODE");
                    Uri imageUri = result.getData().getData();
                    Bitmap photo = (Bitmap) result.getData().getExtras().get("data");

                    if (photo == null){
                        try {
                            if(Build.VERSION.SDK_INT < 28) {
                                photo = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), imageUri);
                            } else {
                                ImageDecoder.Source source = ImageDecoder.createSource(requireContext().getContentResolver(), imageUri);
                                photo = ImageDecoder.decodeBitmap(source);
                            }
                            binding.imageButton2.setImageBitmap(photo);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    Log.w("ACTIVITY_LAUNCHER", result.getData().getAction());
                    Log.w("ACTIVITY_LAUNCHER", String.valueOf(photo == null));
                    //Log.w("ACTIVITY_LAUNCHER", result.getData().getExtras().getParcelable());

                    binding.imageButton2.setImageURI(imageUri);
                    binding.imageButton2.setImageBitmap(photo);
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


        try{
            categories =  Category.listAll(Category.class);
            chosenCategory = categories.get(0);
        }catch (Exception e){
            chosenCategory = null;
        }

        binding = ActivityAddOrderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        manageFoodTypeDropDown();

        binding.addPostButton.setOnClickListener(view1 -> {
            Bitmap photoBitmap;

            try {
                photoBitmap = ((BitmapDrawable)binding.imageButton2.getDrawable()).getBitmap();
            } catch (Exception e){
                photoBitmap = null;
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
                    order = new Product(getService().user_authenticated.getId(), chosenCategory.getId(),
                            binding.namePostEdit.getText().toString()
                            ,binding.productDescriptionEdit.getText().toString(),
                            Base64.getEncoder().encodeToString(bitmapToByteArray(photoBitmap)), binding.expirationDateEdit.getText().toString());
                    order.save();
                    Toast.makeText(getContext(), "Produit ajouté", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Echec de l'ajout du produit", Toast.LENGTH_SHORT).show();
                }
            }

            if (mainBinding != null)
                mainBinding.bottomNavigationView.setSelectedItemId(R.id.home);
            else
                Util.navigateTo(view, new HomeFragment());
        });

        binding.imageButton2.setOnClickListener(viewButton -> {
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
        if (bitmap != null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
        return new ByteArrayOutputStream().toByteArray();
    }

    private void manageFoodTypeDropDown(){
        popupMenu = new PopupMenu(getContext(), binding.foodTypeEdit);
        popupMenu.inflate(R.menu.category_list_menu);

        popupMenu.setOnMenuItemClickListener(item ->{
            binding.foodTypeEdit.setText(item.getTitle());
            chosenCategory = Category.find(Category.class, "name = ?", binding.foodTypeEdit.getText().toString()).get(0);
            return true;
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            categories.forEach(category -> popupMenu.getMenu().add(category.getName()));
        }

        binding.foodTypeEdit.setCursorVisible(false);
        binding.foodTypeEdit.setFocusable(false);
        binding.foodTypeEdit.setInputType(InputType.TYPE_NULL);

        binding.foodTypeEdit.setOnClickListener(l -> popupMenu.show());
    }
}
