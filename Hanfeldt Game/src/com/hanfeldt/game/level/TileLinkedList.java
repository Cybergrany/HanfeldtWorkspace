package com.hanfeldt.game.level;

import java.util.LinkedList;

import com.hanfeldt.game.tile.Tile;

public class TileLinkedList<T> extends LinkedList<LinkedList<T>>{
	private static final long serialVersionUID = -8280017693328004392L;
	
	/**
	 * Add a tile to the TileLinkedList
	 * @param index Index where the tile is to be added
	 * @param element Element to be added at index - must be of {@link Tile} type.
	 */
	public void addTile(int index, T element){
		while(index >= this.size()){
			this.add(new LinkedList<T>());
		}
		this.get(index).add(element);
	}
	
	/**
	 * Add a tile to the TileLinkedList
	 * @param index Location of tile on X axis.
	 * @param index2 Location of tile on Y axis
	 * @param element  Element to be added at index - must be of {@link Tile} type.
	 */
	public void addTile(int index, int index2, T element){
		while(index >= this.size()){
			this.add(new LinkedList<T>());
		}
		
		LinkedList<T> inner = this.get(index);
		while((index2 >= inner.size())){
			inner.add(null);
		}
		
		inner.set(index2, element);
	}
	
	public T getTile(int index, int index2){
		return this.get(index).get(index2);
	}
	
	public int getTileArraySize() {
		if(get(0) == null) {
			return 0;
		}else{
			return get(0).size();
		}
	}

}
