package com.cndd.ldenglish.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cndd.ldenglish.R;
import com.cndd.ldenglish.model.Word;

import java.util.ArrayList;
import java.util.List;

public class WordAdapter<C> extends ArrayAdapter<Word> {
    private Context context;
    private List<Word> words, list, suggestions;
    private int resource;

    public WordAdapter(Context context, int resource, List<Word> words) {
        super(context, resource, words);
        this.context = context;
        this.resource = resource;
        this.words = words;
        this.list = new ArrayList<>(words);
        this.suggestions = new ArrayList<>();
    }

    @NonNull
    @Override
    public Filter getFilter(){
        return wordFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_listview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvID = (TextView) convertView.findViewById(R.id.tv_id);
            viewHolder.tvWords = (TextView) convertView.findViewById(R.id.tv_word);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Word word = words.get(position);

        if (word != null){
            viewHolder.tvWords.setText(word.getWord());
        }
        return convertView;
    }

    private Filter wordFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Word word : list) {
                    if (word.getWord().toLowerCase()
                            .startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(word);
                    }
                }
                FilterResults results = new FilterResults();
                results.values = suggestions;
                results.count = suggestions.size();
                return results;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<Word> filteredList = (ArrayList<Word>) results.values;
            if (results != null && results.count > 0) {
                clear();
                addAll(filteredList);
                notifyDataSetChanged();
            }

        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Word) resultValue).getWord();
        }
    };

    public class ViewHolder {
        TextView tvID, tvWords;
    }
}
