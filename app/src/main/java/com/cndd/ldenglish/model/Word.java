package com.cndd.ldenglish.model;

import java.io.Serializable;

public class Word implements Serializable {
    private int id;
    private String word;
    private String content;

    public Word() {
    }

    public Word(String word, String content) {
        this.word = word;
        this.content = content;
    }

    public Word(int id, String word, String content) {
        this.id = id;
        this.word = word;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
