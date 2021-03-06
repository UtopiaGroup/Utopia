package com.Utopia.utopia.app;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity {

    public static final int KIND_SCHEDULE = DataProviderMetaData.DataTableMetaData.KIND_SCHEDULE;
    public static final int KIND_TIP = DataProviderMetaData.DataTableMetaData.KIND_TIP;
    public static final int KIND_ADVERTISE = DataProviderMetaData.DataTableMetaData.KIND_ADVERTISE;
    public static final String SETNAME = "Utopia";

    ContentResolver cr;
    ViewPagerFragment2 viewPagerFragment2;

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
        SharedPreferences pref = getSharedPreferences(SETNAME, 0);
        //取得相应的值，如果没有该值，说明还未写入，用true作为默认值
        isFirstIn = pref.getBoolean("isFirstIn", true);

        if (isFirstIn) {
            pref.edit().putBoolean("isFirstIn", false).apply();
        }
        Thread netThread = new Thread() {
            public void run() {
                new NetUtil(MainActivity.this).update();
            }
        };
        netThread.start();

        //停止加载主线程，等待数据下载（可能要动画）
        try {
            netThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
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

        viewPagerFragment2 = ((ViewPagerFragment2)fragmentList.get(2));

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
    final public static int REQUEST_CALENDAR = 2;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_add:
                Intent intent_STDEntry = new Intent(MainActivity.this, STDEntry.class);
                startActivityForResult(intent_STDEntry, REQUEST_STDENTRY);
                break;
            case R.id.action_calendar:
                Intent intent_Calendar = new Intent(MainActivity.this,CalendarActivity.class);
                intent_Calendar.putExtra("currentTime",viewPagerFragment2.getCurrentTime());
                startActivityForResult(intent_Calendar,REQUEST_CALENDAR);
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

    public void updateEvent(Bundle map) {
        ((ViewPagerFragment2) fragmentList.get(2)).updateEvent(map);
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
                        map.putLong("call", bundle.getLong("call"));

                        addEvent(map);
                    }
                    break;
                case REQUEST_CALENDAR:
                    long millis = data.getLongExtra("millis",0);
                    long selectedTime = TimeUtil.getTimeFromMillis(millis);
                    Log.v("returned date",""+selectedTime);

                    viewPagerFragment2.setTime(selectedTime);
                    break;
                default:
                    break;
            }
        } else if (resultCode == RESULT_FIRST_USER) {
            switch (requestCode) {
                case REQUEST_STDENTRY:
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bundle map = new Bundle();

                        map.putLong("_id", bundle.getLong("_id"));
                        map.putLong("created", bundle.getLong("created"));
                        map.putLong("modified", bundle.getLong("modified"));
                        map.putString("title", bundle.getString("title"));
                        map.putString("value", bundle.getString("value"));
                        map.putLong("begin", bundle.getLong("begin"));
                        map.putLong("end", bundle.getLong("end"));
                        map.putLong("finish", bundle.getLong("finish"));
                        map.putLong("kind", bundle.getLong("kind"));
                        map.putLong("call", bundle.getLong("call"));

                        updateEvent(map);
                    }
                    break;
                default:
                    break;
            }
        }

    }

}
