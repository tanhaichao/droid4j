package org.droid4j.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.NotImplementedException;

public class UniqueSet<E> implements Set<E> {

	private Map<E, String> data = new ConcurrentHashMap<E, String>();

	@Override
	public int size() {
		return data.size();
	}

	@Override
	public boolean isEmpty() {
		return data.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return data.containsKey(o);
	}

	@Override
	public Iterator<E> iterator() {
		return data.keySet().iterator();
	}

	@Override
	public Object[] toArray() {
		return data.keySet().toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return data.keySet().toArray(a);
	}

	@Override
	public boolean add(E e) {
		if (this.contains(e)) {
			throw new IllegalArgumentException("对象[" + e.toString() + "]已存在.");
		}

		data.put(e, "");
		return true;
	}

	@Override
	public boolean remove(Object o) {
		Object value = data.remove(o);
		return (value != null);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new NotImplementedException();
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new NotImplementedException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new NotImplementedException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new NotImplementedException();
	}

	@Override
	public void clear() {
		this.data.clear();
	}

}
