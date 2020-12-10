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

        System.out.println("__________________________________");
        System.out.println("Welcome to SushB Airlines! We are excited to have you!");
        System.out.println("To get started please type the file name! (example.txt)");
        System.out.println("__________________________________");
        airline.readGraph();

        while (true) {
            switch (airline.menu()) {
                case 1:
                    airline.printGraph();
                case 2:
                    airline.makeMST();
                    break;
                case 4:
                    System.out.println("We're sad to see you go! Come back soon!");
                    System.exit(0);
                default:
                    System.out.println("Incorrect option.");
            }
        }
    }

    private int menu() {
        System.out.println("__________________________________");
        System.out.println("Choose one of the menu options below:");
        System.out.println("1. Display all routes");
        System.out.println("2. Display a miniumum spanning tree (MST) bases on distance");
        // System.out.println("3. Compute Shortest path based on number of hops.");
        System.out.println("4. Exit.");
        System.out.print("Choose (1-4) and press ENTER: ");
        System.out.println("__________________________________");

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
            int distance = fileScan.nextInt();
            double cost = fileScan.nextDouble();
            G.addEdge(new DirectedEdge(from - 1, to - 1, distance, cost));
        }
        fileScan.close();
        System.out.println("__________________________________");
        System.out.println("Thank you! We were able to import the data successfully!");
        System.out.println("Press ENTER to continue.");
        System.out.println("__________________________________");
        scan.nextLine();
    }

    private void printGraph() {
        if (G == null) {
            System.out.println("__________________________________");
            System.out.println("Please import a graph first (option 1).");
            System.out.print("Please press ENTER to continue ...");
            System.out.println("__________________________________");
            scan.nextLine();
        } else {
            for (int i = 0; i < G.v; i++) {
                System.out.println(cityNames[i] + " to... ");
                for (DirectedEdge e : G.adj(i)) {
                    System.out.println(">> " + cityNames[e.to()] + "  (" + e.distance() + " miles)  $" + e.cost());
                }
                System.out.println();
            }
            System.out.println("Please press ENTER to continue ...");
            System.out.println("__________________________________");

            scan.nextLine();

        }
    }

    private void makeMST() {
        PrimMSTTrace pMST = new PrimMSTTrace(G);

    }

}