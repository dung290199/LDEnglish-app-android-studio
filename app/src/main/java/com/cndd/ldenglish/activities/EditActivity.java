package com.cndd.ldenglish.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cndd.ldenglish.R;
import com.cndd.ldenglish.model.Word;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditActivity extends AppCompatActivity {

    private TextView tvTiTle, tvID;
    private EditText edtWord, edtContent;
    private FloatingActionButton btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getSupportActionBar().hide();

        tvTiTle = (TextView) findViewById(R.id.tv_title);
        tvID = (TextView) findViewById(R.id.tv_id);

        edtWord = (EditText) findViewById(R.id.edt_word);
        edtContent = (EditText) findViewById(R.id.edt_content);

        btnSave = (FloatingActionButton) findViewById(R.id.btn_save);

        final Intent intent = getIntent();
        Word word = null;
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("package");
            word = (Word) bundle.getSerializable("word");
            tvTiTle.setText(word.getWord());
            tvID.setText(String.valueOf(word.getId()));
            edtWord.setText(word.getWord());
            edtContent.setText(word.getContent());
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    private void save() {

        int id = Integer.parseInt(tvID.getText().toString());
        String newWord = edtWord.getText().toString();
        String newContent = edtContent.getText().toString();
        final Word finalWord = new Word(id, newWord, newContent);

        MainActivity.mainDB.update(finalWord);
        MainActivity.mainAdapter.notifyDataSetChanged();
        MainActivity.mainAdapter.setNotifyOnChange(true);
        Intent intent = new Intent(EditActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
