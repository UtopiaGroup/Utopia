package com.Utopia.utopia.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.util.EncodingUtils;
import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;


public class ArticleReadingActivity extends Activity {

    ImageView imageView;
    TextView articleContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_reading);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        TextView articleTitle = (TextView)findViewById(R.id.article_title);
        String title = bundle.getString("title");
        articleTitle.setText(title);
        articleContent = (TextView)findViewById(R.id.article_content);
        imageView = (ImageView)findViewById(R.id.article_image);


        setImageViewSrc(bundle.getString("value") + "pic.jpg");
        setArticleSrc(bundle.getString("value") + ".txt");
    }

    public void setArticleSrc(String link) {
        try {
            FileInputStream fin = openFileInput(link);
            //获取文件长度
            int length = fin.available();
            byte[] in = new byte[length];
            fin.read(in);
            articleContent.setText(EncodingUtils.getString(in, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setImageViewSrc(String link) {
        try {
            FileInputStream fin = openFileInput(link);
            //获取文件长度
            int length = fin.available();
            byte[] in = new byte[length];
            fin.read(in);
            BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(new ByteArrayInputStream(in), true);
            Bitmap bitmap = decoder.decodeRegion(new Rect(0, 0, decoder.getWidth(), decoder.getHeight()), null);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.article_reading, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
