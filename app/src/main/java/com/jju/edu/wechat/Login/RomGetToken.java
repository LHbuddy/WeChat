package com.jju.edu.wechat.Login;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RomGetToken {
	/**
	 * 获取token
	 */
//	private static String path="https://api.cn.rong.io/user/getToken.json";
//	private final static String KEY="x18ywvqf8w2kc" ;
//	private final static String SECRET="XNTAfFVbONWcZa";
	private static String path="https://api.cn.rong.io/user/getToken.json";
	private final static String KEY="0vnjpoadnpzlz" ;
	private final static String SECRET="eugqtIOE5YvqCq";
	public static String  getTokenStr(String userid,String name,String urlpic){
		try {
			URL url=new URL(path);
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.addRequestProperty("App-Key",KEY);
			conn.addRequestProperty("appSecret", SECRET);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			String json="userId="+userid+"&name="+name+"&portraitUri="+urlpic;
			OutputStream outputStream=conn.getOutputStream();
			outputStream.write(json.getBytes("utf-8"));
			System.out.println(conn.getResponseCode()+"===");
			outputStream.close();
			InputStream is=conn.getInputStream();
			byte[] b=new byte[5*1024];
			int len;
			StringBuffer sb=new StringBuffer("");
			while((len=is.read(b))!=-1){
				sb.append(new String(b, 0, len));
			}
			System.out.println(sb.toString());
			is.close();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		

	}




}
