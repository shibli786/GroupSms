package com.example.shibli.toolbartoolbar;

import android.widget.ArrayAdapter;

/**
 * Created by shibli on 4/30/2016.
 */
public class Adapter {


    public static ArrayAdapter<MessageDataProvider> getAdapter() {
        if(adapter==null)
             adapter = new MessageAdapter(MessageActivity.getContext(),R.layout.single_row_message);
        return adapter;
    }

    private static ArrayAdapter<MessageDataProvider> adapter;
    private Adapter(){

    }

}
