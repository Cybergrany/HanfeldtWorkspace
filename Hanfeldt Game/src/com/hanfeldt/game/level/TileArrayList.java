/***
 * TODO: Don't think this is needed. Commenting out and not deleting for time being just in case it breaks stuff
 */

//package com.hanfeldt.game.level;
//
//import java.util.ArrayList;
//
//public class TileArrayList<T> extends ArrayList<ArrayList<T>>{
//	private static final long serialVersionUID = -8280017693328004392L;
//	
//	public void addToInnerArray(int index, T element){
//		while(index >= this.size()){
//			this.add(new ArrayList<T>());
//		}
//		this.get(index).add(element);
//	}
//	
//	public void addToInnerArray(int index, int index2, T element){
//		while(index >= this.size()){
//			this.add(new ArrayList<T>());
//		}
//		
//		ArrayList<T> inner = this.get(index);
//		while((index2 >= inner.size())){
//			inner.add(null);
//		}
//		
//		inner.set(index2, element);
//	}
//	
//	public T getFromInnerArray(int index, int index2){
//		return this.get(index).get(index2);
//	}
//	
//	public int getInnerArraySize() {
//		if(get(0) == null) {
//			return 0;
//		}else{
//			return get(0).size();
//		}
//	}
//	
//}
