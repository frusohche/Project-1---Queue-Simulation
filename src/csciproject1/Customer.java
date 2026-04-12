package csciproject1;

/**
 *Description:
 * Represents a single customer in the grocery store simulation.
 * Each customer has a unique ID, an arrival time, a randomly assigned
 * transaction time, and a wait time that grows each simulation tick.
 *
 * Input:
 * A customer ID and arrival time passed to the constructor.
 * Transaction time is assigned randomly between 1 and 10.
 *
 * Output:
 * Provides customer data via getter methods.
 * Returns a formatted summary string via toString().
 *
 *@author 		Frusoh Che
 * @contact: 	frusoh.che@my.century.edu
 * @since		04/12/2026
 * 
 * Course: 		CSCI 2082-70
 * Institution: Century College
 * Instructor: 	Mathew Nyamagwa	
 */
public class Customer {

    int customerId;
    int arrivalTime;
    int transactionTime;
    int waitTime;

    /**
     * Creates a new Customer with a given ID and arrival time.
     * Transaction time is assigned randomly between 1 and 10.
     *
     * @param customerId  a unique number identifying this customer
     * @param arrivalTime the time tick when this customer arrived
     */
    public Customer(int customerId, int arrivalTime) {
        this.customerId = customerId;
        this.arrivalTime = arrivalTime;
        this.transactionTime = (int)(Math.random() * 10) + 1;
        this.waitTime = 0;
    }

    /**
     * Increases this customer's wait time by 1 tick.
     * Called each time the simulation advances one time unit.
     */
    public void incrementWaitTime() {
        waitTime = waitTime + 1;
    }

    /**
     * Returns the unique ID of this customer.
     *
     * @return customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Returns the time tick when this customer arrived.
     *
     * @return arrival time
     */
    public int getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Returns how many ticks this customer's transaction takes.
     *
     * @return transaction time
     */
    public int getTransactionTime() {
        return transactionTime;
    }

    /**
     * Returns how many ticks this customer has been waiting.
     *
     * @return wait time
     */
    public int getWaitTime() {
        return waitTime;
    }

    /**
     * Returns a readable summary of this customer's info.
     *
     * @return formatted customer string
     */
    public String toString() {
        return "Customer #" + customerId +
               " (wait: " + waitTime +
               ", transaction: " + transactionTime + ")";
    }

}