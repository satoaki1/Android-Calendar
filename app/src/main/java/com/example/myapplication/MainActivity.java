package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView currentMonthTextView;
    private Calendar currentCalendar;
    private GridView calendarGridView;
    private final SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentCalendar = Calendar.getInstance();

        currentMonthTextView = findViewById(R.id.currentMonthTextView);
        calendarGridView = findViewById(R.id.calendarGridView);
        Button prevMonthButton = findViewById(R.id.prevMonthButton);
        Button nextMonthButton = findViewById(R.id.nextMonthButton);

        loadMonth();

        prevMonthButton.setOnClickListener(v -> {
            currentCalendar.add(Calendar.MONTH, -1);
            loadMonth();
        });

        nextMonthButton.setOnClickListener(v -> {
            currentCalendar.add(Calendar.MONTH, 1);
            loadMonth();
        });
    }

    private void loadMonth() {
        currentMonthTextView.setText(monthFormat.format(currentCalendar.getTime()));

        List<Date> dates = new ArrayList<>();
        Calendar monthCalendar = (Calendar) currentCalendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth);

        int maxDaysInMonth = currentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) + firstDayOfMonth;

        while (dates.size() < maxDaysInMonth) {
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        calendarGridView.setAdapter(new CalendarAdapter(this, dates));
    }
}
