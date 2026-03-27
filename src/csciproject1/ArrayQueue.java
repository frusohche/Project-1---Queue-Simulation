package csciproject1;

public class ArrayQueue {
	int queue[] = new int[4];
	int size;
	int front;
	int rear;
	
	public void enQueue(int data) {
		queue[rear] = data;
		rear = rear + 1;
		size = size + 1;
	}
	
	public int deQueue() {
		int data = queue[front];
		front = front + 1;
		size = size - 1;
		return data;
	}
	
	public void show() {
		
	}
	
	public int getSize() {
		return size;
	}
	
	public boolean isEmpty() {
		return getSize() == 0;
	}
	
	public boolean isFull() {
		return getSize() == 4;
	}
	

}
