package util;

import java.util.ArrayList;
import java.util.List;

public class Heap {
	private List<Heapable> nodes;
	private boolean invert_sort; //DEfault is accending

	public Heap(){
		this.invert_sort = false;
		nodes = new ArrayList<Heapable>();
	}

	public Heap(boolean sort){
		this.invert_sort = sort;
		nodes = new ArrayList<Heapable>();
	}
	
	public void insert(Heapable node){
		//insert at bottom
		//go up
	}
	
	public Heapable remove(){
		Heapable result = nodes.get(0);
		//move last to top
		//go down
		return result;
	}
	
	private void go_up(){
	}
	
	private void go_down(){
	}
}
