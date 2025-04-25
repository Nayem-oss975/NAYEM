package com.source.busreservation;

import java.util.*;

class Bus {
    int busNumber;
    String route;
    int totalSeats;
    int availableSeats;
    float fare;
    boolean[] seats;

    public Bus(int busNumber, String route, int totalSeats, float fare) {
        this.busNumber = busNumber;
        this.route = route;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.fare = fare;
        this.seats = new boolean[totalSeats];
    }
}

class Booking {
    int bookingID;
    int busNumber;
    int tickets;
    boolean paymentStatus;
    float totalFare;
    String userName;
    String mobileNumber;
    String paymentMethod;

    public Booking(int bookingID, int busNumber, int tickets, boolean paymentStatus, float totalFare, String userName, String mobileNumber, String paymentMethod) {
        this.bookingID = bookingID;
        this.busNumber = busNumber;
        this.tickets = tickets;
        this.paymentStatus = paymentStatus;
        this.totalFare = totalFare;
        this.userName = userName;
        this.mobileNumber = mobileNumber;
        this.paymentMethod = paymentMethod;
    }
}

public class BusReservation {
    static Scanner scanner = new Scanner(System.in);
    static List<Bus> buses = new ArrayList<>();
    static List<Booking> bookings = new ArrayList<>();
    static final String ADMIN_PASSWORD = "jatrajibon";

    public static void main(String[] args) {
        initializeBuses();
        while (true) {
            System.out.println("1. Admin\n2. User\n3. About Us\n4. Exit\nEnter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    if (adminLogin()) {
                        adminMenu();
                    } else {
                        System.out.println("Incorrect password. Access denied.");
                    }
                }
                case 2 -> userMenu();
                case 3 -> aboutUs();
                case 4 -> {
                    System.out.println("Thank you for using our system. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void aboutUs() {
        System.out.println("\n--- About Us ---");
        System.out.println("Bus Reservation System developed by Team JatraJibon.");
        System.out.println("Contributors: Nayem");
        System.out.println("Our goal is to provide an efficient bus ticket booking service.\n");
    }

    static boolean adminLogin() {
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();
        return ADMIN_PASSWORD.equals(password);
    }

    static void adminMenu() {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add Bus");
            System.out.println("2. View Buses");
            System.out.println("3. Buy Ticket");
            System.out.println("4. View Bookings");
            System.out.println("5. View Seat Availability");
            System.out.println("6. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addBus();
                case 2 -> viewBuses();
                case 3 -> buyTickets();
                case 4 -> viewBookings();
                case 5 -> viewSeatAvailability();
                case 6 -> {
                    System.out.println("Admin logged out.\n");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void userMenu() {
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. Buy Tickets");
            System.out.println("2. View Bookings");
            System.out.println("3. View Seat Availability");
            System.out.println("4. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> buyTickets();
                case 2 -> viewBookings();
                case 3 -> viewSeatAvailability();
                case 4 -> {
                    System.out.println("User logged out.\n");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void initializeBuses() {
        buses.add(new Bus(101, "Dhaka to Jashore", 40, 650.0f));
        buses.add(new Bus(102, "Dhaka to Magura", 35, 550.0f));
        buses.add(new Bus(103, "Dhaka to Khulna", 40, 700.0f));
        buses.add(new Bus(104, "Dhaka to feni", 40, 600.0f));
        buses.add(new Bus(105, "Dhaka to Kustia", 40, 620.0f));
    }

    static void addBus() {
        System.out.print("Enter Bus Number: ");
        int busNumber = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Route: ");
        String route = scanner.nextLine();
        System.out.print("Enter Total Seats: ");
        int totalSeats = scanner.nextInt();
        System.out.print("Enter Fare: ");
        float fare = scanner.nextFloat();
        buses.add(new Bus(busNumber, route, totalSeats, fare));
        System.out.println("Bus added successfully!");
    }

    static void viewBuses() {
        if (buses.isEmpty()) {
            System.out.println("No buses available.");
            return;
        }
        for (Bus bus : buses) {
            System.out.println("Bus No: " + bus.busNumber + " | Route: " + bus.route + " | Available Seats: " + bus.availableSeats + " | Fare: " + bus.fare);
        }
    }

    static void buyTickets() {
        System.out.print("Enter Bus Number: ");
        int busNumber = scanner.nextInt();
        scanner.nextLine();
        Bus bus = findBus(busNumber);
        if (bus == null) {
            System.out.println("Bus not found.");
            return;
        }
        System.out.print("Enter Number of Tickets: ");
        int tickets = scanner.nextInt();
        scanner.nextLine();
        if (tickets > bus.availableSeats) {
            System.out.println("Not enough seats available.");
            return;
        }
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Mobile: ");
        String mobile = scanner.nextLine();

        float totalFare = tickets * bus.fare;
        System.out.println("Select Payment Method:");
        System.out.println("1. Bank\n2. Card\n3. Mobile Banking");
        int paymentChoice = scanner.nextInt();
        scanner.nextLine();

        String paymentMethod = "";

        if (paymentChoice == 1) {
            paymentMethod = "Bank";
            System.out.print("Enter Bank Number: ");
            scanner.nextLine();
        } else if (paymentChoice == 2) {
            paymentMethod = "Card";
            System.out.print("Enter Card Number: ");
            scanner.nextLine();
        } else if (paymentChoice == 3) {
            System.out.println("Mobile Banking Options:");
            System.out.println("1. bKash\n2. Nagad\n3. Rocket\n4. Upay");
            int mobileOption = scanner.nextInt();
            scanner.nextLine();
            switch (mobileOption) {
                case 1 -> paymentMethod = "bKash";
                case 2 -> paymentMethod = "Nagad";
                case 3 -> paymentMethod = "Rocket";
                case 4 -> paymentMethod = "Upay";
                default -> {
                    System.out.println("Invalid mobile banking option.");
                    return;
                }
            }
            System.out.print("Enter Wallet Mobile Number: ");
            scanner.nextLine();
            System.out.print("Enter Wallet Password: ");
            scanner.nextLine();
        } else {
            System.out.println("Invalid payment method.");
            return;
        }

        bookings.add(new Booking(bookings.size() + 1, busNumber, tickets, true, totalFare, name, mobile, paymentMethod));
        bus.availableSeats -= tickets;
        System.out.println("Booking successful! Total Fare: " + totalFare + " | Paid via: " + paymentMethod);
    }

    static Bus findBus(int busNumber) {
        for (Bus bus : buses) {
            if (bus.busNumber == busNumber) return bus;
        }
        return null;
    }

    static void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }
        for (Booking booking : bookings) {
            System.out.println("Booking ID: " + booking.bookingID +
                    " | Bus: " + booking.busNumber +
                    " | Tickets: " + booking.tickets +
                    " | Name: " + booking.userName +
                    " | Mobile: " + booking.mobileNumber +
                    " | Payment: " + booking.paymentMethod +
                    " | Total Fare: " + booking.totalFare);
        }
    }

    static void viewSeatAvailability() {
        System.out.print("Enter Bus Number: ");
        int busNumber = scanner.nextInt();
        scanner.nextLine();
        Bus bus = findBus(busNumber);
        if (bus != null) {
            System.out.println("Bus No: " + bus.busNumber + " | Route: " + bus.route + " | Available Seats: " + bus.availableSeats);
        } else {
            System.out.println("Bus not found.");
        }
    }
}
