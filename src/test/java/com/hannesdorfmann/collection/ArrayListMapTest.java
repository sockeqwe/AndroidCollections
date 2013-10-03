package com.hannesdorfmann.collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ArrayListMapTest {

	@Test
	public void test() {

		ListMap<String, Data> list = new ArrayListMap<String, Data>();
		List<Data> inserted = new ArrayList<Data>();

		int tests = 10000;
		// Insert
		for (int i = 0; i < tests; i++) {

			Data d = new Data(Integer.toString(i));
			list.add(d);
			inserted.add(d);
			assertTrue(list.contains(d));
			assertTrue(list.getById(d.getId()) == d);

		}

		// try to find inserted by key
		for (int i = 0; i < tests; i++) {

			Data d = list.getById(Integer.toString(i));
			assertTrue(inserted.contains(d));
		}

		// remove inserted
		for (Data d : inserted) {

			list.removeById(d.getId());

			assertFalse(list.contains(d));
			assertNull(list.getById(d.getId()));
		}

		assertTrue(list.size() == 0);
		assertTrue(list.isEmpty());

		inserted.clear();

		// Try to insert items with same key
		for (int i = 0; i < tests; i++) {

			Data d = new Data(Integer.toString(i));
			list.add(d);
			inserted.add(d);

			Data d2 = new Data(Integer.toString(i));
			list.add(d2);
			inserted.add(d2);

			assertFalse(list.contains(d));
			assertTrue(list.contains(d2));
			assertFalse(list.getById(d.getId()) == d);
			assertTrue(list.getById(d2.getId()) == d2);
			assertTrue(list.getById(d.getId()) == d2);
			assertTrue(list.getById(Integer.toString(i)) == d2);

			// remove original
			list.remove(d);
			inserted.remove(d);

		}

		inserted.clear();
		list.clear();

		assertTrue(list.isEmpty());
		assertTrue(list.size() == 0);

	}
}
