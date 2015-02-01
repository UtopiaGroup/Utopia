package com.Utopia.utopia.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by Joe on 14-9-7.
 */
public class EveryDayPushView extends RelativeLayout {

    TextView textView;
    ImageView imageView;
    View view;
    public EveryDayPushView(Context context) {
        super(context);
        init();
    }

    public EveryDayPushView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EveryDayPushView(Context context, Bundle map) {
        super(context);
        init();
        final Bundle mMap = map;
        setText(String.valueOf(map.get("title")));
        setImageViewSrc(map.getByteArray("edpv"));
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ArticleReadingActivity.class);
                intent.putExtras(mMap);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        view = inflater.inflate(R.layout.every_day_push_view, this, true);
        imageView = (ImageView) view.findViewById(R.id.every_day_push_view_image_view);
        textView = (TextView) view.findViewById(R.id.every_day_push_view_text_view);
    }

    public void setText(String string) {
        textView.setText(string);
    }

    public void setImageViewSrc(byte[] in) {
        try {
            BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(new ByteArrayInputStream(in), true);
            Bitmap bitmap = decoder.decodeRegion(new Rect(0, 0, decoder.getWidth(), decoder.getHeight()), null);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
