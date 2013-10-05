package com.hannesdorfmann.collection;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class ArrayListMapTest extends TestCase {

	public void test() {

		ListMap<String, Data<String>> list = new ArrayListMap<String, Data<String>>();
		List<Data<String>> inserted = new ArrayList<Data<String>>();

		int tests = 10000;
		// Insert
		for (int i = 0; i < tests; i++) {

			Data<String> d = new Data<String>(Integer.toString(i));
			list.add(d);
			inserted.add(d);
			assertTrue(list.contains(d));
			assertTrue(list.getById(d.getMapKey()) == d);

		}

		// try to find inserted by key
		for (int i = 0; i < tests; i++) {

			Data<String> d = list.getById(Integer.toString(i));
			assertTrue(inserted.contains(d));
		}

		// remove inserted
		for (Data<String> d : inserted) {

			list.removeById(d.getMapKey());

			assertFalse(list.contains(d));
			assertNull(list.getById(d.getMapKey()));
		}

		assertTrue(list.size() == 0);
		assertTrue(list.isEmpty());

		inserted.clear();

		// Try to insert items with same key
		for (int i = 0; i < tests; i++) {

			Data<String> d = new Data<String>(Integer.toString(i));
			list.add(d);
			inserted.add(d);

			Data<String> d2 = new Data<String>(Integer.toString(i));
			list.add(d2);
			inserted.add(d2);

			assertFalse(list.contains(d));
			assertTrue(list.contains(d2));
			assertFalse(list.getById(d.getMapKey()) == d);
			assertTrue(list.getById(d2.getMapKey()) == d2);
			assertTrue(list.getById(d.getMapKey()) == d2);
			assertTrue(list.getById(Integer.toString(i)) == d2);

			// remove original
			list.remove(d);
			inserted.remove(d);

		}

		// Clear test
		inserted.clear();
		list.clear();

		assertTrue(list.isEmpty());
		assertTrue(list.size() == 0);

		list.add(new Data<String>("0"));
		list.add(new Data<String>("1"));
		list.add(new Data<String>("2"));
		list.add(new Data<String>("3"));
		list.add(new Data<String>("4"));
		list.add(new Data<String>("5"));
		list.add(new Data<String>("6"));
		list.add(new Data<String>("7"));
		list.add(new Data<String>("8"));
		list.add(new Data<String>("9"));

		// Check the order by removing previous one
		Data<String> d = new Data<String>("10");
		for (int i = 0; i < 10; i++) {
			list.add(i, d);

			assertTrue(list.get(i) == d);
			list.remove(d);
		}

		// Check order by replacing previous one
		for (int i = 0; i < 10; i++) {
			list.add(i, d);

			assertTrue(list.get(i) == d);
		}

		// Test the set() method
		list.set(10, d);
		assertTrue(list.get(10) == d);

		list.set(2, d);
		assertTrue(list.get(2) == d);

		list.set(2, d);
		assertTrue(list.get(2) == d);

		list.set(list.size() - 1, d);
		assertTrue(list.get(list.size() - 1) == d);

		// test for null values
		for (int i = 0; i < 1000; i++) {
			int oldSize = list.size();
			d = new Data<String>(null);
			list.add(d);
			assertTrue(list.size() == oldSize + 1);
			assertTrue(list.get(oldSize) == d);
		}

	}
}
