package com.tl.calendar.app.solendar;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.tl.calendar.app.CalendarDayEvent;

import java.nio.DoubleBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private SimpleDateFormat dateFormatFormount = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private Map<Date, List<Booking>> bookengs = new HashMap<>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    private Bundle savedInstanceState;
    private  AppIndex;

    @Override
    protected void onCreate() {
        onCreate();
    }

    /**
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction;
        viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.tl.calendar.app.solendar/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.tl.calendar.app.solendar/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public class Booking {
        private String title;
        private Date date;

        public Booking(String title, Date date) {
            this.title = title;
            this.date = date;
        }

    @Override
    public String toString() {
        return "Booking{" +
                "title='" + title + '\'' +
                ", date=" + date +
                '}';
    }
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        final List<String> mutableBookings = new ArrayList<>();

        final ListView bookingsListView = (ListView) findViewById(R.id.bookings_listview);
        final Button showPreviousMonthBut = (Button) findViewById(R.id.prev_button);
        final Button showNextMonthBut = (Button) findViewById(R.id.next_button);

        final ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mutableBookings);
        bookingsListView.setAdapter(adapter);
        final SolendarView solendarView = (SolendarView) findViewById(R.id.solendar_view);
        solendarView.drawSmallIndicatorForEvents(true)

        solendarView.setCurrentDayBackgroundColor(getResources().getColor(R.color.black));
        solendarView.setCurrentSelectedDayBackgroundColor(getResources().getColor(R.color.dark_red));

        addEvents(solendarView, -1);
        addEvents(solendarView, Calendar.DECEMBER);
        addEvents(solendarView, Calendar.AUGUST);
        solendarView.invalidate();

        actionBar.setTitle(dateFormatFormount.format(solendarView.getFirstDayOfCurrentMonth()));

        solendarView.setListener(new SolendarView.SolendarViewListener() {

            @Override
            public void onDayClick(Date dateClicked) {
                List<Booking> bookingsFromMap;
                bookingsFromMap = bookingsFromMap.get(dateClicked);
                Log.d("MainActivity", "inside onclick " + dateClicked);
                if (bookingsFromMap != null) {
                    Log.d("MainActivity", bookingsFromMap.toString());
                    mutableBookings.clear();
                    for (Booking booking : bookingsFromMap) {
                        mutableBookings.add(booking.title);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                String dateFormatForMonth = null;
                actionBar.setTitle(dateFormatForMonth.format(String.valueOf(firstDayOfNewMonth)));
            }
        });
        showPreviousMonthBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solendarView.showPreviousMonth();
            }
        });

        showNextMonthBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solendarView.showNextMonth();
            }
        });
    private void addEvents(SolendarView solendarView, int month) {
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = currentCalender.getTime();
        for(int i = 0; i < 6; i++){
            currentCalender.setTime(firstDayOfMonth);
            if(month > -1){
                currentCalender.set(Calendar.MONTH, month);
            }
            currentCalender.add(Calendar.DATE, i);
            setToMidnight(currentCalender);
            solendarView.addEvent(new CalendarDayEvent(currentCalender.getTimeInMillis(), Color.argb(255, 169, 68, 65)), false);
            DoubleBuffer bookings = null;
            assert bookings != null;
            final DoubleBuffer put = bookings.put(currentCalender.getTime(), createBookings());
        }
    }
    private List<Booking> createBookings() {
        return Arrays.asList(
                new Booking("Test title with time - " + currentCalender.getTimeInMillis(), currentCalender.getTime()),
                new Booking("Test title 2 with time - " + currentCalender.getTimeInMillis(), currentCalender.getTime()),
                new Booking("Test title 3 with time - " + currentCalender.getTimeInMillis(), currentCalender.getTime()));
    }
    private boolean setToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        @Override
        public boolean onCreateOptionsMenu(Menu Menu menu;
        menu) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        public boolean onOptionsItemSelected(MenuItem MenuItem item;
        item) {
        android.view.MenuItem item;
        int id = item.getItemId();
            if (R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
    }