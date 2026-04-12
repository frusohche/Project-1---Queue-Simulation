package csciproject1;

/**
 * Description:
 * Represents one cashier line in the grocery store simulation.
 * Wraps an ArrayQueue to manage the customers waiting in this line.
 * Each line has a unique ID used to identify it in the simulation output.
 *
 * Input:
 * Customer objects passed to addCustomer() to join this line.
 *
 * Output:
 * Returns Customer objects via serveCustomer().
 * Prints line contents to the console via showLine().
 * 
 * @author 		Frusoh Che
 * @contact: 	frusoh.che@my.century.edu
 * @since		04/12/2026
 * 
 * Course: 		CSCI 2082-70
 * Institution: Century College
 * Instructor: 	Mathew Nyamagwa	
 */
public class CashierLine {

    int lineId;
    ArrayQueue customers;

    /**
     * Creates a new CashierLine with a given ID.
     *
     * @param lineId a unique number identifying this line (1 through 5)
     */
    public CashierLine(int lineId) {
        this.lineId = lineId;
        this.customers = new ArrayQueue();
    }

    /**
     * Adds a customer to the back of this line.
     * Does nothing if the line is full.
     *
     * @param customer the Customer to add
     */
    public void addCustomer(Customer customer) {
        customers.enQueue(customer);
    }

    /**
     * Removes and returns the customer at the front of this line.
     * Returns null if the line is empty.
     *
     * @return the Customer being served, or null if empty
     */
    public Customer serveCustomer() {
        return customers.deQueue();
    }

    /**
     * Returns how many customers are currently in this line.
     *
     * @return number of customers in line
     */
    public int getLength() {
        return customers.getSize();
    }

    /**
     * Returns true if this line has no customers.
     *
     * @return true if empty
     */
    public boolean isEmpty() {
        return customers.isEmpty();
    }

    /**
     * Returns true if this line is at maximum capacity.
     *
     * @return true if full
     */
    public boolean isFull() {
        return customers.isFull();
    }

    /**
     * Returns the unique ID of this cashier line.
     *
     * @return line ID
     */
    public int getLineId() {
        return lineId;
    }

    /**
     * Prints all customers currently waiting in this line.
     */
    public void showLine() {
        System.out.print("Line " + lineId + ": ");
        customers.show();
    }

}
