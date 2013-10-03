package com.hannesdorfmann.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import android.util.LongSparseArray;

/**
 * This is a {@link ListMap} implementation that maps Long to a Object. This
 * implementation uses a
 * {@link com.hannesdorfmann.collection.support.v4.util.LongSparseArray} and an
 * {@link ArrayList} as internal data structure.
 * 
 * 
 * @author Hannes Dorfmann
 * 
 * @param <V>
 */
public class LongArrayListMap<V extends Identifiable<Long>> extends
		ArrayList<V> implements ListMap<Long, V> {

	private static final long serialVersionUID = 2175336878139990835L;

	private final LongSparseArray<V> idMap;

	/**
	 * Creates a new empty empty {@link ArrayListMap}
	 */
	public LongArrayListMap() {
		super();
		idMap = new LongSparseArray<V>();
	}

	/**
	 * Constructs a {@link ArrayListMap} containing the elements of the
	 * specified collection, in the order they are returned by the collection's
	 * iterator.
	 * 
	 * @param c
	 */
	public LongArrayListMap(Collection<? extends V> c) {
		super(c);
		idMap = new LongSparseArray<V>(c.size());
		addToMap(c);
	}

	public LongArrayListMap(int initialCapacity) {
		super(initialCapacity);
		idMap = new LongSparseArray<V>(initialCapacity);
	}

	/**
	 * Put a collection to the internal {@link Map}
	 * 
	 * @param c
	 */
	private void addToMap(Collection<? extends V> c) {
		for (V v : c)
			idMap.put(v.getId(), v);
	}

	/**
	 * Appends the specified element to the end of this list.
	 * 
	 * @param e
	 * @return
	 */
	@Override
	public boolean add(V e) {
		boolean added = super.add(e);
		if (added)
			idMap.put(e.getId(), e);

		return added;
	}

	/**
	 * Inserts the specified element at the specified position in this list.
	 * 
	 * @param index
	 * @param element
	 */
	@Override
	public void add(int index, V element) {
		super.add(index, element);
		idMap.put(element.getId(), element);
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
		boolean added = super.addAll(c);
		if (added)
			addToMap(c);

		return added;
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
		boolean added = super.addAll(index, c);

		if (added)
			addToMap(c);

		return added;

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
	public V getById(Long id) {
		return idMap.get(id);
	}

	@Override
	public V removeById(Long id) {

		V removed = idMap.get(id);

		if (removed != null) {
			idMap.delete(id);
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

		if (v != null && !super.contains(v))
			idMap.remove(v.getId());

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

		if (removed && !super.contains(element)
				&& element instanceof Identifiable<?>)
			idMap.remove(((V) element).getId());

		return removed;
	}

}
