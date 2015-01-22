package io.leopard.droid4j.data.hyper;

import io.leopard.burrow.httpnb.Https;
import io.leopard.burrow.util.StringUtil;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class TestClient {
	public static void main(String[] args) {
		TestClient client = new TestClient();
		client.upload();
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

	private void upload() {
		String loginedCookie = toLoginCookie("b56ec6cb-c62a-4f47-afe2-70ffc5947583", "hctan", 1, "MzIwOTJiOGVlZTJmNTQ4NzM3ZjQyMWJhYzMxMGZhNDI0NDZjY2ViODoxNDEwNTEyODAyMTM1");

		Https.trustAllHosts();
		try {
			String boundary = "------WebKitFormBoundaryUey8ljRiiZqhZHBu";
			String url = "https://olla.laopao.com/share/add.do";
			URL u = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();

			((HttpsURLConnection) conn).setHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});

			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);

			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Cookie", loginedCookie);
			// conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			// 指定流的大小，当内容达到这个值的时候就把流输出
			// conn.setChunkedStreamingMode(10240);
			OutputStream out = new DataOutputStream(conn.getOutputStream());
			byte[] end_data = ("\r\n--" + boundary + "--\r\n").getBytes();// 定义最后数据分隔线
			List<String> list = new ArrayList<String>();
			// list.add("e:/userInfo.properties");
			// list.add("e:/email.html");
			StringBuilder sb = new StringBuilder();
			// 添加form属性
			sb.append("--");
			sb.append(boundary);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data; name=\"content\"");
			sb.append("\r\n\r\n");
			sb.append("hello word");
			out.write(sb.toString().getBytes("utf-8"));
			out.write("\r\n".getBytes("utf-8"));

			int leng = list.size();
			for (int i = 0; i < leng; i++) {
				String fname = list.get(i);
				File file = new File(fname);
				sb = new StringBuilder();
				sb.append("--");
				sb.append(boundary);
				sb.append("\r\n");
				sb.append("Content-Disposition: form-data;name=\"file" + i + "\";filename=\"" + file.getName() + "\"\r\n");
				sb.append("Content-Type:application/octet-stream\r\n\r\n");
				byte[] data = sb.toString().getBytes();
				out.write(data);
				DataInputStream in = new DataInputStream(new FileInputStream(file));
				int bytes = 0;
				byte[] bufferOut = new byte[1024];
				while ((bytes = in.read(bufferOut)) != -1) {
					out.write(bufferOut, 0, bytes);
				}
				out.write("\r\n".getBytes()); // 多个文件时，二个文件之间加入这个
				in.close();
			}
			out.write(end_data);
			out.flush();
			out.close();
			// 定义BufferedReader输入流来读取URL的响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		}
		catch (Exception e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		}
	}
}
