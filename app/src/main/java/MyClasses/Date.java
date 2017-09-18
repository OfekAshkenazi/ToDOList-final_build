package MyClasses;

import java.io.Serializable;

/**
 * Created by Ofek on 01-Sep-17.
 */

public class Date implements Serializable{
    int year,month,day;

    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
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

    public String getAsString(){
        return ""+day+'.'+month+'.'+year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Date)) return false;

        Date date = (Date) o;

        if (getYear() != date.getYear()) return false;
        if (getMonth() != date.getMonth()) return false;
        return getDay() == date.getDay();

    }

    @Override
    public int hashCode() {
        int result = getYear();
        result = 31 * result + getMonth();
        result = 31 * result + getDay();
        return result;
    }
}
