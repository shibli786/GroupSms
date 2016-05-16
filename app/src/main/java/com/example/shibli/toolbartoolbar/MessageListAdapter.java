package com.example.shibli.toolbartoolbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by shibli on 5/11/2016.
 */
public class MessageListAdapter extends ArrayAdapter<MessageListDataProvider> {
    Context context;



    public MessageListAdapter(Context context, int resource) {
        super(context, resource);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView= inflater.inflate(R.layout.message_list,parent,false);}
        TextView recipient= (TextView) convertView.findViewById(R.id.last_recipient);
        TextView time= (TextView)  convertView.findViewById(R.id.time);


        SimpleDateFormat format= new SimpleDateFormat("dd/MM/yy : HH:MM:yy", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        time.setText(getItem(position).getTimeStamp()+"");
        calendar.setTimeInMillis(Long.parseLong(time.getText().toString()));

        recipient.setText(getItem(position).getRecipient());
       // number.setText(getItem(position).number1);
        time.setText(format.format(calendar.getTime()));
        TextView lastMessage= (TextView)  convertView.findViewById(R.id.last_message);
        String mes=getItem(position).getLastMessage();
String lMessage=getItem(position).getLastMessage();
        if(lMessage.length()>20){
            mes=lMessage.substring(0,20)+"...";

        }
        if(lMessage.contains("\n")){
            mes=lMessage.substring(0,lMessage.indexOf("\n"))+"...";

        }

            lastMessage.setText(mes);
        //9455553333

        //setup the view with values an dreturn
        return convertView;
    }
}
