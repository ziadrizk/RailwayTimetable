package railway_timetable;

public class Time {

    int hour;
    int minutes;

    public Time() {
        hour = 0;
        minutes = 0;
    }

    public Time(int hour, int minutes) {
        this.hour = hour;
        this.minutes = minutes;
    }

    public Time(String tim) {
        String h = tim.substring(0, 2);
        String m = tim.substring(2);

        hour = Integer.parseInt(h);
        minutes = Integer.parseInt(m);

    }

    public Time calculateJourneyTime(Time end) {
        int h = end.hour - hour;
        int m = end.minutes - minutes;

        Time time = new Time(h, m);
        return time;
    }

    @Override
    public String toString() {
        return hour + ":" + minutes;
    }

}
