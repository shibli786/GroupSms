package com.example.shibli.toolbartoolbar;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shibli.databse.DatabaseScema;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    static Context context;
    private static String recipient;
    ListView lv;
    EditText et;
    Button bt;
    int pos;
    boolean isGroup;
    MessageAdapter adapter;
    BroadcastReceiver broadcastReceiver;
    int count = 0;

    public static Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_message);
        context = getApplicationContext();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lv = (ListView) findViewById(R.id.message_listview);
        lv.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_NORMAL);

        registerReceiver(this.broadcastReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));


        Toast.makeText(MessageActivity.this, "Activity Created", Toast.LENGTH_LONG).show();


        et = (EditText) findViewById(R.id.editText_message);
        bt = (Button) findViewById(R.id.button);
        recipient = getIntent().getExtras().getString("groupname");
        getSupportActionBar().setTitle(recipient);
        adapter = (MessageAdapter) Adapter.newAdapter(recipient, MessageActivity.this);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        lv.setAdapter(adapter);

        lv.setSelection(adapter.getCount() - 1);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return true;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = et.getText().toString();

                if (text.equals("")) {

                } else {
                    MessageDataProvider m = new MessageDataProvider(text.trim(), false, System.currentTimeMillis());
                    adapter.add(m);


                    et.setText("");
                    DatabaseScema scema = new DatabaseScema(context);
                    scema.insertMessage(m, recipient);
                    MessageHelper messageHelper = new MessageHelper(context);
                    Group group = scema.getGroup(recipient);
                    if (group == null) {

                        String no = scema.getNumber(recipient);
                        messageHelper.sendSMS(no, text);

                    } else {
                        ArrayList<String> allGroupNumber = scema.getAllGroupNumbers(recipient);
                        Log.e("SAM group size",allGroupNumber.size()+"");
                        for (String number : allGroupNumber) {
                            messageHelper.sendSMS(number, text);
                        }


                    }


                    MessageListDataProvider messageListDataProvider = new MessageListDataProvider(recipient, System.currentTimeMillis(), text);

                    if (scema.searchRecipient(messageListDataProvider)) {

                        scema.updateMessageList(messageListDataProvider);


                         Toast.makeText(MessageActivity.this,"hai hai hai ",Toast.LENGTH_LONG).show();

                    } else {
                        scema.insertMessageList(messageListDataProvider);

                    }
                }


            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
                String text = et.getText().toString();

                if (text.equals("")) ;
                else {
                    MessageDataProvider m = new MessageDataProvider(text.trim(), true, System.currentTimeMillis());
                    adapter.add(m);

                    et.setText("");
                    DatabaseScema scema = new DatabaseScema(context);
                    scema.insertMessage(m, recipient);
                    MessageListDataProvider messageListDataProvider = new MessageListDataProvider(recipient, System.currentTimeMillis(), text);

                    if (scema.searchRecipient(messageListDataProvider)) {
                        scema.updateMessageList(messageListDataProvider);

                    } else {
                        scema.insertMessageList(messageListDataProvider);

                    }


                }
            }
        });
        context = MessageActivity.this;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Delete All Messages");

        MenuInflater menuInflater = getMenuInflater();


        DatabaseScema scema = new DatabaseScema(MessageActivity.this);
        Group group = scema.getGroup(recipient);
        if (group == null) {
            return true;
        }
        isGroup = true;
        menuInflater.inflate(R.menu.add_members, menu);

        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (isGroup) {
            if (item.getItemId() == R.id.addmembers) {
                Intent i = new Intent(MessageActivity.this, SelectContactsFromList.class);
                i.putExtra("groupname", recipient);
                startActivity(i);
            }

        }
        if (item.getTitle().equals("Delete All Messages")) {


            AlertDialog.Builder builder = new AlertDialog.Builder(MessageActivity.this);
            builder.setTitle("Are you sure you want to continue?");

            builder.setPositiveButton(
                    "Delete",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            DatabaseScema scema = new DatabaseScema(MessageActivity.this);
                            scema.deleteAllMessage(recipient);
                            adapter = (MessageAdapter) Adapter.newAdapter(recipient, MessageActivity.this);
                            lv.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                            dialog.cancel();
                        }
                    });

            builder.setNegativeButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder.create();
            alert11.show();


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.broadcastReceiver = new BroadcastReceiver() {
            @Override


            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, "message Received", Toast.LENGTH_LONG).show();

                SMSInfo sms = ParseSMS.readSMSFromBroadCastReceiver(context, intent);
                MessageDataProvider m = new MessageDataProvider(sms.SMS, true, sms.time);

                adapter.add(m);
                DatabaseScema scema = new DatabaseScema(MessageActivity.this);
                String name = scema.getContactNameByNumber(sms.phNo);
                scema.insertMessage(m, name);


            }
        };
        registerReceiver(this.broadcastReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));

    }
}
