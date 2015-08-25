package PriceCrawler;

import java.util.LinkedList;

// Store links that will be visited.
public class Queue {

	private LinkedList<Object> queue = new LinkedList();

	public void EnQueue(Object obj) {
		queue.addLast(obj);
	}

	public Object DeQueue() {
		return queue.removeFirst();
	}

	public boolean IsEmpty() {
		return queue.isEmpty();
	}

	public boolean IsContain(Object obj) {
		return queue.contains(obj);
	}

	public int Size() {
		return queue.size();
	}
}
