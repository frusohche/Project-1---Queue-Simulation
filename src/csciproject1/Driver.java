package csciproject1;

/**
 * Description:
 * This program simulates a grocery store with five cashier lines.
 * Customers arrive randomly each tick and join the shortest available line.
 * Customers will leave if they wait too long, switch to a shorter line at
 * regular intervals, and are turned away if all lines are full.
 * The simulation uses a custom ArrayQueue as the underlying data structure
 * for each cashier line.
 * 
 * * Input:
 * None. All simulation values are controlled by constants defined
 * at the top of this class (SIM_TICKS, MAX_WAIT_TIME, etc.).
 *
 * Output:
 * Prints a tick-by-tick summary of the simulation to the console,
 * including customer arrivals, services, line switches, and departures.
 * Displays a formatted table of all 5 cashier lines each tick.
 *
 * @author 		Frusoh Che
 * @contact: 	frusoh.che@my.century.edu
 * @since		04/12/2026
 * 
 * Course: 		CSCI 2082-70
 * Institution: Century College
 * Instructor: 	Mathew Nyamagwa	
 */
public class Driver {

    static final int NUM_LINES       = 5;
    static final int MAX_LINE_LENGTH = 4;
    static final int MAX_WAIT_TIME   = 8;
    static final int SIM_TICKS       = 40;
    static final int SWITCH_INTERVAL = 3;

    /**
     * Entry point. Sets up lines and runs the simulation.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
    	
    	System.out.println("==========================================");
    	System.out.println("   GROCERY STORE SIMULATION");
    	System.out.println("   Simulated by: Frusoh Che");
    	System.out.println("==========================================");

    	
    	        CashierLine[] lines = setupLines();
        runSimulation(lines);
    }

    /**
     * Creates and returns an array of 5 CashierLine objects.
     *
     * @return array of CashierLine
     */
    public static CashierLine[] setupLines() {
        CashierLine[] lines = new CashierLine[NUM_LINES];
        for (int i = 0; i < NUM_LINES; i++) {
            lines[i] = new CashierLine(i + 1);
        }
        return lines;
    }

    /**
     * Runs the simulation loop for a set number of time ticks.
     *
     * @param lines the array of cashier lines
     */
    public static void runSimulation(CashierLine[] lines) {
        int customerCount = 0;
        for (int tick = 1; tick <= SIM_TICKS; tick++) {
            System.out.println("\n==========================================");
            System.out.println("  TICK " + tick);
            System.out.println("==========================================");
            customerCount = processTick(lines, tick, customerCount);
            printLines(lines, 0);
        }
    }

    /**
     * Processes all events for a single simulation tick.
     * Spawns a customer, serves lines, updates wait times,
     * and checks for line switches and departures.
     *
     * @param lines         the array of cashier lines
     * @param tick          the current time tick
     * @param customerCount the number of customers created so far
     * @return updated customer count
     */
    public static int processTick(CashierLine[] lines, int tick, int customerCount) {
        customerCount = spawnCustomer(lines, tick, customerCount);
        serveCustomers(lines);
        incrementWaitTimes(lines);
        if (tick % SWITCH_INTERVAL == 0) {
            switchLines(lines);
        }
        leaveLines(lines);
        return customerCount;
    }
    
    /**
     * Serves the front customer in each non-empty line.
     * Only serves a customer if they have waited at least 1 tick.
     *
     * @param lines the array of cashier lines
     */
    public static void serveCustomers(CashierLine[] lines) {
        for (int i = 0; i < lines.length; i++) {
            if (!lines[i].isEmpty()) {
                Customer front = lines[i].customers.getCustomerAt(0);
                if (front.getWaitTime() >= front.getTransactionTime()) {
                    lines[i].serveCustomer();
                    System.out.println("Line " + lines[i].getLineId() +
                                       " served Customer #" + front.getCustomerId());
                }
            }
        }
    }

    /**
     * Increments the wait time of every customer currently in a line.
     *
     * @param lines the array of cashier lines
     */
    public static void incrementWaitTimes(CashierLine[] lines) {
        for (int i = 0; i < lines.length; i++) {
            ArrayQueue q = lines[i].customers;
            for (int j = 0; j < q.getSize(); j++) {
                q.getCustomerAt(j).incrementWaitTime();
            }
        }
    }

