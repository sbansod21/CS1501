import java.io.*;
import java.util.*;

/// shortestpath: lab 9/10 shortest hop.
// total miles: DJ lab
public class AirlineSystem {

    /////// declare variables
    private String[] cityNames = null;
    private Digraph G = null;
    private static Scanner scan = null;
    // private static final int INFINITY = Integer.MAX_VALUE;

    /**
     * Test client.
     */
    public static void main(String[] args) throws IOException {
        AirlineSystem airline = new AirlineSystem();
        scan = new Scanner(System.in);

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Welcome to SushB Airlines! We are excited to have you!");
        System.out.println("To get started please type the file name! (example.txt)");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
        airline.readGraph();

        while (true) {
            switch (airline.menu()) {
                case 1:
                    airline.printGraph();
                case 2:
                    System.out.println("Sush");
                    break;
                case 4:
                    System.out.println("Sorry to see you go! Come back soon!");
                    System.exit(0);
                default:
                    System.out.println("Incorrect option.");
            }
        }
    }

    private int menu() {
        System.out.println("*********************************");
        System.out.println("Choose one of the menu options below:");
        // System.out.println("1. Display all routes");
        // System.out.println("3. Compute Shortest path based on number of hops.");
        System.out.println("4. Exit.");
        System.out.println("*********************************");
        System.out.print("Choose (1-4) and press ENTER: ");

        int choice = Integer.parseInt(scan.nextLine());
        return choice;
    }

    private void readGraph() throws IOException {
        String fileName = scan.nextLine();
        Scanner fileScan = new Scanner(new FileInputStream(fileName));
        int v = Integer.parseInt(fileScan.nextLine());
        G = new Digraph(v);

        cityNames = new String[v];
        for (int i = 0; i < v; i++) {
            cityNames[i] = fileScan.nextLine();
        }

        while (fileScan.hasNext()) {
            int from = fileScan.nextInt();
            int to = fileScan.nextInt();
            G.addEdge(new DirectedEdge(from - 1, to - 1));
            fileScan.nextLine();
        }
        fileScan.close();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Thank you! We were able to import the data successfully!");
        System.out.println("Press ENTER to continue.");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
        scan.nextLine();
    }

    // Show the entire list of direct routes, distances and prices. This amounts to
    // outputting the entire graph (you do not need to use graphics) in a
    // well-formatted way. Note that this operation does not require any
    // sophisticated algorithms to implement.
    private void printGraph() {
        if (G == null) {
            System.out.println("Please import a graph first (option 1).");
            System.out.print("Please press ENTER to continue ...");
            scan.nextLine();
        } else {
            for (int i = 0; i < G.v; i++) {
                System.out.print(cityNames[i] + ": ");
                for (DirectedEdge e : G.adj(i)) {
                    System.out.print(cityNames[e.to()] + "  ");
                }
                System.out.println();
            }
            System.out.print("Please press ENTER to continue ...");
            scan.nextLine();

        }
    }

}