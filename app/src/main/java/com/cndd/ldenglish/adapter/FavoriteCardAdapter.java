package com.cndd.ldenglish.adapter;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cndd.ldenglish.R;
import com.cndd.ldenglish.activities.MainActivity;
import com.cndd.ldenglish.model.Word;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Locale;

public class FavoriteCardAdapter extends RecyclerView.Adapter<FavoriteCardAdapter.MyViewHolder> {
    private Context context;
    private List<Word> words;
    private TextToSpeech textToSpeech;

    public FavoriteCardAdapter(Context context, List<Word> words) {
        this.context = context;
        this.words = words;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_word, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int position) {
        myViewHolder.tvID.setText(String.valueOf(words.get(position).getId()));
        myViewHolder.tvWord.setText(words.get(position).getWord());
        myViewHolder.tvContent.setText(words.get(position).getContent());


        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.US);
                }
                myViewHolder.tvWord.setEnabled(true);
            }
        });

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, words.get(position).getWord(), Toast.LENGTH_SHORT).show();
                String text = myViewHolder.tvWord.getText().toString();
                float speed = (float) myViewHolder.seekBarSpeed.getProgress() / 50;
                if (speed < 0.1) speed = 0.1f;
                textToSpeech.setSpeechRate(speed);
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        myViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = words.get(position).getId();
                boolean result = MainActivity.favoriteDB.deleteById(id);

                String success = words.get(position).getWord() + " was deleted";
                String failed = "Nothing is deleted";
                String textToToast = result ? success :failed;

                words.remove(position);
                MainActivity.favoriteCardAdapter.notifyItemRemoved(position);

                Toast.makeText(context, textToToast, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvID, tvWord, tvContent;
        FloatingActionButton btnEdit, btnDelete;
        SeekBar seekBarSpeed;
        public MyViewHolder(View view){
            super(view);
            tvID = (TextView) view.findViewById(R.id.tv_id);
            tvWord = (TextView) view.findViewById(R.id.tv_word);
            tvContent = (TextView) view.findViewById(R.id.tv_content);
            btnEdit = (FloatingActionButton) view.findViewById(R.id.btn_edit);
            btnDelete = (FloatingActionButton) view.findViewById(R.id.btn_delete);
            seekBarSpeed = (SeekBar) view.findViewById(R.id.seek_bar_speed);
        }
    }
}
