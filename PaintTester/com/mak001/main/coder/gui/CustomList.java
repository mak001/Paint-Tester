package com.mak001.main.coder.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

@SuppressWarnings("serial")
public class CustomList<object extends Object> extends
		AbstractListModel<object> {

	private final List<object> list;

	private final ArrayList<CustomListListener> listeners = new ArrayList<CustomListListener>();

	public CustomList() {
		list = new ArrayList<object>();
	}

	public CustomList(List<object> arrayList) {
		list = arrayList;
	}

	@Override
	public int getSize() {
		return list.size();
	}

	@Override
	public object getElementAt(int index) {
		return list.get(index);
	}

	public void add(object o) {
		int index = list.size();
		list.add(o);
		fireIntervalAdded(this, index, index);
		for (CustomListListener l : listeners) {
			l.intervalAdded();
		}
	}

	public void remove(object o) {
		int index = list.size();
		list.remove(o);
		fireIntervalRemoved(this, index, index);
		for (CustomListListener l : listeners) {
			l.intervalRemoved();
		}
	}

	public void addListDataListener(CustomListListener l) {
		listeners.add(l);
	}

	public void removeListDataListener(CustomListListener l) {
		listeners.remove(l);
	}

	public boolean contains(object o) {
		return list.contains(o);
	}

	public Object[] toArray() {
		return list.toArray();
	}

}
