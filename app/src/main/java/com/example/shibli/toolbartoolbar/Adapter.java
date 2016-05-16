package com.example.shibli.toolbartoolbar;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.shibli.databse.DatabaseScema;

import java.util.ArrayList;

/**
 * Created by shibli on 4/30/2016.
 */
public class Adapter {


    private static ArrayAdapter<MessageDataProvider> adapter;

    Adapter() {

    }

    public static ArrayAdapter<MessageDataProvider> newAdapter(String name,Context context) {


        //return apropriate adapter;
        DatabaseScema scema = new DatabaseScema(context);
        //Group g= scema.getGroup(name);
        ArrayList<MessageDataProvider> al = scema.getAdapter(name);
        if (al == null) {
            adapter = new MessageAdapter(context, R.layout.single_row_message);


        } else {
            adapter = new MessageAdapter(context, R.layout.single_row_message);

            for (MessageDataProvider m : al) {

                adapter.add(m);
            }


        }

        return adapter;
    }

}
