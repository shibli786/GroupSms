package com.example.shibli.toolbartoolbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by shibli on 4/26/2016.
 */

public class ContactAdapter extends ArrayAdapter<ContactDetail>
{
    Context context;

    public ContactAdapter(Context context, int resource) {
        super(context, resource);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView= inflater.inflate(R.layout.single_row_contact,parent,false);
        }

        //setup the view with values an dreturn 
        return convertView;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}
