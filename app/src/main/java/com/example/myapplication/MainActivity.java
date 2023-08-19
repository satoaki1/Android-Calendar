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

    // TextView to display the current month and days in the month
    private TextView currentMonthTextView;

    // Calendar object to track the current month and days in the month
    private Calendar currentCalendar;

    // GridView to display calendar's dates
    private GridView calendarGridView;

    // SimpleDateFormat to format current month and year
    private final SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());


    /**
     * @author Satoaki Ishihara
     * This is the method that would be implemented in default when the application is launched.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout for this activity (defined in "activity_main.xml")
        setContentView(R.layout.activity_main);

        // Initialize the currentCalendar variable with the current date and time
        currentCalendar = Calendar.getInstance();

        // Find and assign each items from the layout by Id
        // - Find and assign the TextView that displays the current month
        // - Find and assign the GridView that will display the days of the month
        // - Find and assign the Button that will move to the previous month when clicked
        // - Find and assign the Button that will move to the next month when clicked
        currentMonthTextView = findViewById(R.id.currentMonthTextView);
        calendarGridView = findViewById(R.id.calendarGridView);
        Button prevMonthButton = findViewById(R.id.prevMonthButton);
        Button nextMonthButton = findViewById(R.id.nextMonthButton);

        // Load the current month's dates and display them in the GridView
        loadMonth();

        // Once Prev button is clicked, display the month which value is -1 from current month
        prevMonthButton.setOnClickListener(v -> {
            currentCalendar.add(Calendar.MONTH, -1);
            loadMonth();
        });

        // Once Next button is clicked, display the month which value is +1 from current month
        nextMonthButton.setOnClickListener(v -> {
            currentCalendar.add(Calendar.MONTH, 1);
            loadMonth();
        });
    }

    /**
     * @author Satoaki Ishihara
     * This is the method to load the current month's dates and display them in the GridView,
     * in onCreate() method written above.
     */
    private void loadMonth() {
        // Set the text of the current month and year to the TextView
        currentMonthTextView.setText(monthFormat.format(currentCalendar.getTime()));

        // Create a list to hold dates for the current month view
        List<Date> dates = new ArrayList<>();

        // Clone the current calendar to avoid modifying the original
        Calendar monthCalendar = (Calendar) currentCalendar.clone();

        // Set the cloned calendar to the 1st day of the month
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1);

        // Calculate the day of the week for the 1st day (0 = Sunday, 1 = Monday, etc.)
        int firstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1;

        // Move the calendar back to the start of the week (could be a day from the previous month)
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth);

        // Calculate days needed at the end of the month to make the last row contain 7 days
        int daysInCurrentMonth = currentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int daysNeededAtEnd = 7 - ((daysInCurrentMonth + firstDayOfMonth) % 7);

        // Adjust if it's already a complete week
        if (daysNeededAtEnd == 7) {
            daysNeededAtEnd = 0;
        }

        int totalDays = daysInCurrentMonth + firstDayOfMonth + daysNeededAtEnd; // Total nodes to show in the grid

        // Fill the dates list with days for the month view
        while (dates.size() < totalDays) {
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);  // Move to the next day
        }

        // Set the GridView's adapter with the list of dates
        calendarGridView.setAdapter(new CalendarAdapter(this, dates));
    }
}
