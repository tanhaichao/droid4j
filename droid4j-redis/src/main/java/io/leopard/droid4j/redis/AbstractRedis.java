package io.leopard.droid4j.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;

import io.leopard.droid4j.redis.api.IRedisHashes;
import io.leopard.droid4j.redis.api.IRedisList;
import io.leopard.droid4j.redis.api.IRedisSet;
import io.leopard.droid4j.redis.api.IRedisSortedSet;
import io.leopard.droid4j.redis.api.IRedisString;

/**
 * Redis接口内存实现.
 * 
 * @author 阿海
 * 
 */
public class AbstractRedis implements Redis {

	protected IRedisString redisString;

	protected IRedisHashes redisHashes;

	protected IRedisSortedSet redisSortedSet;

	protected IRedisSet redisSet;

	protected IRedisList redisList;

	@Override
	public Long append(String key, String value) {
		String current = this.get(key);
		current = StringUtils.defaultString(current);
		String str = current + value;
		this.set(key, str);
		return (long) str.length();
		// throw new NotImplementedException();
	}

	@Override
	public String set(String key, String value) {
		return this.redisString.set(key, value);
	}

	@Override
	public Long del(String... keys) {
		long count = 0;
		for (String key : keys) {
			long result = this.decr(key);
			count += result;
		}
		return count;
	}

	@Override
	public Long del(String key) {
		Long result = redisString.del(key);
		if (result == 0) {
			//
		}
		return result;
	}

	@Override
	public Long expire(String key, int seconds) {
		Long result = redisString.expire(key, seconds);
		if (result == 0) {
			//
		}
		return result;
	}

	@Override
	public Long ttl(String key) {
		throw new NotImplementedException("not impl.");
	}

	@Override
	public Long incr(String key) {
		return this.redisString.incr(key);
	}

	@Override
	public String get(String key) {
		return this.redisString.get(key);
	}

	@Override
	public String getSet(String key, String value) {
		String current = this.get(key);
		this.set(key, value);
		return current;
	}

	@Override
	public Long zcard(String key) {
		return this.redisSortedSet.zcard(key);
	}

