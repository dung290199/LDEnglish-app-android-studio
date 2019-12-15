package com.cndd.ldenglish.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cndd.ldenglish.R;
import com.cndd.ldenglish.adapter.FavoriteCardAdapter;
import com.cndd.ldenglish.adapter.WordAdapter;
import com.cndd.ldenglish.databases.FavoriteDatabase;
import com.cndd.ldenglish.databases.MainDatabase;
import com.cndd.ldenglish.model.Word;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Word> words, favorites;
    public static WordAdapter<Word> mainAdapter;
    public static FavoriteCardAdapter favoriteCardAdapter;
    public static MainDatabase mainDB;
    public static FavoriteDatabase favoriteDB;
    private Button btnSearch, btnNewWord, btnFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        mainDB = new MainDatabase(this);
        favoriteDB = new FavoriteDatabase(this);

        //get all contacts
        words = mainDB.getAllWords();
        favorites = favoriteDB.getAllWords();

        mainAdapter = new WordAdapter<>(this, R.layout.row_listview, words);
        favoriteCardAdapter = new FavoriteCardAdapter(this, favorites);


        btnSearch = (Button) findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);

            }
        });

        btnNewWord = (Button) findViewById(R.id.btn_addWord);
        btnNewWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewWordActivity.class);
                startActivity(intent);
            }
        });

        btnFavorite = (Button) findViewById(R.id.btn_favorite);
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
                startActivity(intent);
            }
        });

    }
}
