package models;

import play.data.validation.Constraints;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import static java.util.stream.Collectors.toList;
import static java.util.Comparator.comparing;

/**
 * Created by quarles on 9/1/2015.
 */
public class Printer {
    public String manufacturer;
    public String model;
    @Constraints.Required
    public String serialNumber;
    public String ipAddress;
    public String location;
    public String status;
    private static List<Printer> printers;

    static {
        printers = new ArrayList<>();
        printers.add(new Printer("Xerox", "WCP3545", "FKA345844", "155.118.104.122", "HB1 LS Corridor"));
        printers.add(new Printer("Xerox", "WC5638", "HRK090221", "155.118.97.45", "HB2W20"));
        printers.add(new Printer("Xerox", "ColorQube 8570", "ART657329", "155.118.88.57", "B105"));
        printers.add(new Printer("Xerox", "WC7655", "PBB625331", "155.118.97.18", "HB1N23"));
        printers.add(new Printer("Xerox", "WC5632", "ART987548", "155.118.105.69", "A2M25"));
        printers.add(new Printer("Xerox", "WCP3545", "WRT726038", "155.118.102.35", "A1M03"));
    }

    public Printer(String manufacturer, String model, String serialNumber, String ipAddress, String location) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.serialNumber = serialNumber;
        this.ipAddress = ipAddress;
        this.location = location;
        updateStatus();
    }

    public Printer() {
        updateStatus();
    }

    public static List<Printer> findAll() {
        return new ArrayList<>(printers);
    }

    public static List<Printer> findByManufacturer(String manufacturer) {
   //     List<Printer> results = printers.parallelStream().
    //            filter((Printer p) -> p.manufacturer.toLowerCase().equals(manufacturer.toLowerCase())).collect(toList());
        return null;
    }

    public static List<Printer> sortByModel() {
       ModelSortClass msc = new ModelSortClass();
        Collections.sort(printers, msc);
        return printers;
    }

    public static List<Printer> sortByLocation() {
        LocationSortClass lsc = new LocationSortClass();
        Collections.sort(printers, lsc);
        return printers;
    }

    public static List<Printer> sortBySerial() {
        SerialSortClass ssc = new SerialSortClass();
        Collections.sort(printers, ssc);
        return printers;
    }

    public static List<Printer> sortByManufacturer() {
        ManufacturerSortClass msc = new ManufacturerSortClass();
        Collections.sort(printers, msc);
        return printers;
    }

    public static List<Printer> sortByIp() {
        IpSortClass ipsc = new IpSortClass();
        Collections.sort(printers, ipsc);
        return printers;
    }

    public static Printer findBySerialNumber(String serial) {
        for (Printer p: printers) {
            if (p.serialNumber.toLowerCase().equals(serial.toLowerCase())) {
                return p;
            }
        }
        return null;
    }

    public static Printer findByIpAddress(String ip) {
        for (Printer p: printers) {
            if (p.ipAddress.toLowerCase().equals(ip.toLowerCase())) {
                return p;
            }
        }
        return null;
    }

    public void save() {
        printers.remove(findBySerialNumber(this.serialNumber));
        printers.add(this);
    }

    public static boolean remove(Printer printer) {
        return printers.remove(printer);
    }

    public String updateStatus() {
        String[] statuses = {"Ready", "Offline", "Change Drum", "Paper Jam", "Out of Cyan", "Out of Yellow", "Change Fuser"};
        int randomNumber = (int) (Math.random() * statuses.length);
        String randomStatus = statuses[randomNumber];
        status = randomStatus;
        return status;
    }

    static class ModelSortClass implements Comparator<Printer> {

        @Override
        public int compare(Printer p, Printer p2) {
            return p.model.toLowerCase().compareTo(p2.model.toLowerCase());
        }
    }

    static class LocationSortClass implements Comparator<Printer> {

        @Override
        public int compare(Printer p, Printer p2) {
            return p.location.toLowerCase().compareTo(p2.location.toLowerCase());
        }
    }

    static class SerialSortClass implements Comparator<Printer> {

        @Override
        public int compare(Printer p, Printer p2) {
            return p.serialNumber.toLowerCase().compareTo(p2.serialNumber.toLowerCase());
        }
    }

    static class ManufacturerSortClass implements Comparator<Printer> {

        @Override
        public int compare(Printer p, Printer p2) {
            return p.manufacturer.toLowerCase().compareTo(p2.manufacturer.toLowerCase());
        }
    }

    static class IpSortClass implements Comparator<Printer> {

        @Override
        public int compare(Printer o1, Printer o2) {
            String[] tokens1 = o1.ipAddress.split("\\.");
            String[] tokens2 = o2.ipAddress.split("\\.");
            String updateString1 = String.format("%3s.%3s.%3s.%3s", tokens1[0], tokens1[1], tokens1[2], tokens1[3]);
            String updateString2 = String.format("%3s.%3s.%3s.%3s", tokens2[0], tokens2[1], tokens2[2], tokens2[3]);
            return updateString1.compareTo(updateString2);
        }
    }
}
