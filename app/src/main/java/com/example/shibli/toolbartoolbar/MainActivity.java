package com.example.shibli.toolbartoolbar;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity  {
    private ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout= (TabLayout) findViewById(R.id.tabs);
        viewPager= (ViewPager) findViewById(R.id.viewpager);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("GN App");
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

       // viewPager.setCurrentItem(1);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            if (!Telephony.Sms.getDefaultSmsPackage(this).equals("com.example.shibli.toolbartoolbar")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("This app is not set as your default messaging app. Do you want to set it as default?")
                        .setCancelable(false)
                        .setTitle("Alert!")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @TargetApi(19)
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                                intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, getPackageName());
                                startActivity(intent);
                            }
                        });
                builder.show();
            }
        }
    }


    public void setupViewPager(ViewPager upViewPager) {
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager(),this);
        adapter.add(new ContactFragment(),"ContactS");
              adapter.add(new MessageFragment(),"Messages");

        adapter.add(new GroupFragment(),"Groups");
        viewPager.setAdapter(adapter);

    }
}
