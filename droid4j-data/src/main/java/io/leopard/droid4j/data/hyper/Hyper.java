package io.leopard.droid4j.data.hyper;

import io.leopard.burrow.httpnb.Param;
import io.leopard.burrow.lang.Paging;

import java.util.List;

/**
 * HTTP接口.
 * 
 * @author 阿海
 *
 */
public interface Hyper {

	long getSessUid();

	boolean add(String url, Param... params);

	boolean update(String url, Param... params);

	boolean update2(String url, List<Param> paramList);

	String uploadForUrl(String url, String path, Param... params);

	String uploadForData(String url, List<String> fileList, Param... params);

	boolean delete(String url, Param... params);

	<T> T query(String url, Class<T> clazz, Param... params);

	<T> T queryForNext(String url, Class<T> clazz, Param... params);

	boolean queryForBoolean(String url, Param... params);

	<T> List<T> queryForList(String url, Class<T> clazz, Param... params);

	<T> Paging<T> queryForPaging(String url, Class<T> clazz, Param... params);

	boolean remove(Param... params);

}
