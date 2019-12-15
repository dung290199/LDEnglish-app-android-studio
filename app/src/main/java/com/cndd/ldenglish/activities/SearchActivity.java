package com.cndd.ldenglish.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import com.cndd.ldenglish.R;
import com.cndd.ldenglish.model.Word;

public class SearchActivity extends AppCompatActivity {

    private Button btnBack;
    private AutoCompleteTextView search;
    private ListView lvWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();

        search = (AutoCompleteTextView) findViewById(R.id.search);

        search.setThreshold(1);
        search.setAdapter(MainActivity.mainAdapter);

        search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Word word = (Word) adapterView.getItemAtPosition(position);

                Intent intent = new Intent(SearchActivity.this, DetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("word", word);
                intent.putExtra("package", bundle);

                startActivity(intent);
            }
        });

        lvWord = (ListView) findViewById(R.id.lv_word);
        lvWord.setAdapter(MainActivity.mainAdapter);
        lvWord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Word word = (Word) adapterView.getItemAtPosition(position);

                Intent intent = new Intent(SearchActivity.this, DetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("word", word);
                intent.putExtra("padkage", bundle);

                startActivity(intent);
            }
        });
    }

    public void goBackHome(View view){
        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
