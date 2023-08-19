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

    // List of dates to be displayed on the calendar
    private final List<Date> dates;

    // Context object, used to get application-specific resources and services
    private final Context context;

    // Current month's Calendar object
    private final Calendar currentMonthDate;

    // Constructor for the CalendarAdapter class
    public CalendarAdapter(Context context, List<Date> dates) {
        // Set the context and dates from the parameters
        this.context = context;
        this.dates = dates;

        // Initialize the current month's Calendar object and set it to today's date
        this.currentMonthDate = Calendar.getInstance();
        this.currentMonthDate.set(LocalDate.now().getYear(), getMonth(LocalDate.now().getMonth()), LocalDate.now().getDayOfMonth());
    }

    // Convert the provided Month enum to a corresponding integer value
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

    // Get the total count of dates to be displayed
    @Override
    public int getCount() {
        return dates.size();
    }

    // Get the Date object at the specified position
    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }

    // Return the position as the item ID
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Try to reuse an existing view (convertView). If it doesn't exist, create a new TextView.
        TextView dayView = (TextView) convertView;
        if (dayView == null) {
            dayView = new TextView(context);
        }

        // Get the date for the current position
        Date date = (Date) getItem(position);

        // Convert the Date to a Calendar object for easier manipulation
        Calendar dayDate = Calendar.getInstance();
        dayDate.setTime(date);

        // Get the day number from the date (e.g., 1, 2, 3,...31) and set it to the TextView
        int dayNumber = dayDate.get(Calendar.DAY_OF_MONTH);
        dayView.setText(String.valueOf(dayNumber));

        // Customize the appearance of the TextView based on the date
        customizeDayView(dayView, dayDate);

        // Return the customized TextView
        return dayView;
    }

    public void customizeDayView(View dayView, Calendar dayDate) {
        // Get the day of the week for the given date (e.g., Sunday, Monday,...Saturday)
        int dayOfWeek = dayDate.get(Calendar.DAY_OF_WEEK);

        // If the day is Saturday, set its background to blue and exit the method
        if (dayOfWeek == Calendar.SATURDAY) {
            dayView.setBackgroundColor(Color.BLUE);
            return;
        }
        // If the day is Sunday, set its background to red and exit the method
        else if (dayOfWeek == Calendar.SUNDAY) {
            dayView.setBackgroundColor(Color.RED);
            return;
        }

        // Check if the date is from a month other than the current month. If so, set its background to gray.
        // For example, if displaying January and a date from December or February is shown, it will be grayed out.
        if (dayDate.get(Calendar.MONTH) < currentMonthDate.get(Calendar.MONTH) ||
                dayDate.get(Calendar.MONTH) > currentMonthDate.get(Calendar.MONTH)) {
            dayView.setBackgroundColor(Color.GRAY);
        }
        // If the date is from the current month, set its background to white.
        else {
            dayView.setBackgroundColor(Color.WHITE);
        }
    }
}
