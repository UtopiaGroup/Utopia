package com.Utopia.utopia.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;


public class CalendarActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        final CalendarView calendarView = (CalendarView)findViewById(R.id.calendar_view);
        Intent callerIntent = getIntent();
        long currentTime = callerIntent.getLongExtra("currentTime",0);
        final long millis = TimeUtil.getMillisFromTime(currentTime);
        calendarView.setDate(millis);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(millis));
        final int currentYear = cal.get(Calendar.YEAR);
        final int currentMonth = cal.get(Calendar.MONTH);
        final int currentDayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                long changedMillis = view.getDate();
                if(currentYear != year || currentMonth != month || currentDayOfMonth != dayOfMonth) {
                    Intent intent = new Intent(CalendarActivity.this,MainActivity.class);
                    intent.putExtra("millis", changedMillis);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
