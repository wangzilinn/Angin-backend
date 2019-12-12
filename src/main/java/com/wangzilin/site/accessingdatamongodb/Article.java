package com.***REMOVED***.site.accessingdatamongodb;

import org.springframework.data.annotation.Id;

public class Article {
    @Id
    private String id;
    private String title;
    private String text;

    public Article(){}

    public Article(String title, String text) {
        this.title = title;
        this.text = text;
    }

    @Override
    public String toString(){
        return String.format("%s,%s",title, text);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
