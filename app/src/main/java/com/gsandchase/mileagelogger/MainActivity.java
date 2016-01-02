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
    Context context;

    private float weeklyMileage = 0, monthlyMileage = 0, annualMileage = 0;
    private String weeklyMileageS, monthlyMileageS, annualMileageS;
    private String fileName = "mileageData";
    private Date DATE = new Date();
    private String DATE_S = DATE.toString();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        totalMileage = (TextView) findViewById(R.id.totalMileage);
        addMileageButton = (Button) findViewById(R.id.addMileageButton);
        newMileage = (EditText) findViewById(R.id.newMileage);


        readMileage();

        if(newWeek(DATE)) {
            weeklyMileage = 0;
            weeklyMileageS = "0";
        }

        totalMileage.setText(weeklyMileageS);


        addMileageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                writeMileage();
                readMileage();
                totalMileage.setText(weeklyMileageS);
                newMileage.setText("");

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


    private void clearMileage()
    {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(fileName, MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            fos.write("".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        weeklyMileage = 0; monthlyMileage = 0; annualMileage = 0;

        weeklyMileageS = String.format("%.2f", weeklyMileage);
        monthlyMileageS = String.format("%.2f", monthlyMileage);
        annualMileageS = String.format("%.2f", annualMileage);

        totalMileage.setText(weeklyMileageS);
    }

    private void writeMileage()
    {
        String data = "";
        String delimeter = " : ";

        float mileage;

        if(newMileage.getText().toString() == "")
            mileage = 0;

        else
            mileage = Float.parseFloat(newMileage.getText().toString());


        setMileages(mileage, weeklyMileage, monthlyMileage, annualMileage);

        try {

            FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE);

            fos.write("".getBytes());
            fos.flush();

            data = weeklyMileageS + delimeter + DATE_S;

            System.out.println("data: " + data);

            fos.write(data.getBytes());

            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setMileages(float newMileage, float weeklyMileage, float monthlyMileage, float annualMileage){

        weeklyMileage += newMileage;
        monthlyMileage += newMileage;
        annualMileage += newMileage;

        weeklyMileageS = String.format("%.2f", weeklyMileage);
        monthlyMileageS = String.format("%.2f", monthlyMileage);
        annualMileageS = String.format("%.2f", annualMileage);

    }

    private void readMileage() {

        FileInputStream fis = null;
        String data = "";

        if (!getFileStreamPath(fileName).exists()) {}

        else {
            try {
                fis = openFileInput(fileName);
                InputStreamReader sr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(sr);
                StringBuffer sb = new StringBuffer();

                while ((data = br.readLine()) != null) {
                    sb.append(data);
                }

                if(data != null) {

                    String[] tokens = data.split(" : ");


                    weeklyMileageS = tokens[0];
                    DATE_S = tokens[1];

                    System.out.println(DATE); // Sat Jan 02 00:00:00 GMT 2010

                    SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd kk:mm:ss zzz yyyy", Locale.ENGLISH);
                    DATE = format.parse(DATE_S);
                }


                if (weeklyMileageS == null) {}
                else {
                    weeklyMileage = Float.parseFloat(weeklyMileageS);
                    weeklyMileageS = String.format("%.2f", weeklyMileage);
                    weeklyMileage = Float.parseFloat(weeklyMileageS);
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private boolean newWeek(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        Date thisWeek = c.getTime();

        Date nextWeek = new Date(thisWeek.getTime()+7*24*60*60*1000);

        if(date.after(thisWeek) && date.before(nextWeek))
            return false;

        else
            return true;
    }
}
