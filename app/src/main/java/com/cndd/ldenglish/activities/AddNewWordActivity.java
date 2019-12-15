package com.cndd.ldenglish.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cndd.ldenglish.R;
import com.cndd.ldenglish.model.Word;

public class AddNewWordActivity extends AppCompatActivity {

    private Button btnSave;
    private EditText edtWord, edtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_new_word);

        edtWord = (EditText) findViewById(R.id.edt_word);
        edtContent = (EditText) findViewById(R.id.edt_content);

        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = edtWord.getText().toString();
                String content = edtContent.getText().toString();
                Word newWord = new Word(word, content);
                saveWords(newWord);
                Toast.makeText(AddNewWordActivity.this, "A new word saved", Toast.LENGTH_LONG).show();
                goBackHome(v);
            }
        });
    }

    public void saveWords(Word word) {
        MainActivity.mainDB.addWord(word);
    }

    public void goBackHome(View view){
        Intent intent = new Intent(AddNewWordActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
