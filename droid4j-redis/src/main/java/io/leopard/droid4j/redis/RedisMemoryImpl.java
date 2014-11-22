package io.leopard.droid4j.redis;

import io.leopard.droid4j.redis.memory.RedisHashesImpl;
import io.leopard.droid4j.redis.memory.RedisListImpl;
import io.leopard.droid4j.redis.memory.RedisSetImpl;
import io.leopard.droid4j.redis.memory.RedisSortedSetImpl;
import io.leopard.droid4j.redis.memory.RedisStringImpl;

public class RedisMemoryImpl extends AbstractRedis {

	public RedisMemoryImpl() {
		this.redisString = new RedisStringImpl();
		this.redisHashes = new RedisHashesImpl();
		this.redisSortedSet = new RedisSortedSetImpl();
		this.redisSet = new RedisSetImpl();
		this.redisList = new RedisListImpl();
	}
}
