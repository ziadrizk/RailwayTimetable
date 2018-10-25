package railway_timetable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Railway_timetable {

    private static Scanner sc;

	public static ArrayList<String> readFile(String filename) {

        ArrayList<String> records = new ArrayList<>();
        try {
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    records.add(line);

                }
            }
            return records;
        } catch (IOException e) {
            System.err.format("Exception occurred trying to read '%s'.", filename);
            return null;
        }

    }

    //write to a file
    public static void writeFile(String filename, List<String> l, List<Integer> f, int numberOfWords) throws FileNotFoundException, UnsupportedEncodingException {

        try ( //write to a file
                PrintWriter writer = new PrintWriter(filename, "UTF-8")) {
            for (int i = l.size() - 1; i > l.size() - 1 - numberOfWords; i--) {
                writer.println(l.get(i) + "   " + f.get(i));
            }

            writer.close();
        }

    }

    public static void main(String[] args) {

        ArrayList<Station> myStations = new ArrayList<>();

        //Basic:
        //A. Timetable Data Persistence:
        System.out.println("*************************************Railway Time Table*****************************************");

        ArrayList<String> data = readFile("data.csv");

        System.out.println("                                                                   The time of day");

        System.out.println("Station/Stop");
        for (int i = 0; i < data.size(); i++) {
            String rawData = data.get(i);
            rawData = rawData.trim();
            if (rawData.equals("")) {
                continue;
            }
            Station station = new Station(rawData);
            myStations.add(station);
            System.out.println(station);
        }

        System.out.println("");
        Station startStation = myStations.get(0);
        Station endStation = myStations.get(myStations.size() - 1);

        String journeyTime = String.format("Journey time                    ");

        for (int i = 0; i < startStation.departureTimes.size(); i++) {
            journeyTime += String.format("%-22s  ", startStation.departureTimes.get(i).calculateJourneyTime(endStation.departureTimes.get(i)));
        }

        System.out.println(journeyTime);

        System.out.println("\n\n\n\n\n\n");

        sc = new Scanner(System.in);
        while (true) {

            System.out.println("Menu");
            System.out.println("1. Origin/Destination filtering:");
            System.out.println("2. Journey Duration calculation:");
            System.out.println("3. Real-Time LDBs (Live Departure Boards)");
            System.out.println("4. Exit");

            System.out.println("Please enter your choice:");
            String choice = sc.next();
            /*
            Intermediate:
            C. Origin/Destination filtering:
             */

            if (choice.equals("1")) {
                System.out.println("Please enter the origin station");
                String origin = sc.next() + sc.nextLine();

                System.out.println("Please enter the destination station");
                String destination = sc.next() + sc.nextLine();

                for (int i = 0; i < myStations.size(); i++) {
                    Station station = myStations.get(i);

                    if (station.name.equals(origin)) {
                        System.out.println("Origin");
                        System.out.println(station);
                        startStation = station;
                        break;
                    }
                }

                for (int i = 0; i < myStations.size(); i++) {
                    Station station = myStations.get(i);

                    if (station.name.equals(destination)) {
                        System.out.println("Destination");
                        System.out.println(station);
                        endStation = station;
                        break;
                    }
                }

                System.out.println("");

                journeyTime = "";
                journeyTime = String.format("Journey time                    ");

                for (int i = 0; i < startStation.departureTimes.size(); i++) {
                    journeyTime += String.format("%-22s  ", startStation.departureTimes.get(i).calculateJourneyTime(endStation.departureTimes.get(i)));
                }

                System.out.println(journeyTime);

            } else if (choice.equals("2")) {
                //D. Journey Duration calculation: 
                System.out.println("Please enter the start time");
                System.out.println("Please enter the start hour");
                String startHour = sc.next();
                System.out.println("Please enter the start minute");
                String startMinute = sc.next();
                Time t_start = new Time(Integer.parseInt(startHour), Integer.parseInt(startMinute));

                System.out.println("Please enter the end time");
                System.out.println("Please enter the end hour");
                String endHour = sc.next();
                System.out.println("Please enter the end minute");
                String endMinute = sc.next();
                Time t_end = new Time(Integer.parseInt(endHour), Integer.parseInt(endMinute));

                System.out.println("The journey time is " + t_start.calculateJourneyTime(t_end));
            } else if (choice.equals("4")) {
                break;
            } /*
            Advanced:
E. Real-Time LDBs (Live Departure Boards):
             */ else if (choice.equals("3")) {
                System.out.println("\n\nReal-Time LDBs (Live Departure Boards)");
                System.out.println("Please enter the departure station");
                String origin = sc.next() + sc.nextLine();

                System.out.println("Please enter the destination station");
                String destination = sc.next() + sc.nextLine();

                for (int i = 0; i < myStations.size(); i++) {
                    Station station = myStations.get(i);

                    if (station.name.equals(origin)) {

                        startStation = station;
                        break;
                    }
                }

                for (int i = 0; i < myStations.size(); i++) {
                    Station station = myStations.get(i);

                    if (station.name.equals(destination)) {

                        endStation = station;
                        break;
                    }
                }

                System.out.println("Live departure board for " + origin);

                System.out.println("Time                   To                     P.                     Status");
                for (int i = 0; i < startStation.departureTimes.size(); i++) {
                    Time time = startStation.departureTimes.get(i);
                    int platformNumber = (int) (Math.random() * 5);
                    int statu = (int) (Math.random() * 3);

                    String status = "";

                    if (statu == 0) {
                        status = "on-time";
                    }
                    if (statu == 1) {
                        status = "delayed";
                    }
                    if (statu == 2) {
                        status = "cancelled";
                    }

                    String row = String.format("%-22s %-22s %-22s %-22s  ", time, destination, platformNumber, status);
                    System.out.println(row);
                }

            } else {
                System.out.println("Invalid choice. Please try again.");
            }

        }

    }
}