    /**
     * Checks every line for customers who have waited too long.
     * Removes any customer whose wait time exceeds MAX_WAIT_TIME.
     *
     * @param lines the array of cashier lines
     */
    public static void leaveLines(CashierLine[] lines) {
        for (int i = 0; i < lines.length; i++) {
            ArrayQueue q = lines[i].customers;
            int checked = 0;
            int total = q.size;
            while (checked < total) {
                Customer c = lines[i].serveCustomer();
                if (c.getWaitTime() > MAX_WAIT_TIME) {
                    System.out.println("Customer #" + c.getCustomerId() +
                                       " left Line " + lines[i].getLineId() +
                                       " (waited too long)");
                } else {
                    lines[i].addCustomer(c);
                }
                checked = checked + 1;
            }
        }
    }

    /**
     * Every SWITCH_INTERVAL ticks, each customer at the front of a longer
     * line checks if a shorter line exists. If one is found, they switch.
     * This ensures customers actively seek the fastest available line.
     *
     * @param lines the array of cashier lines
     */
    public static void switchLines(CashierLine[] lines) {
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].isEmpty()) {
                continue;
            }
            CashierLine shortest = findShortestLine(lines);
            if (shortest.getLineId() == lines[i].getLineId()) {
                continue;
            }
            Customer front = lines[i].customers.getCustomerAt(0);
            System.out.println("Customer #" + front.getCustomerId() +
                               " is checking lines from Line " + lines[i].getLineId() + "...");
            if (shortest.getLength() < lines[i].getLength() && !shortest.isFull()) {
                lines[i].serveCustomer();
                shortest.addCustomer(front);
                System.out.println("Customer #" + front.getCustomerId() +
                                   " switched from Line " + lines[i].getLineId() +
                                   " to Line " + shortest.getLineId());
            } else {
                System.out.println("Customer #" + front.getCustomerId() +
                                   " stayed in Line " + lines[i].getLineId());
            }
        }
    }

    /**
     * Randomly decides if a new customer arrives this tick.
     * If so, finds the shortest line and adds them.
     * Customer is turned away if all lines are full.
     *
     * @param lines         the array of cashier lines
     * @param tick          the current time tick
     * @param customerCount the number of customers created so far
     * @return updated customer count
     */
    public static int spawnCustomer(CashierLine[] lines, int tick, int customerCount) {
        boolean arrives = Math.random() < 0.7;
        if (!arrives) {
            return customerCount;
        }

        CashierLine shortest = findShortestLine(lines);

        if (shortest.isFull()) {
            System.out.println("All lines full. Customer turned away.");
            return customerCount;
        }

        customerCount = customerCount + 1;
        Customer newCustomer = new Customer(customerCount, tick);
        shortest.addCustomer(newCustomer);
        System.out.println("Customer #" + customerCount +
                           " joined Line " + shortest.getLineId());
        return customerCount;
    }

    /**
     * Finds and returns the shortest line.
     * If two lines are equal length, the first one is chosen.
     *
     * @param lines the array of cashier lines
     * @return the CashierLine with the fewest customers
     */
    public static CashierLine findShortestLine(CashierLine[] lines) {
        CashierLine shortest = lines[0];
        for (int i = 1; i < lines.length; i++) {
            if (lines[i].getLength() < shortest.getLength()) {
                shortest = lines[i];
            }
        }
        return shortest;
    }

    /**
     * Recursively prints the state of each cashier line.
     * Demonstrates recursion by processing one line per call
     * and calling itself for the remaining lines.
     *
     * Base case: index equals the number of lines, nothing left to print.
     * Recursive case: print current line, then call for next index.
     *
     * @param lines the array of cashier lines
     * @param index the current line index being printed
     */
    public static void printLines(CashierLine[] lines, int index) {
        if (index == lines.length) {
            return;
        }
        lines[index].showLine();
        printLines(lines, index + 1);
    }

    /**
     * Builds a short readable string listing customers in a line.
     *
     * @param line the CashierLine to summarize
     * @return formatted string of customer IDs and wait times
     */
    public static String buildCustomerList(CashierLine line) {
        if (line.isEmpty()) {
            return "(empty)";
        }
        String result = "";
        ArrayQueue q = line.customers;
        for (int i = 0; i < q.getSize(); i++) {
            Customer c = q.getCustomerAt(i);
            result = result + "#" + c.getCustomerId() +
                     "(w:" + c.getWaitTime() + ") ";
        }
        return result;
    }

    /**
     * Pads a string with spaces on the right to a given width.
     *
     * @param text  the string to pad
     * @param width the total desired width
     * @return padded string
     */
    public static String padRight(String text, int width) {
        while (text.length() < width) {
            text = text + " ";
        }
        return text;
    }

}