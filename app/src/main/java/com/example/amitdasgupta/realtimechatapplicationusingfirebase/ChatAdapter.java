package com.example.amitdasgupta.realtimechatapplicationusingfirebase;

import java.util.Date;

/**
 * Created by AMIT DAS GUPTA on 13-08-2017.
 */

public class ChatAdapter {
    String messagetext;
    String messageuser;
    long messagetime;

    public ChatAdapter(String messagetext, String messageuser) {
        this.messagetext = messagetext;
        this.messageuser = messageuser;
        messagetime= new Date().getTime();
    }

    public ChatAdapter() {
    }

    public String getMessagetext() {
        return messagetext;
    }

    public String getMessageuser() {
        return messageuser;
    }

    public long getMessagetime() {
        return messagetime;
    }

    public void setMessagetext(String messagetext) {
        this.messagetext = messagetext;
    }

    public void setMessageuser(String messageuser) {
        this.messageuser = messageuser;
    }

    public void setMessagetime(long messagetime) {
        this.messagetime = messagetime;
    }
}
