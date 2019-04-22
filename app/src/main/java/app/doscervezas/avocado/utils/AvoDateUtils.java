package app.doscervezas.avocado.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.MonthDay;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AvoDateUtils {

    static public long convertDateToLong(Date date){
        return date == null ? null : date.getTime();
    }

    static public Date getCurrentDate() {
        Calendar c = Calendar.getInstance();
        return c.getTime();
    }

    static public Date getTomorrowDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }

    static public String convertDateToString(Date date, String format){
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    static public Date removeTimeFromDate(Date date){
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    static public long getDaysBetweenDates(Date firstDate, Date secondDate){
        long difference = secondDate.getTime() - firstDate.getTime();
        return TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
    }

    static public Date convertStringToDate(String stringDate, String format){
        SimpleDateFormat df = new SimpleDateFormat(format);
        Date date = null;

        try {
            date = df.parse(stringDate);
        } catch (ParseException e){
            e.printStackTrace();
        }

        return date;
    }

    static public Date addDaysToDate(Date date, int days){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

}
