package com.hannesdorfmann.collection;

import java.util.ArrayList;
import java.util.Collection;

import com.hannesdorfmann.collection.support.v4.util.LongSparseArray;

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
public class LongArrayListMap<V extends Mappable<Long>> extends
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

		for (V v : c)
			add(v);

	}

	public LongArrayListMap(int initialCapacity) {
		super(initialCapacity);
		idMap = new LongSparseArray<V>(initialCapacity);
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

		for (V v : c) {
			add(index++, v);
		}

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
			idMap.remove(v.getMapKey());

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

	@Override
	public V set(int position, V e) {

		if (position >= size())
			throw new IndexOutOfBoundsException(
					"Try to replace element with index " + position
							+ " but the size of this list is " + size());

		if (e.getMapKey() != null) {
			V previous = get(position);
			idMap.remove(previous.getMapKey());

			add(position, e);

			return previous;
		} else
			return super.set(position, e);

	}

}
