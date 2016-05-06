package com.example.shibli.toolbartoolbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {
    public SMSReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.


        // Parse the SMS.


        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String str = "";
        if (bundle != null) {
            // Retrieve the SMS.
            String address = "";
            String message="";
            long time ;
            byte[] pdus = (byte[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu(pdus,
                        android.provider.Telephony.Sms.Intents.SMS_RECEIVED_ACTION);


             time=     msgs[i].getTimestampMillis();
             message+=   msgs[i].getDisplayMessageBody();
             address=   msgs[i].getOriginatingAddress();

                // In case of a particular App / Service.
                //if(msgs[i].getOriginatingAddress().equals("+91XXX"))
                //{

                //}
            }
            //saveData(time,message,address);
            //get Adapter and set Chat text

           // Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        }
    }


}
