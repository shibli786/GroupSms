package com.example.shibli.toolbartoolbar;

/**
 * Created by shibli on 5/11/2016.
 */
public class MessageListDataProvider {
    private String lastMessage;

    public MessageListDataProvider(String recipient,long timeStamp,String lastMessage) {
        this.recipient = recipient;
        this.timeStamp=timeStamp;
        this.lastMessage=lastMessage;

    }

    String recipient;

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getRecipient() {
        return recipient;
    }

    long timeStamp;


    public String getLastMessage() {
        return lastMessage;
    }


}
