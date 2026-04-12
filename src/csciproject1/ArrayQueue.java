package csciproject1;

/**
 * Description:
 * A fixed-size circular queue that stores Customer objects using an array.
 * Uses circular indexing so that array slots are reused after customers
 * are removed, preventing the queue from running out of space prematurely.
 *
 * Input:
 * Customer objects passed to enQueue() to be added to the queue.
 *
 * Output:
 * Returns Customer objects via deQueue() and getCustomerAt().
 * Prints queue contents to the console via show().
 * 
 * @author 		Frusoh Che
 * @contact: 	frusoh.che@my.century.edu
 * @since		04/12/2026
 * 
 * Course: 		CSCI 2082-70
 * Institution: Century College
 * Instructor: 	Mathew Nyamagwa	

 */
public class ArrayQueue {

    static final int CAPACITY = 4;
    Customer[] queue = new Customer[CAPACITY];
    int size;
    int front;
    int rear;

    /**
     * Adds a Customer to the back of the queue.
     * Uses circular indexing to wrap around the array.
     * Does nothing if the queue is full.
     *
     * @param customer the Customer to add
     */
    public void enQueue(Customer customer) {
        if (isFull()) {
            System.out.println("Line is full. Customer cannot join.");
            return;
        }
        queue[rear] = customer;
        rear = (rear + 1) % CAPACITY;
        size = size + 1;
    }

    /**
     * Removes and returns the Customer at the front of the queue.
     * Uses circular indexing to wrap around the array.
     * Returns null if the queue is empty.
     *
     * @return the Customer removed, or null if empty
     */
    public Customer deQueue() {
        if (isEmpty()) {
            return null;
        }
        Customer customer = queue[front];
        queue[front] = null;
        front = (front + 1) % CAPACITY;
        size = size - 1;
        return customer;
    }

    /**
     * Prints all Customers currently in the queue from front to back.
     */
    public void show() {
        if (isEmpty()) {
            System.out.println("(empty)");
            return;
        }
        for (int i = 0; i < size; i++) {
            int index = (front + i) % CAPACITY;
            System.out.print(queue[index] + " ");
        }
        System.out.println();
    }

    /**
     * Returns the Customer at a given position without removing them.
     * Position 0 is the front of the queue.
     *
     * @param position the index from the front
     * @return the Customer at that position, or null if invalid
     */
    public Customer getCustomerAt(int position) {
        if (position < 0 || position >= size) {
            return null;
        }
        return queue[(front + position) % CAPACITY];
    }

    /**
     * Returns the number of Customers currently in the queue.
     *
     * @return current queue size
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns true if the queue has no Customers.
     *
     * @return true if empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns true if the queue is at maximum capacity.
     *
     * @return true if full
     */
    public boolean isFull() {
        return size == CAPACITY;
    }
}
