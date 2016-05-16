package com.example.shibli.toolbartoolbar;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by shibli on 5/10/2016.
 */
public class GroupAdapter extends ArrayAdapter<Group> {
    Context context;

    public GroupAdapter(Context context, int resource) {

        super(context, resource);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView= inflater.inflate(R.layout.single_row_contact,parent,false);}
        TextView name= (TextView) convertView.findViewById(R.id.contact_name);
        name.setGravity(Gravity.CENTER);
        name.setTextSize(17);
        TextView no= (TextView) convertView.findViewById(R.id.contact_no);
        no.setText("");

        name.setText(getItem(position).groupName);
        return  convertView;
    }

    @Override
    public void add(Group object) {
        super.add(object);
    }
}
