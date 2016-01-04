package com.gsandchase.mileagelogger;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class MileageCounterTest {
    private static double MILES_THRESHOLD = 0.01;

    @Test
    public void getDailyMileage_returnsMilesForCorrectDay() throws Exception {
        // If we add mileage for just "today", then we expect the same miles to be
        // returned for "today", and 0 miles to be returned for every other day.
        Date today = new Date(1451653200000L); // Friday, 01 Jan 2016 13:00:00 GMT
        Date notToday = new Date(1451739600000L); // Saturday, 02 Jan 2016 13:00:00 GMT
        MileageCounter counter = new MileageCounter();
        counter.addMileage(today, 4.5);
        assertEquals(4.5, counter.getDailyMileage(today), MILES_THRESHOLD);
        assertEquals(0.0, counter.getDailyMileage(notToday), MILES_THRESHOLD);
    }

    @Test
    public void getWeeklyMileage_aggregatesMilesForWeek() {
        Date inThisWeek1 = new Date(1451653200000L); // Friday, 01 Jan 2016 13:00:00 GMT
        Date inThisWeek2 = new Date(1451739600000L); // Saturday, 02 Jan 2016 13:00:00 GMT
        Date notInWeek = new Date(1452258000000L); // Friday, 08 Jan 2016 13:00:00 GMT
        MileageCounter counter = new MileageCounter();
        counter.addMileage(inThisWeek1, 4.0);
        counter.addMileage(inThisWeek2, 6.0);
        // Count should be 10 miles no matter which day in the week is picked.
        assertEquals(10.0, counter.getWeeklyMileage(inThisWeek1), MILES_THRESHOLD);
        assertEquals(10.0, counter.getWeeklyMileage(inThisWeek2), MILES_THRESHOLD);
        assertEquals(0.0, counter.getWeeklyMileage(notInWeek), MILES_THRESHOLD);
    }
}