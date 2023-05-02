package com.example.calendar_0502;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private TextView startDateTextView;
    private TextView endDateTextView;
    private int selectedYear, selectedMonth, selectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startDateTextView = findViewById(R.id.startDateTextView);
        endDateTextView = findViewById(R.id.endDateTextView);

        startDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(true);
            }
        });

        endDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(false);
            }
        });
    }

    private void showDatePickerDialog(boolean isStartDate) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                year,
                month,
                day
        );

        if (!isStartDate) {
            datePickerDialog.getDatePicker().setMinDate(getDateInMillis(startDateTextView.getText().toString()));
        }

        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        selectedYear = year;
        selectedMonth = month;
        selectedDay = dayOfMonth;

        String date = String.format(Locale.getDefault(), "%d-%02d-%02d", year, month + 1, dayOfMonth);

        if (startDateTextView.isFocused()) {
            startDateTextView.setText(date);
        } else if (endDateTextView.isFocused()) {
            endDateTextView.setText(date);
        }
    }

    private long getDateInMillis(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date date = sdf.parse(dateString);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void onPeriodSetButtonClick(View view) {
        long startDateInMillis = getDateInMillis(startDateTextView.getText().toString());
        long endDateInMillis = getDateInMillis(endDateTextView.getText().toString());
        long diffInMillis = endDateInMillis - startDateInMillis;
        long daysDiff = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);

        Toast.makeText(this, "Period selected: " + daysDiff + " days", Toast.LENGTH_SHORT).show();
    }
}
