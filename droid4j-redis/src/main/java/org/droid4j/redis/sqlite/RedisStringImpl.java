package org.droid4j.redis.sqlite;

import org.apache.commons.lang.NotImplementedException;
import org.droid4j.redis.api.IRedisString;
import org.droid4j.sqlite.Sqlite;

public class RedisStringImpl implements IRedisString {

	private Sqlite sqlite = null;// new SqliteImpl();

	private static final String TABLE = "redis_strings";

	@Override
	public Long setnx(String key, String value) {
		if (this.exists(key)) {
			return 0L;
		}
		this.set(key, value);
		return 1L;
	}

	@Override
	public String set(String key, String value) {
		int seconds = 0;
		return this.setex(key, seconds, value);
	}

	@Override
	public String setex(String key, int seconds, String value) {
		long expire;
		if (seconds > 0) {
			expire = System.currentTimeMillis() + seconds * 1000;
		}
		else {
			expire = 0;
		}

		String sql = "insert into " + TABLE + "(name, value, expire) values(?,?,?);";
		this.sqlite.updateForBool(sql, key, value, expire);
		return value;
	}

	@Override
	public String get(String key) {
		String sql = "select * from " + TABLE + " where name=?";
		Strings strings = this.sqlite.query(sql, Strings.class, key);
		long expire = strings.getExpire();
		if (expire > 0 && expire < System.currentTimeMillis()) {
			return null;
		}
		return strings.getValue();
	}

	@Override
	public Long incr(String key) {
		throw new NotImplementedException();
	}

	@Override
	public Long decrBy(String key, long integer) {
		throw new NotImplementedException();
	}

	@Override
	public Long decr(String key) {
		throw new NotImplementedException();
	}

	@Override
	public Long incrBy(String key, long integer) {
		throw new NotImplementedException();
	}

	@Override
	public Boolean exists(String key) {
		throw new NotImplementedException();
	}

	@Override
	public Long expire(String key, int seconds) {
		throw new NotImplementedException();
	}

	@Override
	public Long del(String key) {
		String sql = "delete from " + TABLE + " where name=?";
		boolean success = this.sqlite.updateForBool(sql, key);
		if (success) {
			return 1L;
		}
		else {
			return 0L;
		}
	}

	@Override
	public boolean flushAll() {
		throw new NotImplementedException();
	}

	@Override
	public Long setrange(String key, long offset, String value) {
		throw new NotImplementedException();
	}
}
