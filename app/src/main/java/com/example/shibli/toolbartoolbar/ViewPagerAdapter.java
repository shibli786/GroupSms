package com.example.shibli.toolbartoolbar;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by shibli on 4/25/2016.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter{
    private ArrayList<Fragment> fragmentlist=new ArrayList<Fragment>();
    private ArrayList<String> titlelist=new ArrayList<String>();
    Context context;


    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context=context;
    }



    public void add(Fragment fragment, String title) {
        fragmentlist.add(fragment);
        titlelist.add(title);

    }

    @Override
        public Fragment getItem(int position) {
        Toast.makeText(context,"getItem "+getPageTitle(position),Toast.LENGTH_SHORT).show();
        return fragmentlist.get(position);
    }

    @Override
    public int getCount() {
        return fragmentlist.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titlelist.get(position);
    }
}
