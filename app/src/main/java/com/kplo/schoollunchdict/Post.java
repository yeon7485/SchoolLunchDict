package com.kplo.schoollunchdict;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Post {
    private String title;
    private String contents;
    private String nickname;
    private String date;

    public Post(String title, String contents, String nickname, String date){
        this.title = title;
        this.contents = contents;
        this.nickname = nickname;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {

        this.date = date;
    }
}
