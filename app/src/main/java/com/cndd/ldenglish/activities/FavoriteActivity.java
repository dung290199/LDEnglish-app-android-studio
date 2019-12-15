package com.cndd.ldenglish.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cndd.ldenglish.R;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_favorite);

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setAdapter(MainActivity.favoriteCardAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    public void goBackHome(View view){
        Intent intent = new Intent(FavoriteActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
