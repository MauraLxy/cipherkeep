package com.example.cipherkeep;

import android.os.Bundle;

public class ListActivity extends TagActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_list);
    }
}
