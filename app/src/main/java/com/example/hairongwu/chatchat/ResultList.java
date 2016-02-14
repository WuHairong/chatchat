package com.example.hairongwu.chatchat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by hairongwu on 2/9/16.
 */
public class    ResultList {

        @SerializedName("timestamp")
        @Expose
        public String timestamp;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("nickname")
        @Expose
        public String nickname;
        @SerializedName("user_id")
        @Expose
        public String userId;
        @SerializedName("message_id")
        @Expose
        public String messageId;


    public ResultList(){
    }
    /**
     *
     * @param timestamp
     * @param message
     * @param nickname
     * @param userId
     * @param messageId
     */

    public ResultList(String timestamp, String message,
                      String nickname, String userId,
                      String messageId){
        this.timestamp =timestamp;
        this.message   = message;
        this.nickname  = nickname;
        this.userId    = userId;
        this.messageId = messageId;

    }

    public String getTimestamp(){return timestamp;}
    public void setTimestamp(String timestamp){this.timestamp = timestamp;}

    public String getMessage(){return message;}
    public void setMessage(String message){this.message = message;}

    public String getNickname(){return nickname;}
    public void setNickname(String nickname){this.nickname=nickname;}

    public String getUserId(){return userId;}
    public void setUserId(String userId){this.userId=userId;}

    public String getMessageId(){return messageId;}
    public void setMessageId(String messageId){this.messageId=messageId;}
}
