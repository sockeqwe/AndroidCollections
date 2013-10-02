package com.hannesdorfmann.collection;

import com.hannesdorfmann.collections.Identifiable;
import com.hannesdorfmann.collections.ListMap;

/**
 * Simple class to do some unit testing on {@link ListMap} implementations
 * 
 * @author Hannes Dorfmann
 * 
 */
public class Data implements Identifiable<String> {

	public String id;

	public Data(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

}
