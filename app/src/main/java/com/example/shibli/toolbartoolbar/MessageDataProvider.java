package com.example.shibli.toolbartoolbar;

/**
 * Created by shibli on 4/30/2016.
 */
public class MessageDataProvider {
    String message;

    public MessageDataProvider(String message, boolean position, String timeStamp) {
        this.message = message;
        this.position = position;
        this.timeStamp = timeStamp;
    }

   boolean position;
    String timeStamp;
}
