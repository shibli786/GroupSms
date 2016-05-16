package com.example.shibli.toolbartoolbar;

/**
 * Created by shibli on 4/30/2016.
 */
public class MessageDataProvider {



    public MessageDataProvider(String message, boolean position, long timeStamp) {
        this.message = message;
        this.position = position;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public boolean getPosition() {
        return position;
    }

    String message;
   boolean position;
    long timeStamp;
}
