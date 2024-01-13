import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


class TrainTicket {
    String from;
    String to;
    String userFirstName;
    String userLastName;
    String userEmail;
    double pricePaid;
    String seatSection;
    int seatNumber;

    public TrainTicket(String from, String to, String userFirstName, String userLastName, String userEmail,
            double pricePaid, String seatSection, int seatNumber) {
        this.from = from;
        this.to = to;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.pricePaid = pricePaid;
        this.seatSection = seatSection;
        this.seatNumber = seatNumber;
    }

    public String getFormattedReceipt() {
        return "Receipt for " + userFirstName + " " + userLastName +
                "\nFrom: " + from +
                "\nTo: " + to +
                "\nEmail: " + userEmail +
                "\nPrice Paid: $" + pricePaid +
                "\nSeat Section: " + seatSection +
                "\nSeat Number: " + seatNumber;
    }
}

public class TrainBookingApp {
    private static Map<String, TrainTicket> tickets = new HashMap<>();
    private static int seatCounter = 1;

    private static void downloadReceipt() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your email address to download receipt: ");
        String email = scanner.next();

        TrainTicket ticket = tickets.get(email);
        if (ticket != null) {
            String formattedReceipt = ticket.getFormattedReceipt();
            String filePath = "./receipt.txt";
            try (FileWriter fileWriter = new FileWriter(filePath)) {
                fileWriter.write(formattedReceipt);
                System.out.println("Receipt downloaded successfully. Check 'receipt.txt'");
            } catch (IOException e) {
                System.out.println("Error occurred while downloading receipt.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Ticket not found for the provided email.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(
                    "\nTrain Booking System\n1. Purchase Ticket\n2. View Receipt\n3. View Users by Section\n4. Remove User\n5. Modify User's Seat\n6. Download Ticket\n7.Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    purchaseTicket();
                    break;
                case 2:
                    viewReceipt();
                    break;
                case 3:
                    viewUsersBySection();
                    break;
                case 4:
                    removeUser();
                    break;
                case 5:
                    modifyUserSeat();
                    break;
                case 6:
                    downloadReceipt();
                    break;
                case 7:
                    System.out.println("Exiting Train Booking System. Goodbye!");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

    }

    private static void purchaseTicket() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your first name: ");
        String firstName = scanner.next();

        System.out.print("Enter your last name: ");
        String lastName = scanner.next();

        System.out.print("Enter your email address: ");
        String email = scanner.next();

        double price = 20.0;

        String seatSection = seatCounter % 2 == 0 ? "B" : "A";
        TrainTicket ticket = new TrainTicket("London", "France", firstName, lastName, email, price, seatSection,
                seatCounter);
        tickets.put(email, ticket);

        seatCounter++;

        System.out.println("Ticket purchased successfully. Receipt:");
        System.out.println("From: " + ticket.from);
        System.out.println("To: " + ticket.to);
        System.out.println("User: " + ticket.userFirstName + " " + ticket.userLastName);
        System.out.println("Email: " + ticket.userEmail);
        System.out.println("Price Paid: $" + ticket.pricePaid);
        System.out.println("Seat Section: " + ticket.seatSection);
        System.out.println("Seat Number: " + ticket.seatNumber);
    }

    private static void viewReceipt() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your email address: ");
        String email = scanner.next();

        TrainTicket ticket = tickets.get(email);
        if (ticket != null) {
            System.out.println("Receipt for " + ticket.userFirstName + " " + ticket.userLastName);
            System.out.println("From: " + ticket.from);
            System.out.println("To: " + ticket.to);
            System.out.println("Email: " + ticket.userEmail);
            System.out.println("Price Paid: $" + ticket.pricePaid);
            System.out.println("Seat Section: " + ticket.seatSection);
            System.out.println("Seat Number: " + ticket.seatNumber);
        } else {
            System.out.println("Ticket not found for the provided email.");
        }
    }

    private static void viewUsersBySection() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the section (A or B): ");
        String section = scanner.next().toUpperCase();

        System.out.println("Users and their seats in Section " + section + ":");
        for (TrainTicket ticket : tickets.values()) {
            if (ticket.seatSection.equals(section)) {
                System.out.println(ticket.userFirstName + " " + ticket.userLastName + " - Seat " + ticket.seatNumber);
            }
        }
    }

    private static void removeUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the email address to remove: ");
        String email = scanner.next();

        TrainTicket removedTicket = tickets.remove(email);
        if (removedTicket != null) {
            System.out.println("User with email " + email + " removed from the train.");
        } else {
            System.out.println("Ticket not found for the provided email.");
        }
    }

    private static void modifyUserSeat() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the email address to modify seat: ");
        String email = scanner.next();

        TrainTicket ticket = tickets.get(email);
        if (ticket != null) {
            System.out.print("Enter the new seat number: ");
            int newSeatNumber = scanner.nextInt();

            ticket.seatNumber = newSeatNumber;
            System.out.println("Seat modified successfully. New seat number for " + ticket.userFirstName + " "
                    + ticket.userLastName + ": " + ticket.seatNumber);
        } else {
            System.out.println("Ticket not found for the provided email.");
        }
    }
}
