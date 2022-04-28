package id319520425;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

@SuppressWarnings("serial")
public class NewSet<T extends AmericanAnswers> implements Set<T>, Serializable{
	
	ArrayList<T> data = new ArrayList<T>();
	
	@Override
	public int size() {
		return data.size();
	}

	@Override
	public boolean isEmpty() {
		if (data.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean contains(Object o) {
		if (data.contains(o)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Iterator<T> iterator() {
		Iterator<T> it = data.iterator();
		if (it.hasNext()) {
			return it;
		}
		return data.iterator();
	}

	@Override
	public Object[] toArray() {
		Object[] arr = new Object[data.size()];
		for (int i = 0; i < data.size(); i++) {
			arr[i] = data.get(i);
		}
		return arr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		final T[] arr = (T[]) Array.newInstance(a.getClass().getComponentType(), data.size());
		for (int i = 0; i < data.size(); i++) {
			arr[i] = (T) data.get(i);
		}
		return arr;
	}

	public boolean add(int i, T e) {
		if (data.contains(e)) {
			System.out.println("Object already exists in Data.");
			return false;
		}
		else {
			data.add(i, e);
			return true;
		}
	}

	@Override
	public boolean remove(Object o) {
		if (data.contains(o)) {
			data.remove(o);
			System.out.println("Removed sucessfully.");
			return true;
		}
		else {
			System.out.println("Does not exist.");
			return false;
		}
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		if (data.containsAll(c)) {
			return true;
		}
		else {
			System.out.println("Instance does not contain whole collection.");
			return false;
		}
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		if (data.addAll(c)) {
			return true;
		}
		else {
			System.out.println("Failed to add all collection elements.");
			return false;
		}
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		if (data.retainAll(c)) {
			return true;
		}
		else {
			System.out.println("Failed to retain all collection elements.");
			return false;
		}
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		if (data.removeAll(c)) {
			return true;
		}
		else {
			System.out.println("Failed to remove all collection elements.");
			return false;
		}
	}

	@Override
	public void clear() {
		data.clear();
		System.out.println("Data cleared.");
	}

	@Override
	public boolean add(T e) {
		if (data.contains(e)) {
			System.out.println("Object already exists in Data.");
			return false;
		}
		if (!(e instanceof AmericanAnswers) ) {
			System.out.println("Object not an american answer.");
			return false;
		}
		else {
			data.add(e);
			return true;
		}
	}

	public void set(int i, Object object) {
		data.set(i, (T) object);
		System.out.println("Changed sucessfully.");
		
	}

	public T get(int index) {
		return data.get(index);
	}
}
