package com.example.hairongwu.chatchat;

/**
 * Created by hairongwu on 2/14/16.
 */
public class ListElement {
    public String message;
    public String nickname;

    ListElement() {};
    ListElement(String message, String nickname) {
        this.message = message;
        this.nickname = nickname;
    }

    public String getMessage(){return message;}
    public void setMessage(String message){this.message = message;}

    public String getNickname(){return nickname;}
    public void setNickname(String nickname){
        this.nickname= nickname;
    }
}
