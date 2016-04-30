package com.example.shibli.toolbartoolbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

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
            byte[] pdus = (byte[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu(pdus,
                        android.provider.Telephony.Sms.Intents.SMS_RECEIVED_ACTION);


                // In case of a particular App / Service.
                //if(msgs[i].getOriginatingAddress().equals("+91XXX"))
                //{

                //}
            }
            // Display the SMS as Toast.
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        }
    }


}
