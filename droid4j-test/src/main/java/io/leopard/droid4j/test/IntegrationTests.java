package io.leopard.droid4j.test;

import io.leopard.burrow.util.StringUtil;
import io.leopard.droid4j.data.UserSession;
import io.leopard.droid4j.data.ioc.AutowiredInjecter;
import io.leopard.droid4j.sqlite.java.SqliteHelperJavaImpl;

import java.io.File;

public class IntegrationTests {

	static {
		// {"status":"200","message":"","data":{"uid":1,"username":"hctan","sessionId":"b56ec6cb-c62a-4f47-afe2-70ffc5947583","token":"MzIwOTJiOGVlZTJmNTQ4NzM3ZjQyMWJhYzMxMGZhNDI0NDZjY2ViODoxNDEwNTEyODAyMTM1","nickname":"阿海","gender":"male","avatar":"https://olla.laopao.com/image.do?f=/user/avatar/b2341827ed8d4b80ac1a28b32cf4e35a.jpg"}}
		String loginedCookie = toLoginCookie("b56ec6cb-c62a-4f47-afe2-70ffc5947582", "hctan", 1, "MzIwOTJiOGVlZTJmNTQ4NzM3ZjQyMWJhYzMxMGZhNDI0NDZjY2ViODoxNDEwNTEyODAyMTM1");
		UserSession.setLoginedInfo(1, loginedCookie);
		UserSession.setSqliteHelper(new SqliteHelperJavaImpl());
		UserSession.setRootDir(new File("/tmp/android/"));
	}

	public static String toLoginCookie(String sessionId, String username, long uid, String token) {
		// AssertUtil.assertNotNull(loginAuth, "没有登陆信息.");
		StringBuilder sb = new StringBuilder();
		sb.append("SESSIONID=" + sessionId);
		sb.append(";username=" + StringUtil.urlEncode(username));
		sb.append(";uid=" + uid);
		sb.append(";token=" + StringUtil.urlEncode(token));
		return sb.toString();
	}

	public IntegrationTests() {
		this.inject();
	}

	protected void inject() {
		new AutowiredInjecter().inject(this);
	}
}