	@Override
	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		return this.redisSortedSet.zrevrangeWithScores(key, start, end);
	}

	@Override
	public Set<String> zrevrange(String key, long start, long end) {
		return this.redisSortedSet.zrevrange(key, start, end);
	}

	@Override
	public Set<String> zrange(String key, long start, long end) {
		return this.redisSortedSet.zrange(key, start, end);
	}

	@Override
	public Long zadd(String key, double score, String member) {
		return this.redisSortedSet.zadd(key, score, member);
	}

	@Override
	public Long zadd(String key, Map<String, Double> scoreMembers) {
		throw new NotImplementedException("not impl.");
	}

	@Override
	public Long srem(String key, String... member) {
		return this.redisSet.srem(key, member);
	}

	@Override
	public Boolean exists(String key) {
		if (this.redisString.exists(key)) {
			return true;
		}
		else if (this.redisList.exists(key)) {
			return true;
		}
		else if (this.redisSet.exists(key)) {
			return true;
		}
		else if (this.redisSortedSet.exists(key)) {
			return true;
		}
		else if (this.redisHashes.exists(key)) {
			return true;
		}
		return false;
	}

	@Override
	public String type(String key) {
		throw new NotImplementedException("not impl.");

	}

	@Override
	public Long expireAt(String key, long unixTime) {
		throw new NotImplementedException("not impl.");

	}

	@Override
	public Boolean setbit(String key, long offset, boolean value) {
		throw new NotImplementedException("not impl.");

	}

	@Override
	public Boolean getbit(String key, long offset) {
		throw new NotImplementedException("not impl.");

	}

	@Override
	public String getrange(String key, long startOffset, long endOffset) {
		String value = this.get(key);
		if (StringUtils.isEmpty(value)) {
			return null;
		}

		if (startOffset < 0) {
			startOffset = value.length() + startOffset;
		}
		// throw new NotImplementedException();
		if (endOffset == -1) {
			return value.substring((int) startOffset);
		}

		int endIndex = (int) endOffset + 1;
		if (endIndex > value.length()) {
			endIndex = value.length();
		}

		return value.substring((int) startOffset, endIndex);
	}

	@Override
	public Long setnx(String key, String value) {
		return redisString.setnx(key, value);
	}

	@Override
	public String setex(String key, int seconds, String value) {
		return this.redisString.setex(key, seconds, value);
	}

	@Override
	public Long decrBy(String key, long integer) {
		return this.redisString.decrBy(key, integer);
	}

	@Override
	public Long decr(String key) {
		return this.redisString.decr(key);
	}

	@Override
	public Long incrBy(String key, long integer) {
		return this.redisString.incrBy(key, integer);
	}

	@Override
	public String substr(String key, int start, int end) {
		throw new NotImplementedException("not impl.");
	}

	@Override
	public Long hset(String key, String field, String value) {
		return redisHashes.hset(key, field, value);
	}

	@Override
	public String hget(String key, String field) {
		return redisHashes.hget(key, field);
	}

	@Override
	public Long hsetnx(String key, String field, String value) {
		return redisHashes.hsetnx(key, field, value);
	}

	@Override
	public String hmset(String key, Map<String, String> hash) {
		return redisHashes.hmset(key, hash);
	}

	@Override
	public List<String> hmget(String key, String... fields) {
		return redisHashes.hmget(key, fields);
	}

	@Override
	public Long hincrBy(String key, String field, long value) {
		return redisHashes.hincrBy(key, field, value);
	}

	@Override
	public Boolean hexists(String key, String field) {
		return redisHashes.hexists(key, field);
	}

	@Override
	public Long hdel(String key, String... fields) {
		return redisHashes.hdel(key, fields);
	}

	@Override
	public Long hlen(String key) {
		return redisHashes.hlen(key);
	}

	@Override
	public Set<String> hkeys(String key) {
		return redisHashes.hkeys(key);
	}

	@Override
	public List<String> hvals(String key) {
		return redisHashes.hvals(key);
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		return redisHashes.hgetAll(key);
	}

	@Override
	public Long rpush(String key, String... strings) {
		return this.redisList.rpush(key, strings);
	}

	@Override
	public Long lpush(String key, String... strings) {
		return this.redisList.lpush(key, strings);
	}

	@Override
	public Long llen(String key) {
		return this.redisList.llen(key);
	}

	@Override
	public List<String> lrange(String key, long start, long end) {
		return this.redisList.lrange(key, start, end);
	}

	@Override
	public String ltrim(String key, long start, long end) {
		return this.redisList.ltrim(key, start, end);
	}

	@Override
	public String lindex(String key, long index) {
		return this.redisList.lindex(key, index);
	}

	@Override
	public String lset(String key, long index, String value) {
		return this.redisList.lset(key, index, value);
	}

	@Override
	public Long lrem(String key, long count, String value) {
		return this.redisList.lrem(key, count, value);
	}

	@Override
	public String lpop(String key) {
		return this.redisList.lpop(key);
	}

	@Override
	public String rpop(String key) {
		return this.redisList.rpop(key);
	}

	@Override
	public Set<String> smembers(String key) {
		return this.redisSet.smembers(key);
	}

	@Override
	public String spop(String key) {
		return this.redisSet.spop(key);
	}

	@Override
	public Long scard(String key) {
		return this.redisSet.scard(key);
	}

	@Override
	public Boolean sismember(String key, String member) {
		return this.redisSet.sismember(key, member);
	}

	@Override
	public String srandmember(String key) {
		return this.redisSet.srandmember(key);
	}

	@Override
	public Long zrem(String key, String... members) {
		return redisSortedSet.zrem(key, members);
	}

	@Override
	public Double zincrby(String key, double score, String member) {
		return redisSortedSet.zincrby(key, score, member);
	}

	@Override
	public Long zrank(String key, String member) {
		return redisSortedSet.zrank(key, member);
	}

	@Override
	public Long zrevrank(String key, String member) {
		return redisSortedSet.zrevrank(key, member);
	}

	@Override
	public Set<Tuple> zrangeWithScores(String key, long start, long end) {
		return redisSortedSet.zrangeWithScores(key, start, end);
	}

	@Override
	public Double zscore(String key, String member) {
		return redisSortedSet.zscore(key, member);
	}

	@Override
	public Double zscore(final String key, final long member) {
		return this.zscore(key, Long.toString(member));
	}

	@Override
	public List<String> sort(String key) {
		throw new NotImplementedException("not impl.");

	}

	@Override
	public Long zcount(String key, double min, double max) {
		return redisSortedSet.zcount(key, min, max);
	}

	@Override
	public Long zcount(String key, String min, String max) {
		return redisSortedSet.zcount(key, min, max);
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max) {
		return redisSortedSet.zrangeByScore(key, min, max);
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max) {
		return redisSortedSet.zrangeByScore(key, min, max);
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
		return redisSortedSet.zrangeByScore(key, min, max, offset, count);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min) {
		return redisSortedSet.zrevrangeByScore(key, max, min);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
		return redisSortedSet.zrevrangeByScore(key, max, min, offset, count);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min) {
		return redisSortedSet.zrevrangeByScore(key, max, min);
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
		return redisSortedSet.zrangeByScore(key, min, max, offset, count);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
		return redisSortedSet.zrevrangeByScore(key, max, min, offset, count);
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		return redisSortedSet.zrangeByScoreWithScores(key, min, max);
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
		return redisSortedSet.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
		return redisSortedSet.zrangeByScoreWithScores(key, min, max);
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
		return redisSortedSet.zrangeByScoreWithScores(key, min, max);
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
		return redisSortedSet.zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
		return redisSortedSet.zrevrangeByScoreWithScores(key, max, min);
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
		return redisSortedSet.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
		return redisSortedSet.zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	@Override
	public Long zremrangeByRank(String key, long start, long end) {
		return redisSortedSet.zremrangeByRank(key, start, end);
	}

	@Override
	public Long zremrangeByScore(String key, double start, double end) {
		return redisSortedSet.zremrangeByScore(key, start, end);
	}

	@Override
	public Long zremrangeByScore(String key, String start, String end) {
		return redisSortedSet.zremrangeByScore(key, start, end);
	}

	@Override
	public Long lpushx(String key, String string) {
		return this.redisList.lpushx(key, string);
	}

	@Override
	public Long rpushx(String key, String string) {
		return this.redisList.rpushx(key, string);
	}

	@Override
	public boolean append(String key, String value, int seconds) {
		this.append(key, value);
		this.expire(key, seconds);
		return true;
	}

	@Override
	public boolean rename(String oldkey, String newkey) {
		throw new NotImplementedException("not impl.");
	}

	@Override
	public Long setrange(String key, long offset, String value) {
		return this.redisString.setrange(key, offset, value);
	}

	@Override
	public Long setrange(String key, int offset, String value) {
		return this.setrange(key, (long) offset, value);
	}

	@Override
	public Long sadd(String key, String... members) {
		return this.redisSet.sadd(key, members);
	}

	@Override
	public boolean flushDB() {
		return this.redisString.flushAll();
		// return true;
	}

	@Override
	public boolean rename(String oldkey, String newkey, int seconds) {
		throw new NotImplementedException("not impl.");

	}

	@Override
	public String set(String key, String value, int seconds) {
		return this.setex(key, seconds, value);
	}

	@Override
	public boolean set(List<String> keyList, List<String> valueList) {
		for (int i = 0; i < keyList.size(); i++) {
			String key = keyList.get(i);
			String value = valueList.get(i);
			this.set(key, value);
		}
		return true;
	}

	@Override
	public boolean append(List<String> keyList, List<String> valueList, int seconds) {
		throw new NotImplementedException("not impl.");

	}

	@Override
	public List<String> mget(String... keys) {
		List<String> list = new ArrayList<String>();
		for (String key : keys) {
			list.add(this.get(key));
		}
		return list;
	}

	@Override
	public Long zinterstore(String dstkey, String... sets) {
		return this.redisSortedSet.zinterstore(dstkey, sets);
	}

	@Override
	public Set<String> keys(String pattern) {
		throw new NotImplementedException("not impl.");
	}

	@Override
	public Long zunionstore(String dstkey, String... sets) {
		return this.redisSortedSet.zunionstore(dstkey, sets);
	}

	@Override
	public String hget(String key, long field) {
		return this.hget(key, Long.toString(field));
	}

	@Override
	public Long hset(String key, long field, String value) {
		return this.hset(key, Long.toString(field), value);
	}

	@Override
	public Long hdel(String key, long field) {
		return this.hdel(key, Long.toString(field));
	}

	@Override
	public Long zadd(String key, double score, long member) {
		return redisSortedSet.zadd(key, score, Long.toString(member));
	}

	@Override
	public Long zrem(String key, long member) {
		return redisSortedSet.zrem(key, member);
	}

	@Override
	public Set<String> zunionStoreInJava(String... sets) {
		return redisSortedSet.zunionStoreInJava(sets);
	}

	@Override
	public Set<String> zunionStoreByScoreInJava(double min, double max, String... sets) {
		return redisSortedSet.zunionStoreByScoreInJava(min, max, sets);
	}

	@Override
	public Set<String> sdiff(String... keys) {
		return this.redisSet.sdiff(keys);
	}

	@Override
	public String randomKey() {
		throw new NotImplementedException("not impl.");
	}

	@Override
	public Long persist(String key) {
		throw new NotImplementedException("not impl.");
	}

	@Override
	public Boolean setbit(String key, long offset, String value) {
		throw new NotImplementedException("not impl.");
	}

	@Override
	public Long strlen(String key) {
		throw new NotImplementedException("not impl.");
	}

	@Override
	public Long lpushx(String key, String... string) {
		throw new NotImplementedException("not impl.");
	}

	@Override
	public Long rpushx(String key, String... string) {
		throw new NotImplementedException("not impl.");
	}

	@Override
	public List<String> blpop(String arg) {
		throw new NotImplementedException("not impl.");
	}

	@Override
	public List<String> brpop(String arg) {
		throw new NotImplementedException("not impl.");
	}

	@Override
	public String echo(String string) {
		throw new NotImplementedException("not impl.");
	}

	@Override
	public Long move(String key, int dbIndex) {
		throw new NotImplementedException("not impl.");
	}

	@Override
	public Long bitcount(String key) {
		throw new NotImplementedException("not impl.");
	}

	@Override
	public Long bitcount(String key, long start, long end) {
		throw new NotImplementedException("not impl.");
	}

}
