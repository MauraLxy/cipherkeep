package com.example.cipherkeep;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputActivity extends Activity{

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_page);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    public void selectCategory(View view) {
        Button button = (Button) view;
        // if selected, then cancel the select
        button.setSelected(!button.isSelected());
    }

    public void add(View view) {
        // get inputs
        EditText nameInput = findViewById(R.id.nameInput);
        EditText urlInput = findViewById(R.id.urlInput);
        EditText usernameInput = findViewById(R.id.usernameInput);
        EditText passwordInput = findViewById(R.id.passwordInput);

        String name = nameInput.getText().toString().trim();
        String url = urlInput.getText().toString().trim();
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        List<String> selectedCategories = new ArrayList<>();
        if (findViewById(R.id.category_entertain).isSelected()) {
            selectedCategories.add(getString(R.string.entertainment));
        }
        if (findViewById(R.id.category_life).isSelected()) {
            selectedCategories.add(getString(R.string.life));
        }
        if (findViewById(R.id.category_study).isSelected()) {
            selectedCategories.add(getString(R.string.study));
        }
        if (findViewById(R.id.category_work).isSelected()) {
            selectedCategories.add(getString(R.string.work));
        }
        if (findViewById(R.id.category_other).isSelected()) {
            selectedCategories.add(getString(R.string.other));
        }

        if (name.isEmpty()) {
            Toast.makeText(this, "网站名不能为空", Toast.LENGTH_SHORT).show();
        } else if (username.isEmpty()) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
        } else if (selectedCategories.isEmpty()) {
            Toast.makeText(this, "请选择至少一个类别", Toast.LENGTH_SHORT).show();
        }else{
            Map<String,Object> info = new HashMap<>();
            info.put("webname",name);
            info.put("url",url);
            info.put("category",selectedCategories);
            info.put("username",username);
            info.put("password",password);

            db.collection("user").document(auth.getCurrentUser().getUid())
                    .collection("entry").add(info)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(InputActivity.this, "Entry added successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(InputActivity.this, TagActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(InputActivity.this, "Failed to add entry: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });



        }

    }

}
