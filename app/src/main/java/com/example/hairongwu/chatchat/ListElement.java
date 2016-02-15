package com.example.hairongwu.chatchat;

/**
 * Created by hairongwu on 2/14/16.
 */
public class ListElement {
    public String message;
    public String nickname;
    public String timestamp;
    public String user_id;

    ListElement() {};
    ListElement(String timestamp, String message,
                String nickname, String user_id) {
        this.timestamp= timestamp;
        this.message  = message;
        this.nickname = nickname;
        this.user_id  = user_id;
    }

    public String getTimestamp(){return timestamp;}
    public void setTimestamp(String timestamp){this.timestamp=timestamp;}
    public String getMessage(){return message;}
    public void setMessage(String message){this.message = message;}
    public String getNickname(){return nickname;}
    public void setNickname(String nickname){
        this.nickname= nickname;
    }
    public String getUser_id(){return user_id;}
    public void setUser_id(String user_id){this.user_id=user_id;}
}
