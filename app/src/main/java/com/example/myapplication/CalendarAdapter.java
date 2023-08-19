package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarAdapter extends BaseAdapter {
    private final List<Date> dates;
    private final Context context;
    private final Calendar currentMonthDate;
//    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd", Locale.getDefault());

    public CalendarAdapter(Context context, List<Date> dates) {
        this.context = context;
        this.dates = dates;
        this.currentMonthDate = Calendar.getInstance();
        this.currentMonthDate.set(LocalDate.now().getYear(), getMonth(LocalDate.now().getMonth()), LocalDate.now().getDayOfMonth());
    }

    public int getMonth(Month currentMonth) {
        int month = 0;
        switch (currentMonth) {
            case JANUARY:
                month = Calendar.JANUARY;
                break;
            case FEBRUARY:
                month = Calendar.FEBRUARY;
                break;
            case MARCH:
                month = Calendar.MARCH;
                break;
            case APRIL:
                month = Calendar.APRIL;
                break;
            case MAY:
                month = Calendar.MAY;
                break;
            case JUNE:
                month = Calendar.JUNE;
                break;
            case JULY:
                month = Calendar.JULY;
                break;
            case AUGUST:
                month = Calendar.AUGUST;
                break;
            case SEPTEMBER:
                month = Calendar.SEPTEMBER;
                break;
            case OCTOBER:
                month = Calendar.OCTOBER;
                break;
            case NOVEMBER:
                month = Calendar.NOVEMBER;
                break;
            case DECEMBER:
                month = Calendar.DECEMBER;
                break;
        }
        return month;
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView dayView = (TextView) convertView;
        if (dayView == null) {
            dayView = new TextView(context);
        }

        Date date = (Date) getItem(position);
        Calendar dayDate = Calendar.getInstance();
        dayDate.setTime(date);

        // Set day number as text
        int dayNumber = dayDate.get(Calendar.DAY_OF_MONTH);
        dayView.setText(String.valueOf(dayNumber));

        customizeDayView(dayView, dayDate);

        return dayView;
    }

    public void customizeDayView(View dayView, Calendar dayDate) {
        int dayOfWeek = dayDate.get(Calendar.DAY_OF_WEEK);

        // Check if day is Saturday or Sunday first
        if (dayOfWeek == Calendar.SATURDAY) {
            dayView.setBackgroundColor(Color.BLUE);
            return; // exit the method after setting the color
        } else if (dayOfWeek == Calendar.SUNDAY) {
            dayView.setBackgroundColor(Color.RED);
            return; // exit the method after setting the color
        }

        // Check if day is from previous, current, or next month
        if (dayDate.get(Calendar.MONTH) < currentMonthDate.get(Calendar.MONTH) ||
                dayDate.get(Calendar.MONTH) > currentMonthDate.get(Calendar.MONTH)) {
            dayView.setBackgroundColor(Color.GRAY);
        } else {
            dayView.setBackgroundColor(Color.WHITE);
        }
    }
}
