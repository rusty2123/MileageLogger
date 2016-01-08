package com.gsandchase.mileagelogger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Date;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    Random random = new Random();

    TextView totalMileage;
    Button addMileageButton;
    EditText newMileage;
    TextView insult;

    MileageCounter mileageCounter;
    MileageLog mileageLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final String[] insults = {
                getApplicationContext().getString(R.string.insult1), getApplicationContext().getString(R.string.insult2),
                getApplicationContext().getString(R.string.insult3), getApplicationContext().getString(R.string.insult4),
                getApplicationContext().getString(R.string.insult5), getApplicationContext().getString(R.string.insult6),
                getApplicationContext().getString(R.string.insult7), getApplicationContext().getString(R.string.insult8),
                getApplicationContext().getString(R.string.insult9), getApplicationContext().getString(R.string.insult10),
                getApplicationContext().getString(R.string.insult11), getApplicationContext().getString(R.string.insult12)};

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        totalMileage = (TextView) findViewById(R.id.totalMileage);
        addMileageButton = (Button) findViewById(R.id.addMileageButton);
        newMileage = (EditText) findViewById(R.id.newMileage);
        insult = (TextView) findViewById(R.id.insult);

        insult.setVisibility(View.GONE);


        // TODO: Read in saved mileage data
        mileageLog = new MileageLog(getApplicationContext());

        //mileageLog.clearFile();

        System.out.println("File contents: " + mileageLog.readFromFile());

        mileageCounter = new MileageCounter(mileageLog.readFromFile());

        mileageCounter.printMap();

        updateCounterText();


        addMileageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Date now = new Date();
                mileageCounter.addMileage(now, Double.parseDouble(newMileage.getText().toString()));
                updateCounterText();
                newMileage.setText("");
                insult.setText(insults[random.nextInt(12)]);
                insult.setVisibility(View.VISIBLE);
                mileageLog.writeJsonToFile(mileageCounter.getMilesJSON_S());
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
        totalMileage.setText(String.format("%1$.1f", mileageCounter.getWeeklyMileage(now)));

        System.out.println("Weekly Mileage: " + mileageCounter.getWeeklyMileage(now));
    }


    private void clearMileage() {
        mileageCounter.clear();
        updateCounterText();
    }

}
