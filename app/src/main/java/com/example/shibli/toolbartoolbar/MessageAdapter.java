package com.example.shibli.toolbartoolbar;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by shibli on 4/30/2016.
 */



public class MessageAdapter extends ArrayAdapter<MessageDataProvider> {;




    private Context context;
    private int  resource;
    public MessageAdapter(Context context, int resource) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
    }

    @Override
    public void add(MessageDataProvider object) {
        super.add(object);
    }

    @Override
    public MessageDataProvider getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView= inflater.inflate(resource,parent,false);
        }
        TextView tv= (TextView) convertView.findViewById(R.id.chat_text);
        tv.setTextColor(Color.BLACK);
        String message=getItem(position).message;
        boolean pos=getItem(position).position;
        String timeStamp=getItem(position).timeStamp;


        tv.setText(message);



        tv.setBackgroundResource((pos) ? R.drawable.speech_bubble_b : R.drawable.sppech_bubble_a);
        LinearLayout.LayoutParams params= new  LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if(!pos){
            params.gravity= Gravity.LEFT;
        }
        else{
        params.gravity=Gravity.RIGHT;}

        tv.setLayoutParams(params);

        return convertView;
    }
}
