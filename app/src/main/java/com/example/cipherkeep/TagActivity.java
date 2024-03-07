package com.example.cipherkeep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TagActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
    }

    public void toAddPage(View view) {
        Intent intent = new Intent(TagActivity.this, InputActivity.class);
        startActivity(intent);
    }


}

