package com.example.shibli.toolbartoolbar;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.view.Gravity.LEFT;
import static android.view.Gravity.RIGHT;

/**
 * Created by shibli on 4/30/2016.
 */



public class MessageAdapter extends ArrayAdapter<MessageDataProvider> {







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



        tv.setBackgroundResource((pos) ? R.drawable.ic_chat_bubble_white_24dp : R.drawable.ic_chat_bubble_white_24dp);
       // LinearLayout.LayoutParams params= new  LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

      LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)tv.getLayoutParams();

        if(!pos){
            params.gravity= LEFT;
            Log.e("Sam", "valuessssssssssssss: "+pos+" "+getCount());
        }
        else{
            Log.e("Sam", "valuesssssssssssssss: " + pos + " " + getCount());

            params.gravity= RIGHT;}

        tv.setLayoutParams(params);

        return convertView;
    }
}
