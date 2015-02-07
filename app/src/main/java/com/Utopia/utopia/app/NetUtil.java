package com.Utopia.utopia.app;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.Utopia.utopia.app.SQL.DataProviderMetaData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by chenwenxiao on 15-2-5.
 */
public class NetUtil {
    public static final int KIND_SCHEDULE = DataProviderMetaData.DataTableMetaData.KIND_SCHEDULE;
    public static final int KIND_TIP = DataProviderMetaData.DataTableMetaData.KIND_TIP;
    public static final int KIND_ADVERTISE = DataProviderMetaData.DataTableMetaData.KIND_ADVERTISE;
    public static final String SETNAME = "Utopia";

    public static final String WEBHOME = "http://123.57.252.155:3000/";
    public static final String TAG = "NetUtil";

    public ContentResolver cr;
    public SharedPreferences sp;
    public Activity context;

    String old_tipList, old_advertiseList, old_resourceList;

    public NetUtil(Activity context) {
        this.cr = context.getContentResolver();
        this.sp = context.getSharedPreferences(SETNAME, 0);
        this.context = context;
    }

    public void update() {
        old_tipList = sp.getString("tipList", "");
        old_advertiseList = sp.getString("advertiseList", "");
        old_resourceList = sp.getString("resourceList", "");
        getResourceList();
        getAdvertiseList();
        getTipList();
    }

    public void getTipList() {
        String data = getNetTxt("TipList.txt");
        String[] tipList = data.split("\n");
        for (String i : tipList)
            if (!old_tipList.contains("i")) getTip(i);
        sp.edit().putString("tipList", data).apply();
    }

    public void getAdvertiseList() {
        String data = getNetTxt("AdvertiseList.txt");
        String[] advertiseList = data.split("\n");
        for (String i : advertiseList)
            if (!old_advertiseList.contains(i)) getAdvertise(i);
        sp.edit().putString("advertiseList", data).apply();
    }

    public void getResourceList() {
        String data = getNetTxt("ResourceList.txt");
        String[] resourceList = data.split("\n");
        for (String i : resourceList)
            if (!old_resourceList.contains(i))
                getResource(i);
        sp.edit().putString("resourceList", data).apply();
    }

    public void getTip(String link) {
        //获取一个月的Tip
        link = "Tip/" + link + ".txt";
        String data = getNetTxt(link);
        String[] lines = data.split("\n");
        for (int i = 0; i < lines.length; i += 3) {
            long kind = KIND_TIP;
            long begin = Long.parseLong(lines[i]) * 1000000L + 120000L;
            String title = lines[i + 1];

            ContentValues cv = new ContentValues();
            cv.put("begin", begin);
            cv.put("kind", kind);
            cv.put("title", title);

            cr.insert(DataProviderMetaData.DataTableMetaData.CONTENT_URI, cv);

            title = lines[i + 2];

            cv = new ContentValues();
            cv.put("begin", begin);
            cv.put("kind", kind);
            cv.put("title", title);

            cr.insert(DataProviderMetaData.DataTableMetaData.CONTENT_URI, cv);
        }
    }

    public void getAdvertise(String link) {
        //获取一个月的Advertise
        link = "Advertise/" + link + ".txt";
        String data = getNetTxt(link);
        String[] lines = data.split("\n");
        for (int i = 0; i < lines.length; i += 3) {
            long kind = KIND_ADVERTISE;
            long begin = Long.parseLong(lines[i]) * 1000000L + 120000L;

            ContentValues cv = new ContentValues();
            cv.put("begin", begin);
            cv.put("kind", kind);
            cv.put("title", lines[i + 1]);
            cv.put("value", lines[i + 2]);

            cr.insert(DataProviderMetaData.DataTableMetaData.CONTENT_URI, cv);
        }
    }

    public void getResource(String link) {
        //获取一个Advertise
        String txtlink = "Resource/" + link + ".txt";
        String piclink = "Resource/" + link + "pic.jpg";
        try {
            Bitmap source = getNetImg(piclink);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            source.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] buffer = byteArrayOutputStream.toByteArray();
            String value = getNetTxt(txtlink);
            context.openFileOutput(link + ".txt", Context.MODE_PRIVATE).write(value.getBytes());
            context.openFileOutput(link + "pic.jpg", Context.MODE_PRIVATE).write(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getNetTxt(String link) {
        String result = null;
        link = WEBHOME + link;
        try {
            byte[] data = getByte(link);
            result = new String(data, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Bitmap getNetImg(String link) {
        Bitmap result = null;
        link = WEBHOME + link;
        try {
            byte[] data = getByte(link);
            result = BitmapFactory.decodeByteArray(data, 0, data.length);  //生成位图
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static byte[] getByte(String link) throws IOException {
        URL url = new URL(link);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");   //设置请求方法为GET
        conn.setReadTimeout(5*1000);    //设置请求过时时间为5秒
        InputStream inputStream = conn.getInputStream();   //通过输入流获得数据
        byte[] data = readInputStream(inputStream);     //获得二进制数据
        return data;
    }

    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
}
