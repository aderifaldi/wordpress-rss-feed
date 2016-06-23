package com.ar.simplerssfeed.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Base64;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.ar.simplerssfeed.R;
import com.ar.simplerssfeed.app.model.Post;
import com.squareup.picasso.Picasso;

import java.io.InputStream;

/**
 * Created by aderifaldi on 26/04/2016.
 */
public class DetailFeedActivity extends AppCompatActivity {

    private TextView txt_item_feed_author, txt_item_feed_title;
    private ImageView img_profile;
    private Bundle extras;
    private Post post;
    private WebView webViewContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_feed);

        extras = getIntent().getExtras();
        post = (Post) extras.getSerializable("data");

        img_profile = (ImageView) findViewById(R.id.img_profile);

        txt_item_feed_author = (TextView) findViewById(R.id.txt_item_feed_date);
        txt_item_feed_title = (TextView) findViewById(R.id.txt_item_feed_title);
        webViewContent = (WebView) findViewById(R.id.webViewContent);

        Picasso.with(this).
                load(post.getAuthor().
                        getAvatar_URL()).into(img_profile);

        txt_item_feed_author.setText(post.getAuthor().getName());
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
