package io.leopard.droid4j.redis;

import io.leopard.droid4j.redis.sqlite.RedisHashesImpl;
import io.leopard.droid4j.redis.sqlite.RedisListImpl;
import io.leopard.droid4j.redis.sqlite.RedisSetImpl;
import io.leopard.droid4j.redis.sqlite.RedisSortedSetImpl;
import io.leopard.droid4j.redis.sqlite.RedisStringImpl;

public class RedisSqliteImpl extends AbstractRedis {

	public RedisSqliteImpl() {
		this.redisString = new RedisStringImpl();
		this.redisHashes = new RedisHashesImpl();
		this.redisSortedSet = new RedisSortedSetImpl();
		this.redisSet = new RedisSetImpl();
		this.redisList = new RedisListImpl();
	}

}
