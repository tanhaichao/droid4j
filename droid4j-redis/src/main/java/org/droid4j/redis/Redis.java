package org.droid4j.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Redis {

	public String set(String key, String value);

	public String get(String key);

	public Boolean exists(String key);

	public String type(String key);

	public Long expire(String key, int seconds);

	public Long expireAt(String key, long unixTime);

	public Long ttl(String key);

	public Boolean setbit(String key, long offset, boolean value);

	public Boolean getbit(String key, long offset);

	public Long setrange(String key, long offset, String value);

	public String getrange(String key, long startOffset, long endOffset);

	public String getSet(String key, String value);

	public Long setnx(String key, String value);

	public String setex(String key, int seconds, String value);

	public Long decrBy(String key, long integer);

	public Long decr(String key);

	public Long incrBy(String key, long integer);

	public Long incr(String key);

	public Long append(String key, String value);

	public boolean append(String key, String value, int seconds);

	public String substr(String key, int start, int end);

	public Long hset(String key, String field, String value);

	public String hget(String key, String field);

	public Long hsetnx(String key, String field, String value);

	public String hmset(String key, Map<String, String> hash);

	public List<String> hmget(String key, String... fields);

	public Long hincrBy(String key, String field, long value);

	public Boolean hexists(String key, String field);

	public Long hdel(String key, String... fields);

	public Long hlen(String key);

	public Set<String> hkeys(String key);

	public List<String> hvals(String key);

	public Map<String, String> hgetAll(String key);

	public Long rpush(String key, String... strings);

	public Long lpush(String key, String... strings);

	public Long llen(String key);

	public List<String> lrange(String key, long start, long end);

	public String ltrim(String key, long start, long end);

	public String lindex(String key, long index);

	public String lset(String key, long index, String value);

	public Long lrem(String key, long count, String value);

	public String lpop(String key);

	public String rpop(String key);

	public Long sadd(String key, String... members);

	public Set<String> smembers(String key);

	public Long srem(String key, String... members);

	public String spop(String key);

	public Long scard(String key);

	public Boolean sismember(String key, String member);

	public String srandmember(String key);

	public Long zadd(String key, double score, String member);

	public Set<String> zrange(String key, long start, long end);

	public Long zrem(String key, String... members);

	public Double zincrby(String key, double score, String member);

	public Long zrank(String key, String member);

	public Long zrevrank(String key, String member);

	public Set<String> zrevrange(String key, long start, long end);

	public Set<Tuple> zrangeWithScores(String key, long start, long end);

	public Set<Tuple> zrevrangeWithScores(String key, long start, long end);

	public Long zcard(String key);

	public Double zscore(String key, String member);

	public Double zscore(final String key, final long member);

	public List<String> sort(String key);

	public Long zcount(String key, double min, double max);

	public Set<String> zrangeByScore(String key, double min, double max);

	public Set<String> zrevrangeByScore(String key, double max, double min);

	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count);

	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count);

	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max);

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min);

	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count);

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count);

	public Long zremrangeByRank(String key, long start, long end);

	public Long zremrangeByScore(String key, double start, double end);

	public boolean rename(String oldkey, String newkey);

	public boolean flushDB();

	public boolean rename(String oldkey, String newkey, int seconds);

	public String set(String key, String value, int seconds);

	public boolean set(List<String> keyList, List<String> valueList);

	public boolean append(List<String> keyList, List<String> valueList, int seconds);

	public Long del(String key);

	public Long del(String... keys);

	public List<String> mget(String... keys);

	public Long zinterstore(String dstkey, String... sets);

	public Set<String> keys(String pattern);

	public Long zunionstore(String dstkey, String... sets);

	public String hget(String key, long field);

	public Long hset(String key, long field, String value);

	public Long hdel(String key, long field);

	public Long zadd(String key, double score, long member);

	public Long zrem(String key, long member);

	public Set<String> zunionStoreInJava(String... sets);

	public Set<String> zunionStoreByScoreInJava(double min, double max, String... sets);

	public Long zadd(String key, Map<String, Double> scoreMembers);

	public Long zcount(String key, String min, String max);

	public Set<String> zrangeByScore(String key, String min, String max);

	public Set<String> zrevrangeByScore(String key, String max, String min);

	public Set<String> zrangeByScore(String key, String min, String max, int offset, int count);

	public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count);

	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max);

	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min);

	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count);

	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count);

	public Long zremrangeByScore(String key, String start, String end);

	public Long lpushx(String key, String string);

	public Long rpushx(String key, String string);

	public Long setrange(String key, int offset, String value);

	public Set<String> sdiff(String... keys);

	public String randomKey();

	public Long persist(String key);

	public Boolean setbit(String key, long offset, String value);

	public Long strlen(String key);

	public Long lpushx(String key, String... string);

	public Long rpushx(String key, String... string);

	public List<String> blpop(String arg);

	public List<String> brpop(String arg);

	public String echo(String string);

	public Long move(String key, int dbIndex);

	public Long bitcount(String key);

	public Long bitcount(String key, long start, long end);

}
