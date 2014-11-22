package org.droid4j.redis;

import org.droid4j.redis.sqlite.RedisHashesImpl;
import org.droid4j.redis.sqlite.RedisListImpl;
import org.droid4j.redis.sqlite.RedisSetImpl;
import org.droid4j.redis.sqlite.RedisSortedSetImpl;
import org.droid4j.redis.sqlite.RedisStringImpl;

public class RedisSqliteImpl extends AbstractRedis {

	public RedisSqliteImpl() {
		this.redisString = new RedisStringImpl();
		this.redisHashes = new RedisHashesImpl();
		this.redisSortedSet = new RedisSortedSetImpl();
		this.redisSet = new RedisSetImpl();
		this.redisList = new RedisListImpl();
	}

}
