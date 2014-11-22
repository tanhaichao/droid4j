package org.droid4j.data.hyper;

import org.junit.Test;

public class HttpPostUtilTest {

	@Test
	public void HttpPostUtil() throws Exception {
		HttpPostUtil http = new HttpPostUtil("http://olla.laopao.com/share/add.do");
		http.addTextParameter("content", "content");

		String json = new String(http.send());
		System.out.println("json :" + json);
	}

}