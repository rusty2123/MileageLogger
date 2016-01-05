package com.gsandchase.mileagelogger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

public class MileageCounter {
    private HashMap<LocalDate, Double> milesMap;
    private String milesJSON_S;

    public MileageCounter() {
        milesMap = new HashMap<>();
    }

    public MileageCounter(String JSON){
        if(JSON == "")
            milesMap = new HashMap<>();
        else{
            Type collectionType = new TypeToken<HashMap<String, Double>>(){}.getType();
            milesMap = new Gson().fromJson(JSON, collectionType);
        }
    }

    public void addMileage(Date date, double miles) {
        // Converting to joda.time.LocalDate keeps day info, but gets rid of time info.
        LocalDate localDate = LocalDate.fromDateFields(date);
        if(milesMap.containsKey(localDate)) {
            Double prevMiles = milesMap.get(localDate);
            prevMiles = prevMiles == null ? 0.0 : prevMiles;
            milesMap.put(localDate, prevMiles + miles);
        } else {
            milesMap.put(localDate, miles);
        }
    }

    // clear deletes all mileage information for every day.
    public void clear() {
        milesMap = new HashMap<>();
    }

    // getDailyMileage gets the mileage associated with a particular day.
    public double getDailyMileage(Date date) {
        Double miles = milesMap.get(LocalDate.fromDateFields(date));
        if(miles == null) {
            return 0.0;
        } else {
            return miles;
        }
    }

    // getWeeklyMileage gets the mileage of each day in the week that 'date' is a part of.
    // The week starts with Monday and ends with Sunday (TODO: Verify this is true).
    public double getWeeklyMileage(Date date) {
        double totalMiles = 0.0;
        LocalDate localDate = LocalDate.fromDateFields(date);
        LocalDate endOfWeek = localDate.dayOfWeek().withMaximumValue();
        for(LocalDate currentDay = localDate.dayOfWeek().withMinimumValue();
            currentDay.compareTo(endOfWeek) <= 0;
            currentDay = currentDay.plusDays(1)) {
            if(milesMap.containsKey(currentDay)) {
                totalMiles += milesMap.get(currentDay);
            }
        }
        return totalMiles;
    }

    public void printMap(){
        System.out.println("Map Contents: " + milesMap);
    }

    // getWeeklyMileage gets the mileage of each day in the month that 'date' is a part of.
    public double getMonthlyMileage(Date date) {
        // TODO: Implement getMonthlyMileage.
        return 0.0;
    }

    // getAnnualMileage gets the mileage of each day in the year that 'date' is a part of.
    public double getAnnualMileage(Date date) {
        // TODO: Implement getAnnualMileage.
        return 0.0;
    }

    private void createJSON(){

        Gson gson = new Gson();
        milesJSON_S = gson.toJson(milesMap);
    }

    public String getMilesJSON_S(){
        createJSON();
        return milesJSON_S;
    }
}
