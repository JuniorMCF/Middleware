package middleware;

import java.util.LinkedList;
import java.util.Queue;

public class Cola{
	public Queue<String> queue;// = new LinkedList<>();

	public Cola(){
		this.queue = new LinkedList<>();
	}

	public boolean empty(){
		if(queue.size()>0) return false;
		else return true;
	}

	public void addElement(String element){
		queue.add(element);
	}

	public String extractElement(){
		String data = queue.peek();
		queue.poll();
		return data;
	}
}