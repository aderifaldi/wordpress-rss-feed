package com.ar.simplerssfeed.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ar.simplerssfeed.R;
import com.ar.simplerssfeed.fragment.FragmentRSSFeedAde;
import com.ar.simplerssfeed.fragment.FragmentRSSFeedPuja;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private ActionBar actionBar;
    private DrawerLayout mDrawerLayout;
    private Fragment fragment = null;
    private TextView txt_blog_1, txt_blog_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_side_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setElevation(0);
        actionBar.setTitle("");

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        txt_blog_1 = (TextView) findViewById(R.id.txt_blog_1);
        txt_blog_2 = (TextView) findViewById(R.id.txt_blog_2);

        txt_blog_1.setOnClickListener(this);
        txt_blog_2.setOnClickListener(this);

        tampilkanKonten(1);

    }

    private void tampilkanKonten(int feed){

        switch (feed){
            case 1:
                fragment = new FragmentRSSFeedPuja();
                break;

            case 2:
                fragment = new FragmentRSSFeedAde();
                break;
        }

        if (fragment != null){
            try {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content, fragment);
                fragmentTransaction.commit();
            } catch (Exception ex) {

            }
        }

    }

    private void bukaSideMenu(){
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    private void tutupSideMenu(){
        mDrawerLayout.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                bukaSideMenu();
                return true;

            default:
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_blog_1:

                tampilkanKonten(1);
                tutupSideMenu();

                break;

            case R.id.txt_blog_2:

                tampilkanKonten(2);
                tutupSideMenu();

                break;
        }

    }
}
