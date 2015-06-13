package com.supersmashcoders.backtobackhackathon;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.supersmashcoders.backtobackhackathon.enums.Tag;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class CreateActivity extends ActionBarActivity {

    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        final EditText startDate = (EditText) findViewById(R.id.input_start_date);
        startDate.setOnClickListener(getDatePickerListener(startDate));

        EditText endDate = (EditText) findViewById(R.id.input_end_date);
        endDate.setOnClickListener(getDatePickerListener(endDate));

        Spinner tagSpinner = (Spinner) findViewById(R.id.input_tag);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Tag.displayNames());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tagSpinner.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private View.OnClickListener getDatePickerListener(final EditText exitText) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateActivity.this,
                        new DatePickerListener(myCalendar, exitText),
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        };
    }

    private static class DatePickerListener implements DatePickerDialog.OnDateSetListener {
        private Calendar calendar;
        private EditText editTextDisplay;

        public DatePickerListener(Calendar calendar, EditText editTextDisplay) {
            this.calendar = calendar;
            this.editTextDisplay = editTextDisplay;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

        private void updateLabel() {
            String myFormat = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            editTextDisplay.setText(sdf.format(calendar.getTime()));
        }
    };
}
