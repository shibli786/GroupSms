package com.example.shibli.toolbartoolbar;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MessageActivity extends AppCompatActivity {

    static Context context;
    ListView lv;
    EditText et;
    Button bt;

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
        final MessageAdapter adapter = (MessageAdapter) Adapter.getAdapter();

        lv.setAdapter(adapter);
        et = (EditText) findViewById(R.id.editText_message);
        bt = (Button) findViewById(R.id.button);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = et.getText().toString();

                if (text.equals("")) {

                }
                else{
                adapter.add(new MessageDataProvider(text, false, "asa"));
                    et.setText("");
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

                if (text.equals("")) {

                }
                else{
                    adapter.add(new MessageDataProvider(text, true, "asa"));
                    et.setText("");
                }
            }
        });
        context = MessageActivity.this;

    }

}
