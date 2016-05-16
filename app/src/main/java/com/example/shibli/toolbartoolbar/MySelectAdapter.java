package com.example.shibli.toolbartoolbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by shibli on 5/14/2016.
 */

public class MySelectAdapter extends ArrayAdapter<SelectInformation> {
 Context context;

    public MySelectAdapter(Context context, int resource) {
        super(context, resource);
        this.context=context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView= inflater.inflate(R.layout.single_row_select,parent,false);}
        TextView name= (TextView) convertView.findViewById(R.id.select_name);
        CheckBox checkBox= (CheckBox) convertView.findViewById(R.id.checkBox1);
       // TextView number= (TextView)  convertView.findViewById(R.id.contact_no);
        boolean isSelected=getItem(position).isSelected;
        //Log.e("Samdsssssssssssssssds", getItem(position).name+"  ;");

        name.setText(getItem(position).name);
        checkBox.setChecked(isSelected);
        return  convertView;

    }
}
