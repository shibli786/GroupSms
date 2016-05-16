package com.example.shibli.toolbartoolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * Created by shibli on 5/15/2016.
 */
public class ParseSMS {


    public static SMSInfo readSMSFromBroadCastReceiver(Context context, Intent intent) {
        String address = "";
        String message = "";
        long time = 0;
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String str = "";
        if (bundle != null) {
            // Retrieve the SMS.

            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);


                time = msgs[i].getTimestampMillis();
                message += msgs[i].getDisplayMessageBody();
                address = msgs[i].getOriginatingAddress();

                // In case of a particular App / Service.
                //if(msgs[i].getOriginatingAddress().equals("+91XXX"))
                //{

                //}
            }
        }
        return new SMSInfo(message,time,address);
    }
}

class SMSInfo{
    public SMSInfo(String SMS, long time, String phNo) {
        this.SMS = SMS;
        this.time = time;
        this.phNo = phNo;
    }

    String SMS;String phNo;long time;

}