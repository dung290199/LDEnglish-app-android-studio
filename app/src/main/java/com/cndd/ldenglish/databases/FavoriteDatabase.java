package com.cndd.ldenglish.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cndd.ldenglish.model.Word;

import java.util.ArrayList;

public class FavoriteDatabase extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "FAVORITE_WORD";


    public FavoriteDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = "CREATE TABLE Favorite_word(id INTEGER primary key,"
                + "word TEXT,"
                + "content TEXT)";
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addWord(Word word){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("word", word.getWord());
        values.put("content", word.getContent());
        db.insert("Favorite_word", null, values);
        db.close();
    }

    public ArrayList<Word> getAllWords(){
        ArrayList<Word> words = new ArrayList<>();
        String script = "Select * from Favorite_word";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(script, null);
        while (cursor.moveToNext()){
            Word word = new Word();
            word.setId(cursor.getInt(0));
            word.setWord(cursor.getString(1));
            word.setContent(cursor.getString(2));
            words.add(word);
        }
        return words;
    }

    public boolean deleteById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("Favorite_word", "id=?", new String[]{String.valueOf(id)});
        return result == 1;
    }

    public boolean isContains(int id) {
        ArrayList<Word> words = getAllWords();
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).getId() == id){
                return true;
            }
        }
        return false;
    }
//
//    public boolean isContains(int id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "SELECT count(*) FROM Favorite_word WHERE id=" + id;
//        Cursor cursor = db.rawQuery(query, null);
//        boolean result = cursor.moveToFirst();
//        cursor.close();
//        return result;
//    }
}
