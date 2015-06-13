package com.supersmashcoders.backtobackhackathon;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.supersmashcoders.backtobackhackathon.converters.DateConverter;
import com.supersmashcoders.backtobackhackathon.enums.Tag;
import com.supersmashcoders.backtobackhackathon.models.EventModel;
import com.supersmashcoders.backtobackhackathon.proxy.EventProxy;
import com.supersmashcoders.backtobackhackathon.proxy.RequestListener;

import java.util.Calendar;


public class CreateActivity extends ActionBarActivity {

    private EventProxy mEventProxy;
    private Calendar myCalendar;

    private EditText mName;
    private EditText mDescription;
    private EditText mStartDate;
    private EditText mEndDate;
    private String mSelectedTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        mEventProxy = new EventProxy();
        myCalendar = Calendar.getInstance();

        mName = (EditText) findViewById(R.id.input_title);

        mDescription = (EditText) findViewById(R.id.input_description);

        mStartDate = (EditText) findViewById(R.id.input_start_date);
        mStartDate.setOnClickListener(getDatePickerListener(mStartDate));

        mEndDate = (EditText) findViewById(R.id.input_end_date);
        mEndDate.setOnClickListener(getDatePickerListener(mEndDate));

        Spinner tagSpinner = (Spinner) findViewById(R.id.input_tag);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Tag.displayNames());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tagSpinner.setAdapter(adapter);
        tagSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedTag = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button submitButton = (Button) findViewById(R.id.button_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEvent();
            }
        });
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
            editTextDisplay.setText(DateConverter.toString(calendar.getTime(), DateConverter.DateFormat.DATE_FORMAT));
        }
    };

    private void createEvent() {
        EventModel model = EventModel.of(
                mName.getText().toString(),
                mDescription.getText().toString(),
                DateConverter.toDate(mStartDate.getText().toString(), DateConverter.DateFormat.DATE_FORMAT),
                DateConverter.toDate(mEndDate.getText().toString(), DateConverter.DateFormat.DATE_FORMAT),
                Tag.fromDisplayName(mSelectedTag)
        );

        mEventProxy.create(this, model, new RequestListener<EventModel>() {
            @Override
            public void onComplete(EventModel object) {
                Toast.makeText(CreateActivity.this, "Created", Toast.LENGTH_SHORT);
            }

            @Override
            public void onError() {

            }
        });
    }
}
