package com.hannesdorfmann.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This {@link ListMap} implementation uses {@link ArrayList} and
 * {@link HashMap} and its not thread safe.
 * 
 * <p>
 * Unlike a normal List implementation, every item is unique ( proved by
 * {@link Mappable#getMapKey()} in this list. So its more like a Set
 * that contains his insert order than a List.
 * </p>
 * 
 * <p>
 * So this kind of {@link ListMap} gives you a {@link HashMap} to retrieve a
 * element by his id (key) and a {@link ArrayList} at the same time to get a
 * item very fast by his position in the list. So you will have O(1) for
 * retrieving a element by his id and O(1) to access item at a certain position
 * (index) in the list.
 * </p>
 * 
 * @author Hannes Dorfmann
 * 
 * @param <K>
 *            The Key
 * @param <V>
 *            The Value
 */
public class ArrayListMap<K, V extends Mappable<K>> extends ArrayList<V>
		implements ListMap<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4630450968498105177L;

	private final Map<K, V> idMap;

	/**
	 * Creates a new empty empty {@link ArrayListMap}
	 */
	public ArrayListMap() {
		super();
		idMap = new HashMap<K, V>();
	}

	/**
	 * Constructs a {@link ArrayListMap} containing the elements of the
	 * specified collection, in the order they are returned by the collection's
	 * iterator.
	 * 
	 * @param c
	 */
	public ArrayListMap(Collection<? extends V> c) {
		idMap = new HashMap<K, V>(c.size());
		for (V v : c)
			add(v);
	}

	public ArrayListMap(int initialCapacity) {
		super(initialCapacity);
		idMap = new HashMap<K, V>(initialCapacity);
	}

	/**
	 * Appends the specified element to the end of this list.
	 * 
	 * @param e
	 * @return
	 */
	@Override
	public boolean add(V e) {

		if (e.getMapKey() != null) {

			V oldValue = idMap.get(e.getMapKey());
			if (oldValue != null)
				super.remove(oldValue);

			boolean added = super.add(e);

			if (added)
				idMap.put(e.getMapKey(), e);
			else
				idMap.put(oldValue.getMapKey(), oldValue);

			return added;
		} else
			return super.add(e);
	}

	/**
	 * Inserts the specified element at the specified position in this list.
	 * 
	 * @param index
	 * @param element
	 */
	@Override
	public void add(int index, V e) {

		if (e.getMapKey() != null) {
			V oldValue = idMap.get(e.getMapKey());
			if (oldValue != null) {
				// there is already an elemenet with the same id
				super.remove(oldValue);
			}

			super.add(index, e);

			idMap.put(e.getMapKey(), e);
		} else
			super.add(index, e);
	}

	/**
	 * Appends all of the elements in the specified collection to the end of
	 * this list, in the order that they are returned by the specified
	 * collection's Iterator.
	 * 
	 * @param c
	 * @return
	 */
	@Override
	public boolean addAll(Collection<? extends V> c) {

		for (V v : c)
			add(v);

		return true;
	}

	/**
	 * Inserts all of the elements in the specified collection into this list,
	 * starting at the specified position.
	 * 
	 * @param index
	 * @param c
	 * @return
	 */
	@Override
	public boolean addAll(int index, Collection<? extends V> c) {

		for (V v : c)
			add(index++, v);

		return true;
	}

	/**
	 * Removes all elements
	 */
	@Override
	public void clear() {
		super.clear();
		idMap.clear();
	}

	@Override
	public V getByMapKey(K id) {
		return idMap.get(id);
	}

	@Override
	public V removeByMapKey(K id) {
		V removed = idMap.remove(id);

		if (removed != null) {
			int index;
			while ((index = super.indexOf(removed)) >= 0)
				super.remove(index);
		}

		return removed;
	}

	/**
	 * Removes the element at the specified position in this list.
	 * 
	 * @param index
	 * @return
	 */
	@Override
	public V remove(int index) {
		V v = super.remove(index);

		if (v != null && v.getMapKey() != null) {
			idMap.remove(v.getMapKey());
		}

		return v;
	}

	/**
	 * Removes the first occurrence of the specified element from this list, if
	 * it is present.
	 * 
	 * @param element
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object element) {
		boolean removed = super.remove(element);

		if (removed && element instanceof Mappable<?>
				&& ((V) element).getMapKey() != null)
			idMap.remove(((V) element).getMapKey());

		return removed;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean contains(Object value) {

		if (value instanceof Mappable
				&& ((V) value).getMapKey() != null) {
			V found = idMap.get(((Mappable<K>) value).getMapKey());
			if (found == value)
				return true;
		}

		return super.contains(value);

	}

	/**
	 * Replaces the
	 * 
	 * @param position
	 * @param e
	 * @return
	 */
	@Override
	public V set(int position, V e) {

		if (position >= size())
			throw new IndexOutOfBoundsException(
					"Try to replace element with index " + position
							+ " but the size of this list is " + size());

		V previous = get(position);

		if (e.getMapKey() != null)
			idMap.remove(previous.getMapKey());

		add(position, e);

		return previous;

	}
}
