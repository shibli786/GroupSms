package com.example.shibli.toolbartoolbar;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.widget.Toast;

public class MessageHelper {


    Context context;

    MessageHelper(Context context) {

    }

    // Method to send SMS.
    public void sendSMS(String mobNo, String message) {
        String smsSent = "SMS_SENT";
        String smsDelivered = "SMS_DELIVERED";
        PendingIntent sentPI = PendingIntent.getBroadcast(context, 0,
                new Intent(smsSent), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0,
                new Intent(smsDelivered), 0);

        // Receiver for Sent SMS.

        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        //  Toast.makeText(context.getBaseContext(), "SMS sent",
                        ///     Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        // Toast.makeText(context.getBaseContext(), "Generic failure",
                        //        Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        // Toast.makeText(getBaseContext(), "No service",
                        //        Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(context, "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(context, "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(smsSent));

        // Receiver for Delivered SMS.
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(context, "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(smsDelivered));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(mobNo, null, message, sentPI, deliveredPI);
    }

}
