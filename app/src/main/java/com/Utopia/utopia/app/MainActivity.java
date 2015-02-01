package com.Utopia.utopia.app;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;

import com.Utopia.utopia.app.SQL.DataProviderMetaData;

import org.apache.http.util.EncodingUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity {

    public static final int KIND_SCHEDULE = DataProviderMetaData.DataTableMetaData.KIND_SCHEDULE;
    public static final int KIND_TIP = DataProviderMetaData.DataTableMetaData.KIND_TIP;
    public static final int KIND_ADVERTISE = DataProviderMetaData.DataTableMetaData.KIND_ADVERTISE;
    ContentResolver cr;

    private void getOverflowMenu() {

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.activity_main);
        //用于永远显示3个点
        getOverflowMenu();

        cr = getContentResolver();

        Boolean isFirstIn = false;
        SharedPreferences pref = getSharedPreferences("Utopia", 0);
        //取得相应的值，如果没有该值，说明还未写入，用true作为默认值
        isFirstIn = pref.getBoolean("isFirstIn", true);

        if (isFirstIn) {
            initAdvertise();
            initTip();

            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isFirstIn", false);
            editor.commit();
        }

        PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pagertab);
        pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.gold));
        pagerTabStrip.setDrawFullUnderline(true);
        pagerTabStrip.setBackgroundColor(getResources().getColor(R.color.azure));
        pagerTabStrip.setTextSpacing(50);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
        fragmentList = new ArrayList<Fragment>();//ViewPager中显示的数据
        ArrayList<String> titleList = new ArrayList<String>();// 标题数据
        //添加数据
        fragmentList.add(new ViewPagerFragment0());
        fragmentList.add(new ViewPagerFragment1());
        fragmentList.add(new ViewPagerFragment2());
        fragmentList.add(new ViewPagerFragment3());

        titleList = new ArrayList<String>();// 每个页面的Title数据
        titleList.add(getResources().getString(R.string.page0_title));
        titleList.add(getResources().getString(R.string.page1_title));
        titleList.add(getResources().getString(R.string.page2_title));
        titleList.add(getResources().getString(R.string.page3_title));


        mViewPager.setAdapter(new MyPagerFragmentAdapter(
                getSupportFragmentManager(), fragmentList, titleList));

        mViewPager.setCurrentItem(2);
        mViewPager.setOffscreenPageLimit(4);
    }


    void initAdvertise() {
        long beginTime = 20150110120000L;
        long endTime = 20150211120000L;
        long day = 1000000L;
        int loop = 3;
        try {
            Bitmap source = BitmapFactory.decodeResource(getResources(), R.raw.data1pic);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            source.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] buffer = byteArrayOutputStream.toByteArray();
            long kind = KIND_ADVERTISE;
            String title = "仙人球满身刺_养生美容呵护您";

            InputStream in = getResources().openRawResource(R.raw.data1);
            int length = in.available();
            byte[] buffer2 = new byte[length];
            in.read(buffer2);
            String value = EncodingUtils.getString(buffer2, "UTF-8");
            Log.i("value",value);
            in.close();

            for (long begin = beginTime;begin <endTime;begin += day*loop) {
                ContentValues cv = new ContentValues();
                cv.put("begin", begin);
                cv.put("kind", kind);
                cv.put("edpv", buffer);
                cv.put("title", title);
                cv.put("value",value);
                cr.insert(DataProviderMetaData.DataTableMetaData.CONTENT_URI, cv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Bitmap source = BitmapFactory.decodeResource(getResources(), R.raw.data2pic);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            source.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] buffer = byteArrayOutputStream.toByteArray();

            long kind = KIND_ADVERTISE;
            String title = "高跟鞋女人的养生美容法";
            InputStream in = getResources().openRawResource(R.raw.data2);
            int length = in.available();
            byte[] buffer2 = new byte[length];
            in.read(buffer2);
            String value = EncodingUtils.getString(buffer2, "UTF-8");
            in.close();
            for (long begin = beginTime;begin <endTime;begin += day*loop) {
                ContentValues cv = new ContentValues();
                cv.put("begin", begin);
                cv.put("kind", kind);
                cv.put("edpv", buffer);
                cv.put("title", title);
                cv.put("value",value);
                cr.insert(DataProviderMetaData.DataTableMetaData.CONTENT_URI, cv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Bitmap source = BitmapFactory.decodeResource(getResources(), R.raw.data3pic);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            source.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] buffer = byteArrayOutputStream.toByteArray();

            long kind = KIND_ADVERTISE;
            String title = "保护好双脚_老中医十个养肾妙招";
            InputStream in = getResources().openRawResource(R.raw.data3);
            int length = in.available();
            byte[] buffer2 = new byte[length];
            in.read(buffer2);
            String value = EncodingUtils.getString(buffer2, "UTF-8");
            in.close();
            for (long begin = beginTime+day;begin <endTime;begin += day*loop) {
                ContentValues cv = new ContentValues();
                cv.put("begin", begin);
                cv.put("kind", kind);
                cv.put("edpv", buffer);
                cv.put("title", title);
                cv.put("value",value);
                cr.insert(DataProviderMetaData.DataTableMetaData.CONTENT_URI, cv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Bitmap source = BitmapFactory.decodeResource(getResources(), R.raw.data4pic);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            source.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] buffer = byteArrayOutputStream.toByteArray();

            long kind = KIND_ADVERTISE;
            String title = "祛痘佳品_柚子脐橙可养生美容";
            InputStream in = getResources().openRawResource(R.raw.data4);
            int length = in.available();
            byte[] buffer2 = new byte[length];
            in.read(buffer2);
            String value = EncodingUtils.getString(buffer2, "UTF-8");
            in.close();
            for (long begin = beginTime+day;begin <endTime;begin += day*loop) {
                ContentValues cv = new ContentValues();
                cv.put("begin", begin);
                cv.put("kind", kind);
                cv.put("edpv", buffer);
                cv.put("title", title);
                cv.put("value",value);
                cr.insert(DataProviderMetaData.DataTableMetaData.CONTENT_URI, cv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Bitmap source = BitmapFactory.decodeResource(getResources(), R.raw.data5pic);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            source.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] buffer = byteArrayOutputStream.toByteArray();

            long kind = KIND_ADVERTISE;
            String title = "西瓜皮的营养成分和美容作用";
            InputStream in = getResources().openRawResource(R.raw.data5);
            int length = in.available();
            byte[] buffer2 = new byte[length];
            in.read(buffer2);
            String value = EncodingUtils.getString(buffer2, "UTF-8");
            in.close();
            for (long begin = beginTime+2*day;begin <endTime;begin += day*loop) {
                ContentValues cv = new ContentValues();
                cv.put("begin", begin);
                cv.put("kind", kind);
                cv.put("edpv", buffer);
                cv.put("title", title);
                cv.put("value",value);
                cr.insert(DataProviderMetaData.DataTableMetaData.CONTENT_URI, cv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Bitmap source = BitmapFactory.decodeResource(getResources(), R.raw.data6pic);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            source.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] buffer = byteArrayOutputStream.toByteArray();

            long kind = KIND_ADVERTISE;
            String title = "98岁老中医给每个职场人的长寿建议";
            InputStream in = getResources().openRawResource(R.raw.data6);
            int length = in.available();
            byte[] buffer2 = new byte[length];
            in.read(buffer2);
            String value = EncodingUtils.getString(buffer2, "UTF-8");
            in.close();
            for (long begin = beginTime+2*day;begin <endTime;begin += day*loop) {
                ContentValues cv = new ContentValues();
                cv.put("begin", begin);
                cv.put("kind", kind);
                cv.put("edpv", buffer);
                cv.put("title", title);
                cv.put("value",value);
                cr.insert(DataProviderMetaData.DataTableMetaData.CONTENT_URI, cv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void initTip() {
        String res = "";
        try {
            InputStream in = getResources().openRawResource(R.raw.tip_month);
            int length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
            res = EncodingUtils.getString(buffer, "UTF-8");
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] lines = res.split("\n");
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

    //适配器
    private class MyPagerFragmentAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> fragmentList;
        private List<String> titleList;

        public MyPagerFragmentAdapter(FragmentManager fragmentManager,
                                      List<Fragment> fragmentList, List<String> titleList) {
            super(fragmentManager);
            this.fragmentList = fragmentList;
            this.titleList = titleList;
        }

        // ViewPage中显示的内容
        @Override
        public Fragment getItem(int arg0) {
            // TODO Auto-generated method stub
            return fragmentList.get(arg0);
        }

        // Title中显示的内容
        @Override
        public CharSequence getPageTitle(int position) {
            // TODO Auto-generated method stub
            return titleList.get(position);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return fragmentList.size();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    final public static int REQUEST_STDENTRY = 1;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_add:
                Intent intent = new Intent(MainActivity.this, STDEntry.class);
                startActivityForResult(intent, REQUEST_STDENTRY);
                break;
            case R.id.action_settings:

                break;
            case R.id.action_account:

                break;
            case R.id.action_recommend:

                break;
            case R.id.action_score:

                break;
            case R.id.action_feedback:

                break;
            case R.id.action_about:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addEvent(Bundle map) {
        ((ViewPagerFragment2) fragmentList.get(2)).addEvent(map);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_STDENTRY:
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bundle map = new Bundle();

                        map.putLong("created", bundle.getLong("created"));
                        map.putLong("modified", bundle.getLong("modified"));
                        map.putString("title", bundle.getString("title"));
                        map.putString("value", bundle.getString("value"));
                        map.putLong("begin", bundle.getLong("begin"));
                        map.putLong("end", bundle.getLong("end"));
                        map.putLong("finish", bundle.getLong("finish"));
                        map.putLong("kind", bundle.getLong("kind"));
                        map.putString("myhint", bundle.getString("myhint"));
                        map.putByteArray("bitmap", bundle.getByteArray("bitmap"));

                        addEvent(map);
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
