package com.Utopia.utopia.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


public class ArticleReadingActivity extends Activity {

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
        TextView articleContent = (TextView)findViewById(R.id.article_content);
        String value = bundle.getString("value");
        articleContent.setText(value);
        ImageView imageView = (ImageView)findViewById(R.id.article_image);
        byte[] edpv = bundle.getByteArray("edpv");
        Bitmap bitmap = BitmapFactory.decodeByteArray(edpv,0,edpv.length);
        imageView.setImageBitmap(bitmap);


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
