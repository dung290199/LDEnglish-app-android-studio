
package com.cndd.ldenglish.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cndd.ldenglish.R;
import com.cndd.ldenglish.model.Word;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {

    private TextView tvWord, tvContent, tvTiTle, tvID;
    private FloatingActionButton btnEdit, btnDelete, btnLike;
    private boolean like = false;
    private TextToSpeech textToSpeech;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_word_details);

        tvWord = (TextView) findViewById(R.id.tv_word);
        tvContent = (TextView) findViewById(R.id.tv_content);
        tvTiTle = (TextView) findViewById(R.id.tv_title);
        tvID = (TextView) findViewById(R.id.tv_id);

        btnEdit = (FloatingActionButton) findViewById(R.id.btn_edit);
        btnDelete = (FloatingActionButton) findViewById(R.id.btn_delete);
        btnLike = (FloatingActionButton) findViewById(R.id.btn_like);

        seekBar = (SeekBar) findViewById(R.id.seek_bar_speed);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.US);
                }
                tvWord.setEnabled(true);
            }
        });

        tvWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

        final Intent intent = getIntent();
        Word word = null;
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("package");
             word = (Word) bundle.getSerializable("word");
            tvTiTle.setText(word.getWord());
            tvID.setText(String.valueOf(word.getId()));
            tvWord.setText(word.getWord());
            tvContent.setText(word.getContent());
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mainDB.deleteById(Integer.parseInt(tvID.getText().toString()));
                Intent newIntent = new Intent(DetailsActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        like = MainActivity.favoriteDB.isContains(word.getId());

        int resourceImage = like ? R.drawable.ic_star_like : R.drawable.ic_star_unlike;
        btnLike.setImageResource(resourceImage);

        final Word finalWord = word;
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (like){
                    removeFromFavorite(finalWord);
                } else {
                    addToFavorite(finalWord);
                }

            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this, EditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("word", finalWord);
                intent.putExtra("package", bundle);

                startActivity(intent);
            }
        });
    }

    private void speak() {
        String text = tvWord.getText().toString();
        float speed = (float) seekBar.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;
        textToSpeech.setSpeechRate(speed);
        Toast.makeText(this, text, Toast.LENGTH_SHORT);
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void removeFromFavorite(Word finalWord) {
        btnLike.setImageResource(R.drawable.ic_star_unlike);
        MainActivity.favoriteDB.deleteById(finalWord.getId());
        like = false;
    }

    public void goBackHome(View view){
        Intent intent = new Intent(DetailsActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    private void addToFavorite(Word finalWord) {
        btnLike.setImageResource(R.drawable.ic_star_like);
        MainActivity.favoriteDB.addWord(finalWord);
        MainActivity.favoriteCardAdapter.notifyItemInserted(MainActivity.favoriteCardAdapter.getItemCount());
        Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
        like = true;
    }
}
