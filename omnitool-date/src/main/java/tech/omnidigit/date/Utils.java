package tech.omnidigit.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    public static String timeFormat = "yyyy-MM-dd HH:mm:ss";
    public static String dateFormat = "yyyy-MM-dd";

    public static String getTimeFormat() {
        return timeFormat;
    }

    public static void setTimeFormat(String timeFormat) {
        Utils.timeFormat = timeFormat;
    }

    public static String getDateFormat() {
        return dateFormat;
    }

    public static void setDateFormat(String dateFormat) {
        Utils.dateFormat = dateFormat;
    }

    public static String getCurrentTime() {
        SimpleDateFormat df = new SimpleDateFormat(timeFormat);
        return df.format(new Date());
    }

    public static String getCurrentDate() {
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        return df.format(new Date());
    }

    public static String date2String(Date date) {
        return date2String(date, dateFormat);
    }

    public static String date2String(Date date, String dateFormat) {
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        return df.format(date);
    }

    public static Date string2Date(String date) throws ParseException {
        return string2Date(date, dateFormat);
    }

    public static Date string2Date(String date, String dateFormat) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.parse(date);
    }

    public static String getLastDayOfMonth(String year, String month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(year));
        calendar.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        calendar.set(Calendar.DATE, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static boolean isLastDayOfMonth(String date) throws ParseException {
        return isLastDayOfMonth(date, dateFormat);
    }

    public static boolean isLastDayOfMonth(String date, String dateFormat) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(string2Date(date, dateFormat));
        calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));
        return calendar.get(Calendar.DAY_OF_MONTH) == 1;
    }

    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    public static int getDay(String date) throws ParseException {
        return getDay(string2Date(date));
    }

    public static int getDay(String date, String dateFormat) throws ParseException {
        return getDay(string2Date(date, dateFormat));
    }

    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getMonth(String date) throws ParseException {
        return getMonth(string2Date(date));
    }

    public static int getMonth(String date, String dateFormat) throws ParseException {
        return getMonth(string2Date(date, dateFormat));
    }

    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int getYear(String date) throws ParseException {
        return getYear(string2Date(date));
    }


    public static int getYear(String date, String dateFormat) throws ParseException {
        return getYear(string2Date(date, dateFormat));
    }

    public static int getIntervalMonth(String startDate, String endDate) throws ParseException {
        return getIntervalMonth(startDate, endDate, dateFormat);
    }

    public static int getIntervalMonth(String startDate, String endDate, String dateFormat) throws ParseException {
        SimpleDateFormat sfd = new SimpleDateFormat(dateFormat);
        Date start = sfd.parse(startDate);
        Date end = sfd.parse(endDate);
        int startYear = getYear(start);
        int startMonth = getMonth(start);
        int startDay = getDay(start);
        int endYear = getYear(end);
        int endMonth = getMonth(end);
        int endDay = getDay(end);

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        int maxStartDay = startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        int maxEndDay = endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int result = (endYear - startYear) * 12 + endMonth - startMonth;
        if (startDay == maxStartDay) {
            if (maxEndDay != endDay) {
                result -= 1;
            }
        } else {
            if (endDay != maxEndDay) {
                if (endDay < startDay) {
                    result -= 1;
                }
            }
        }

        return result;
    }

    public static int getIntervalDays(String startDate, String endDate) throws ParseException {
        return getIntervalDays(startDate, endDate, dateFormat);
    }

    public static int getIntervalDays(String startDate, String endDate, String dateFormat) throws ParseException {
        SimpleDateFormat simpleFormat = new SimpleDateFormat(dateFormat);
        Date start = simpleFormat.parse(startDate);
        Date end = simpleFormat.parse(endDate);
        long startTime = start.getTime();
        long endTime = end.getTime();
        return (int) ((endTime - startTime) / (1000 * 60 * 60 * 24));
    }

    public static boolean compare(String startDate, String endDate) throws ParseException {
        return compare(startDate, endDate, dateFormat);
    }

    public static boolean compare(String startDate, String endDate, String dateFormat) throws ParseException {
        return string2Date(startDate, dateFormat).before(string2Date(endDate, dateFormat));
    }
}
