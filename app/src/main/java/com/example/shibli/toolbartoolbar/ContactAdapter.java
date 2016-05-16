package com.example.shibli.toolbartoolbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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

        convertView= inflater.inflate(R.layout.single_row_contact,parent,false);}
          TextView name= (TextView) convertView.findViewById(R.id.contact_name);
            TextView number= (TextView)  convertView.findViewById(R.id.contact_no);
            name.setText(getItem(position).name);
            number.setText(getItem(position).number1);


        //setup the view with values an dreturn 
        return convertView;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}
