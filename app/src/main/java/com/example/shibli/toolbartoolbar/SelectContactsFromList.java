package com.example.shibli.toolbartoolbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shibli.databse.DatabaseScema;

import java.util.ArrayList;

public class SelectContactsFromList extends AppCompatActivity {
    ListView lv;
    ArrayList<Integer> checkedPositions;
    ArrayList<ContactDetail> al;
    static int c=0;
    String Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contacts_from_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lv= (ListView) findViewById(R.id.select_listView);
        final MySelectAdapter adapter= new MySelectAdapter(SelectContactsFromList.this,R.layout.single_row_select);
       lv.setAdapter(adapter);
        DatabaseScema scema= new DatabaseScema(SelectContactsFromList.this);
        al=  scema.getAllContacts();


        for (ContactDetail contactDetail:al){
            adapter.add(new SelectInformation(contactDetail.name));
        }
String name=getIntent().getExtras().getString("groupname");
        Name=name;

         checkedPositions = new ArrayList<Integer>();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view,
                                    int position, long arg3) {


                CheckBox cb = (CheckBox) view.findViewById(R.id.checkBox1);
                cb.setChecked(cb.isChecked()?false:true);
                if(cb.isChecked()){
                    c++;
                }
                else{c--;}
                getSupportActionBar().setTitle(c +" selected");




                if (cb.isChecked()) {

                    checkedPositions.add(position); // add position of the row
                    adapter.getItem(position).isSelected=true;
                    // when checkbox is checked
                } else {

                    adapter.getItem(position).isSelected=false;

                    checkedPositions.remove(position); // remove the position when the
                    // checkbox is unchecked
                    Toast.makeText(getApplicationContext(), "Row " + position + " is unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     menu.add("Done");


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      Toast.makeText(SelectContactsFromList.this,c+ "Member Added",Toast.LENGTH_LONG).show();
        DatabaseScema scema= new DatabaseScema(SelectContactsFromList.this);

      for(int i:checkedPositions) {
         scema.insertIntoGroupMemberTable(al.get(i),Name);

      }


        return super.onOptionsItemSelected(item);
    }

}
