package com.hannesdorfmann.collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.hannesdorfmann.collections.ArrayListMap;
import com.hannesdorfmann.collections.ListMap;

public class ArrayListMapTest {

	@Test
	public void test() {

		ListMap<String, Data> list = new ArrayListMap<String, Data>();
		List<Data> inserted = new ArrayList<Data>();

		int tests = 100000;
		// Insert
		for (int i = 0; i < tests; i++) {

			Data d = new Data(Integer.toString(i));
			list.add(d);
			inserted.add(d);

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
	}
}
