package nl.michielgraat.adventofcode2018.day4;

import java.util.Calendar;
import java.util.Date;

public class Record implements Comparable<Record>{

	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	
	private Date date;
	
	private boolean beginsShift;
	private boolean fallsAsleep;
	private boolean wakesUp;
	
	private String guard;
	
	public Record (String record) {
		parseRecord(record);
	}
	
	private void parseRecord(String record) {
		String date = record.substring(0, record.indexOf("]"));
		year = Integer.valueOf(date.substring(1, date.indexOf("-")));
		date = date.substring(date.indexOf("-") + 1);
		month = Integer.valueOf(date.substring(0, date.indexOf("-")));
		date = date.substring(date.indexOf("-") + 1);
		day = Integer.valueOf(date.substring(0, date.indexOf(" ")));
		date = date.substring(date.indexOf(" ") + 1);
		hour = Integer.valueOf(date.substring(0, date.indexOf(":")));
		minute = Integer.valueOf(date.substring(date.indexOf(":") + 1));
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DAY_OF_MONTH, day);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, 0);
		this.date = c.getTime();
		
		String action = record.substring(record.indexOf("]") + 2);
		if (action.equals("wakes up")) {
			wakesUp = true;
		} else if (action.equals("falls asleep")) {
			fallsAsleep = true;
		} else {
			action = action.substring(action.indexOf(" ") + 1);
			guard = action.substring(action.indexOf("#") + 1, action.indexOf(" "));
		}
	}
	
	public String getFormattedMonth() {
		return (this.month < 10) ? "0" + this.month : String.valueOf(this.month);
	}
	
	public String getFormattedDay() {
		return (this.day < 10) ? "0" + this.day : String.valueOf(this.day);
	}
	
	public String getMonthDay() {
		return getFormattedMonth() + "-" + getFormattedDay();
	}
	
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isBeginsShift() {
		return beginsShift;
	}

	public void setBeginsShift(boolean beginsShift) {
		this.beginsShift = beginsShift;
	}

	public boolean isFallsAsleep() {
		return fallsAsleep;
	}

	public void setFallsAsleep(boolean fallsAsleep) {
		this.fallsAsleep = fallsAsleep;
	}

	public boolean isWakesUp() {
		return wakesUp;
	}

	public void setWakesUp(boolean wakesUp) {
		this.wakesUp = wakesUp;
	}

	public String getGuard() {
		return guard;
	}

	public void setGuard(String guard) {
		this.guard = guard;
	}

	@Override
	public int compareTo(Record o) {
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
		String month = (this.month < 10) ? "0" + this.month : String.valueOf(this.month);
		String day = (this.day < 10) ? "0" + this.day : String.valueOf(this.day);
		String hour = (this.hour < 10) ? "0" + this.hour : String.valueOf(this.hour);
		String minute = (this.minute < 10) ? "0" + this.minute : String.valueOf(this.minute);
		String result = "[" + year + "-" + month + "-" + day + " " + hour + ":" + minute + "] ";
		if (guard != null && !guard.isEmpty()) {
			result += "Guard #" + guard + " begins shift";
		} else if (fallsAsleep) {
			result += "falls asleep";
		} else if (wakesUp) {
			result += "wakes up";
		}
		return result;
	}
	
	
}