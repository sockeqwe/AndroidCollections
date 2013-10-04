package com.hannesdorfmann.collection;


/**
 * Simple class to do some unit testing on {@link ListMap} implementations
 * 
 * @author Hannes Dorfmann
 * 
 */
public class Data<T> implements Identifiable<T> {

	public T id;

	public Data(T id) {
		this.id = id;
	}

	@Override
	public T getKeyIdentifier() {
		return id;
	}

}
