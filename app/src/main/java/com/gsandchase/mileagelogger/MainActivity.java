package com.gsandchase.mileagelogger;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    TextView totalMileage;
    Button addMileageButton;
    EditText newMileage;

    MileageCounter mileageCounter;

    // private String fileName = "mileageData";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        totalMileage = (TextView) findViewById(R.id.totalMileage);
        addMileageButton = (Button) findViewById(R.id.addMileageButton);
        newMileage = (EditText) findViewById(R.id.newMileage);

        // TODO: Read in saved mileage data
        mileageCounter = new MileageCounter();


        updateCounterText();


        addMileageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Date now = new Date();
                mileageCounter.addMileage(now, Double.parseDouble(newMileage.getText().toString()));
                updateCounterText();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO: Handle selection of different counters

        int id = item.getItemId();

        if (id == R.id.action_menu) {
            return true;
        }
        else if (id == R.id.menu_clear) {
            clearMileage();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateCounterText() {
        Date now = new Date();
        totalMileage.setText(String.format("%1$.2f", mileageCounter.getWeeklyMileage(now)));
    }


    private void clearMileage() {
        mileageCounter.clear();
        updateCounterText();
    }
}
