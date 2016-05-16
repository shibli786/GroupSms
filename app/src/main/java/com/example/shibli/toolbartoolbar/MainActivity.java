package com.example.shibli.toolbartoolbar;

import android.os.Bundle;
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




    public void setupViewPager(ViewPager upViewPager) {
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager(),this);
        adapter.add(new ContactFragment(),"ContactS");
              adapter.add(new MessageFragment(),"Messages");

        adapter.add(new GroupFragment(),"Groups");
        viewPager.setAdapter(adapter);

    }
}
