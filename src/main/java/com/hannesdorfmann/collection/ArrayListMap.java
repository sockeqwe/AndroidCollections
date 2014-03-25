package com.hannesdorfmann.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * This {@link ListMap} implementation uses {@link ArrayList} and
 * {@link HashMap} and its not thread safe.
 * 
 * <p>
 * Like a normal List implementation, every item can be contained multiple times
 * in the list.
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

	private final Map<K, Set<V>> idMap;

	/**
	 * Creates a new empty empty {@link ArrayListMap}
	 */
	public ArrayListMap() {
		super();
		idMap = new HashMap<K, Set<V>>();
	}

	/**
	 * Constructs a {@link ArrayListMap} containing the elements of the
	 * specified collection, in the order they are returned by the collection's
	 * iterator.
	 * 
	 * @param c
	 */
	public ArrayListMap(Collection<? extends V> c) {
		idMap = new HashMap<K, Set<V>>(c.size());
		for (V v : c) {
			add(v);
		}
	}

	public ArrayListMap(int initialCapacity) {
		super(initialCapacity);
		idMap = new HashMap<K, Set<V>>(initialCapacity);
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

			Set<V> listForKey = idMap.get(e.getMapKey());

			if (listForKey == null) {
				listForKey = new LinkedHashSet<V>(1);
				idMap.put(e.getMapKey(), listForKey);
			}

			boolean added = super.add(e);

			if (added) {
				listForKey.add(e);
			}

			return added;
		} else {
			return super.add(e);
		}
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

			Set<V> listForKey = idMap.get(e.getMapKey());

			if (listForKey == null) {
				listForKey = new LinkedHashSet<V>(1);
				idMap.put(e.getMapKey(), listForKey);
			}

			super.add(index, e);

			listForKey.add(e);

		} else {
			super.add(index, e);
		}
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

		for (V v : c) {
			add(v);
		}

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
	public Set<V> getByMapKey(K id) {
		return idMap.get(id);
	}

	@Override
	public Set<V> removeByMapKey(K id) {
		Set<V> removed = idMap.remove(id);

		if (removed != null) {

			int index;
			for (V v : removed) {

				index = -1;
				while ((index = super.indexOf(v)) >= 0) {
					super.remove(index);
				}
			}
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

			Set<V> removeList = idMap.get(v.getMapKey());

			if (removeList != null) {
				removeList.remove(v);

				if (removeList.isEmpty()) {
					idMap.remove(v.getMapKey());
				}
			}
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
				&& ((V) element).getMapKey() != null) {

			K key = ((V) element).getMapKey();
			Set<V> toRemove = idMap.get(key);

			if (toRemove != null) {

				toRemove.remove(element);

				if (toRemove.isEmpty()) {
					idMap.remove(key);
				}
				return true;
			}

		}

		return removed;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean contains(Object value) {

		if (value instanceof Mappable && ((V) value).getMapKey() != null) {

			Set<V> found = idMap.get(((Mappable<K>) value).getMapKey());

			if (found != null && !found.isEmpty()) {
				boolean foundInKeyMap = found.contains(value);

				if (foundInKeyMap) {
					return true;
				}

			}
		}

		return super.contains(value);

	}

	/**
	 * Replaces the item at the current position
	 * 
	 * @param position
	 * @param e
	 * @return
	 */
	@Override
	public V set(int position, V e) {

		if (position >= size()) {
			throw new IndexOutOfBoundsException(
					"Try to replace element with index " + position
							+ " but the size of this list is " + size());
		}

		V previous = get(position);

		K key = previous.getMapKey();

		if (key != null) {
			Set<V> prevList = idMap.get(key);
			if (prevList != null) {
				prevList.remove(previous);

				if (prevList.isEmpty()) {
					idMap.remove(key);
				}
			}
		}

		add(position, e);
		remove(position + 1);

		return previous;

	}

	@Override
	public V getFirstByMapKey(K id) {

		Set<V> founds = getByMapKey(id);

		if (founds == null || founds.isEmpty()) {
			return null;
		}

		for (V v : founds) {
			return v;
		}

		return null;
	}
}
