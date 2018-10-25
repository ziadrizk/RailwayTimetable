package railway_timetable;

import java.util.ArrayList;

public class Station {

    String name;

    ArrayList<Time> departureTimes;

    public Station() {
        name = "";
        departureTimes = new ArrayList<>();
    }

    public Station(String name, ArrayList<Time> departureTimes) {
        this.name = name;
        this.departureTimes = departureTimes;
    }

    public Station(String rawData) {

        departureTimes = new ArrayList<>();

        String[] split = rawData.split(",");

        name = split[0];

        for (int i = 2; i < split.length; i++) {
            if (split[i].trim().equals("-")) {
                Time time = new Time();
                departureTimes.add(time);
            } else {
                Time time = new Time(split[i].trim());
                departureTimes.add(time);
            }
        }

    }

    @Override
    public String toString() {
        String format = String.format("%-30s  ", name);

        for (int i = 0; i < departureTimes.size(); i++) {

            Time time = departureTimes.get(i);

            if (time.hour == 0 && time.minutes == 0) {
                format += String.format("%-22s  ", " -  ");
            } else {
                format += String.format("%-22s  ", time);
            }
        }

        return format;

    }
}
