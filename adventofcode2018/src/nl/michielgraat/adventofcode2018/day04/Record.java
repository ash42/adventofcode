package nl.michielgraat.adventofcode2018.day04;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Record implements Comparable<Record> {

    int guard = -1;
    int year;
    int month;
    int day;
    int hour;
    int minute;

    boolean fallsAsleep;
    boolean wakesUp;

    public Record(final String line) throws ParseException {
        parse(line);
    }

    private void parse(String line) throws ParseException {
        final String d = line.substring(0, line.indexOf("]") + 1);
        final SimpleDateFormat f = new SimpleDateFormat("[yyyy-MM-dd HH:mm]");
        final java.util.Date date = f.parse(d);
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        line = line.substring(line.indexOf("] ") + 2);
        if (line.startsWith("falls asleep")) {
            fallsAsleep = true;
        } else if (line.startsWith("wakes up")) {
            wakesUp = true;
        } else {
            guard = Integer.parseInt(line.substring(line.indexOf(" ") + 2, line.indexOf("begins") - 1));
        }
    }

    @Override
    public int compareTo(final Record o) {
        if (year != o.year) {
            if (year < o.year) {
                return -1;
            } else {
                return 1;
            }
        }
        if (month != o.month) {
            if (month < o.month) {
                return -1;
            } else {
                return 1;
            }
        }
        if (day != o.day) {
            if (day < o.day) {
                return -1;
            } else {
                return 1;
            }
        }
        if (hour != o.hour) {
            if (hour < o.hour) {
                return -1;
            } else {
                return 1;
            }
        }
        if (minute != o.minute) {
            if (minute < o.minute) {
                return -1;
            } else {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        final String month = (this.month < 10) ? "0" + this.month : String.valueOf(this.month);
        final String day = (this.day < 10) ? "0" + this.day : String.valueOf(this.day);
        final String hour = (this.hour < 10) ? "0" + this.hour : String.valueOf(this.hour);
        final String minute = (this.minute < 10) ? "0" + this.minute : String.valueOf(this.minute);
        String result = "[" + year + "-" + month + "-" + day + " " + hour + ":" + minute + "] ";
        if (guard != -1) {
            result += "Guard #" + guard + " begins shift";
        } else if (fallsAsleep) {
            result += "falls asleep";
        } else if (wakesUp) {
            result += "wakes up";
        }
        return result;
    }

}
