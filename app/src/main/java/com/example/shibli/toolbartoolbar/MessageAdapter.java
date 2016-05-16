package com.example.shibli.toolbartoolbar;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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
        TextView time= (TextView) convertView.findViewById(R.id.text_time);
        SimpleDateFormat format= new SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        time.setText(getItem(position).getTimeStamp() + "");
        time.setTextColor(Color.GRAY);
        calendar.setTimeInMillis(Long.parseLong(time.getText().toString()));
        time.setText("   " + new StringBuffer(format.format(calendar.getTime())).insert(9, "\n"));

        tv.setTextColor(Color.BLACK);
        String message=getItem(position).message;
        //message=message.replace('\n',' ');


        StringBuffer sb= new StringBuffer(message);

        int i=20;
        //GK PUBLIC SCHOOL
        // Dear parents School will
        // remain closed on Monday 20/05/16
        // Regards principal
       // while(i<sb.length()){
         //   int in=sb.indexOf(" ",i);
           // sb.insert(in,'\n');
       // }




        boolean pos=getItem(position).position;
        long timeStamp=getItem(position).timeStamp;



        tv.setText(new String(sb));

    LinearLayout layout=(LinearLayout)convertView.findViewById(R.id.linear_chat_id);
        RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);



        layout.setBackgroundResource((pos) ? R.drawable.bubble_green : R.drawable.bubble_yellow);
       // LinearLayout.LayoutParams params= new  LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);



        if(!pos){
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);


            Log.e("Sam", "valuessssssssssssss: "+pos+" "+getCount());
        }
        else{
            Log.e("Sam", "valuesssssssssssssss: " + pos + " " + getCount());
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);


        }
layout.setLayoutParams(params);

        return convertView;
    }
}
