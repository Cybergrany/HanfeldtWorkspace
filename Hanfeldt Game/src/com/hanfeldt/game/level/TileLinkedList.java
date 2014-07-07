package com.hanfeldt.game.level;

import java.util.LinkedList;

public class TileLinkedList<T> extends LinkedList<LinkedList<T>>{
	private static final long serialVersionUID = -8280017693328004392L;
	
	public void addToInnerArray(int index, T element){
		while(index >= this.size()){
			this.add(new LinkedList<T>());
		}
		this.get(index).add(element);
	}
	
	public void addToInnerArray(int index, int index2, T element){
		while(index >= this.size()){
			this.add(new LinkedList<T>());
		}
		
		LinkedList<T> inner = this.get(index);
		while((index2 >= inner.size())){
			inner.add(null);
		}
		
		inner.set(index2, element);
	}
	
	public T getFromInnerArray(int index, int index2){
		return this.get(index).get(index2);
	}
	
	public int getInnerArraySize() {
		if(get(0) == null) {
			return 0;
		}else{
			return get(0).size();
		}
	}

}
