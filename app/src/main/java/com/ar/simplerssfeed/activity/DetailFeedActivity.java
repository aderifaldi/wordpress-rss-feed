package com.ar.simplerssfeed.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.ar.simplerssfeed.R;
import com.ar.simplerssfeed.app.model.Post;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by aderifaldi on 26/04/2016.
 */
public class DetailFeedActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar actionBar;

    private TextView txt_item_feed_date, txt_item_feed_title, txt_author_name;
    private ImageView img_profile;
    private Bundle extras;
    private Post post;
    private WebView webViewContent;
    private SimpleDateFormat sdf;
    private Locale id = new Locale("ID", "ID");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_feed);

        extras = getIntent().getExtras();
        post = (Post) extras.getSerializable("data");

        sdf = new SimpleDateFormat("EEEE, dd-MM-yyyy | HH:mm", id);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_arrow_back);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setElevation(0);
        actionBar.setTitle("");

        img_profile = (ImageView) findViewById(R.id.img_profile);

        txt_item_feed_date = (TextView) findViewById(R.id.txt_item_feed_date);
        txt_item_feed_title = (TextView) findViewById(R.id.txt_item_feed_title);
        txt_author_name = (TextView) findViewById(R.id.txt_author_name);

        webViewContent = (WebView) findViewById(R.id.webViewContent);

        Picasso.with(this).
                load(post.getAuthor().
                getAvatar_URL()).into(img_profile);

        txt_author_name.setText(post.getAuthor().getName());

        txt_item_feed_date.setText(sdf.format(post.getDate()));
        txt_item_feed_title.setText(post.getTitle());

        String stringContent = post.getContent().replace("\\", "");

        webViewContent.getSettings().setJavaScriptEnabled(true);
        webViewContent.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                injectCSS();
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                return true;
            }
        });

        webViewContent.loadData(stringContent, "text/html; charset=utf-8", "UTF-8");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_blog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_share:
                shareURL();
                break;

            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.action_open_browser:
                openBrowser();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void openBrowser(){
        String url = post.getURL();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void shareURL(){

        String title = post.getTitle();
        String url = post.getURL();

        Intent share = new Intent(android.content.Intent.ACTION_SEND);

        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra(Intent.EXTRA_SUBJECT, title);
        share.putExtra(Intent.EXTRA_TEXT, url);

        startActivity(Intent.createChooser(share, "Share Blog"));
    }

    private void injectCSS() {
        try {
            InputStream inputStream = getAssets().open("style.css");
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
            webViewContent.loadUrl("javascript:(function() {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var style = document.createElement('style');" +
                    "style.type = 'text/css';" +
                    // Tell the browser to BASE64-decode the string into your script !!!
                    "style.innerHTML = window.atob('" + encoded + "');" +
                    "parent.appendChild(style)" +
                    "})()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
