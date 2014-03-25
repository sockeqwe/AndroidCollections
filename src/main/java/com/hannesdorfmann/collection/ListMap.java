package com.hannesdorfmann.collection;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class brings the best of both, {@link List} and {@link Map}. So you can
 * simply iterate, like you would do with any {@link List} or get a item by his
 * list position. You can also search for an value by searching for his key
 * (id), like you would do with any {@link Map} implementation (
 * {@link #getByMapKey(Mappable)})
 * 
 * @author Hannes Dorfmann
 * 
 * @param <K>
 * @param <V>
 */
public interface ListMap<K, V extends Mappable<K>> extends List<V> {

	/**
	 * Get the value by the key. The access will be as fast as accessing a
	 * {@link Map}. If there are more elements with the same key in the list,
	 * than the first one (with the lowest index) will be returned.
	 * 
	 * @param id
	 *            The values id.
	 * @return
	 */
	public Set<V> getByMapKey(K id);

	/**
	 * Calls {@link #getByMapKey(Object)} but returns just the first one. Useful
	 * for scenarios, where you are absolutely sure that there is only one
	 * element with the given key, so you can use this short cut.
	 * 
	 * @param id
	 * @return
	 */
	public V getFirstByMapKey(K id);

	/**
	 * Remove all items in the list that matches have this key. More formally,
	 * removes all elements with the index i such that (o==null ? get(i)==null :
	 * o.equals(get(i))) (if such an element exists)
	 * 
	 * @param id
	 * @return The first element that has been found to be removed or null, if
	 *         there is no such element
	 */
	public Set<V> removeByMapKey(K id);

}
