package com.example.shibli.toolbartoolbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shibli.databse.DatabaseScema;

import java.util.ArrayList;

public class GroupmembersActivity extends AppCompatActivity {
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupmembers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lv = (ListView) findViewById(R.id.list_group_member);
        ContactAdapter adapter = new ContactAdapter(GroupmembersActivity.this, R.layout.single_row_contact);
        lv.setAdapter(adapter);
        DatabaseScema scema = new DatabaseScema(GroupmembersActivity.this);



        ArrayList<ContactDetail> al = scema.getGroupMembers(getIntent().getExtras().getString("name"));
     if(al==null){
         Toast.makeText(GroupmembersActivity.this,"No Members",Toast.LENGTH_LONG).show();
     }
        else
        adapter.addAll(al);
    }

}
