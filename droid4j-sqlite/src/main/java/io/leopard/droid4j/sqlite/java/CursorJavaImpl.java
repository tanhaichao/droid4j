package io.leopard.droid4j.sqlite.java;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;

public class CursorJavaImpl implements Cursor {

	private Map<Integer, Integer> columnType = new LinkedHashMap<Integer, Integer>();
	private Map<Integer, String> columnName = new LinkedHashMap<Integer, String>();
	private List<Map<Integer, Object>> data = new ArrayList<Map<Integer, Object>>();

	public void addRow(Map<Integer, Object> row) {
		this.data.add(row);
	}

	public void addType(int columnIndex, int type, String name) {
		if (type == 12) {
			type = Cursor.FIELD_TYPE_STRING;
		}
		else if (type == 4) {
			type = Cursor.FIELD_TYPE_INTEGER;
		}
		this.columnType.put(columnIndex, type);
		this.columnName.put(columnIndex, name);
	}

	public void printMetaInfo() {
		System.out.println("columnType:" + columnType);
		System.out.println("columnName:" + columnName);
	}

	@Override
	public int getCount() {
		return this.data.size();
	}

	@Override
	public int getPosition() {
		return position;
	}

	@Override
	public boolean move(int offset) {

		return false;
	}

	@Override
	public boolean moveToPosition(int position) {

		return false;
	}

	private int position;

	@Override
	public boolean moveToFirst() {
		if (this.data.isEmpty()) {
			return false;
		}
		this.position = 0;
		return true;
	}

	@Override
	public boolean moveToLast() {

		return false;
	}

	@Override
	public boolean moveToNext() {
		if (this.position < this.data.size() - 1) {
			this.position++;
			return true;
		}
		return false;
	}

	@Override
	public boolean moveToPrevious() {

		return false;
	}

	@Override
	public boolean isFirst() {

		return false;
	}

	@Override
	public boolean isLast() {

		return false;
	}

	@Override
	public boolean isBeforeFirst() {

		return false;
	}

	@Override
	public boolean isAfterLast() {

		return false;
	}

	@Override
	public int getColumnIndex(String columnName) {

		return 0;
	}

	@Override
	public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {

		return 0;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return this.columnName.get(columnIndex);
	}

	@Override
	public String[] getColumnNames() {

		return null;
	}

	@Override
	public int getColumnCount() {
		return this.columnType.size();
	}

	@Override
	public byte[] getBlob(int columnIndex) {

		return null;
	}

	@Override
	public String getString(int columnIndex) {
		// System.err.println("getString:" + columnIndex);
		return (String) this.getCurrent().get(columnIndex);
	}

	@Override
	public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {

	}

	@Override
	public short getShort(int columnIndex) {

		return 0;
	}

	@Override
	public int getInt(int columnIndex) {
		return (Integer) this.getCurrent().get(columnIndex);
	}

	protected Map<Integer, Object> getCurrent() {
		return this.data.get(this.position);
	}

	@Override
	public long getLong(int columnIndex) {
		Object obj = this.getCurrent().get(columnIndex);
		return Long.parseLong(obj.toString());
	}

	@Override
	public float getFloat(int columnIndex) {
		return (Float) this.getCurrent().get(columnIndex);
	}

	@Override
	public double getDouble(int columnIndex) {

		return 0;
	}

	@Override
	public int getType(int columnIndex) {
		return this.columnType.get(columnIndex);
	}

	@Override
	public boolean isNull(int columnIndex) {

		return false;
	}

	@Override
	public void deactivate() {

	}

	@Override
	public boolean requery() {

		return false;
	}

	@Override
	public void close() {

	}

	@Override
	public boolean isClosed() {

		return false;
	}

	@Override
	public void registerContentObserver(ContentObserver observer) {

	}

	@Override
	public void unregisterContentObserver(ContentObserver observer) {

	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {

	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {

	}

	@Override
	public void setNotificationUri(ContentResolver cr, Uri uri) {

	}

	@Override
	public boolean getWantsAllOnMoveCalls() {

		return false;
	}

	@Override
	public Bundle getExtras() {

		return null;
	}

	@Override
	public Bundle respond(Bundle extras) {

		return null;
	}

}
