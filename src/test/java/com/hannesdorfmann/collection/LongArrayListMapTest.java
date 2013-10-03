package com.hannesdorfmann.collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class LongArrayListMapTest {

	@Test
	public void test() {

		ListMap<Long, Data<Long>> list = new LongArrayListMap<Data<Long>>();
		List<Data<Long>> inserted = new ArrayList<Data<Long>>();

		int tests = 100000;
		// Insert
		for (long i = 0; i < tests; i++) {

			Data<Long> d = new Data<Long>(i);
			list.add(d);
			inserted.add(d);
			assertTrue(list.contains(d));
			assertTrue(list.getById(d.getId()) == d);

		}

		// try to find inserted by key
		for (long i = 0; i < tests; i++) {

			Data<Long> d = list.getById(i);
			assertTrue(inserted.contains(d));
		}

		// remove inserted
		for (Data<Long> d : inserted) {

			list.removeById(d.getId());

			assertFalse(list.contains(d));
			assertNull(list.getById(d.getId()));
		}

		assertTrue(list.size() == 0);
		assertTrue(list.isEmpty());

		inserted.clear();

		// Try to insert items with same key
		for (long i = 0; i < tests; i++) {

			Data<Long> d = new Data<Long>(i);
			list.add(d);
			inserted.add(d);

			Data<Long> d2 = new Data<Long>(i);
			list.add(d2);
			inserted.add(d2);

			assertFalse(list.contains(d));
			assertTrue(list.contains(d2));
			assertFalse(list.getById(d.getId()) == d);
			assertTrue(list.getById(d2.getId()) == d2);
			assertTrue(list.getById(d.getId()) == d2);
			assertTrue(list.getById(i) == d2);

			// remove original
			list.remove(d);
			inserted.remove(d);

		}

		// Clear test
		inserted.clear();
		list.clear();

		assertTrue(list.isEmpty());
		assertTrue(list.size() == 0);

		list.add(new Data<Long>(0L));
		list.add(new Data<Long>(1L));
		list.add(new Data<Long>(2L));
		list.add(new Data<Long>(3L));
		list.add(new Data<Long>(4L));
		list.add(new Data<Long>(5L));
		list.add(new Data<Long>(6L));
		list.add(new Data<Long>(7L));
		list.add(new Data<Long>(8L));
		list.add(new Data<Long>(9L));

		// Check the order by removing previous one
		Data<Long> d = new Data<Long>(10L);
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

	}
}