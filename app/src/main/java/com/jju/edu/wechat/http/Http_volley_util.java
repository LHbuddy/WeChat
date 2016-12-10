package com.jju.edu.wechat.http;

import java.util.HashMap;
import java.util.Map;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;

public class Http_volley_util {
	private static RequestQueue queue;
	private static Context context;

	private static String path_1 = "http://10.107.251.85:8080/WeChat/servlet/";
	private static String iamge_path = "http://192.168.1.105:8080/BookBox/drawable/";

	public static void getRequstQueue(Context context) {
		Http_volley_util.context = context;
		if (queue == null) {
			queue = Volley.newRequestQueue(context);
		}
	}

	public static void DoGet(String classname, Map<String, String> oMap) {

		StringBuilder builder = new StringBuilder(path_1);
		builder.append(classname).append("?");
		for (Map.Entry<String, String> str : oMap.entrySet()) {
			String key = str.getKey();
			String value = str.getValue();
			builder.append(key).append("=").append(value).append("&");
		}

		builder.deleteCharAt(builder.length() - 1);

		StringRequest request = new StringRequest(Method.GET,
				builder.toString(), new Response.Listener<String>() {

					@Override
					public void onResponse(String info) {
						Http_volley_util.rInfo.result(info);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						error.printStackTrace();
						Toast.makeText(context, "获取数据失败", Toast.LENGTH_SHORT).show();
					}
				});
		request.setTag("get");
		queue.add(request);
	}

	public interface Result_info {
		public void result(String info);

		public void result_bitmap(Bitmap bitmap);
	}

	private static Result_info rInfo;

	public static void setResult_info(Result_info result_info) {
		Http_volley_util.rInfo = result_info;
	}

	public static void doPost(String classname, final Map<String, String> oMap) {
		StringBuilder builder = new StringBuilder(path_1);
		builder.append(classname);
		StringRequest request = new StringRequest(Method.POST,
				builder.toString(), new Response.Listener<String>() {

					@Override
					public void onResponse(String info) {
						Http_volley_util.rInfo.result(info);

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						error.printStackTrace();
						Toast.makeText(context, "获取数据失败", Toast.LENGTH_SHORT).show();
					}
				}) {

			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return oMap;
			}
		};
		request.setTag("post");
		queue.add(request);
	}

	public static void image_Requst(String image_name) {
		StringBuilder builder = new StringBuilder(iamge_path);
		builder.append(image_name);
		ImageRequest imageRequest = new ImageRequest(builder.toString(),
				new Response.Listener<Bitmap>() {

					@Override
					public void onResponse(Bitmap arg0) {
						Http_volley_util.rInfo.result_bitmap(arg0);
					}
				}, 300, 200, Config.ARGB_8888, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Toast.makeText(context, "获取数据失败", Toast.LENGTH_SHORT).show();
					}
				});
		imageRequest.setTag("image");
		queue.add(imageRequest);
	}

	static Map<String, Object> oMap = new HashMap<String, Object>();



	public static void network_image(String image_name, NetworkImageView view) {
		view.setImageUrl(new StringBuilder(iamge_path).append(image_name)
				.toString(), new ImageLoader(queue, new ImageCache() {

			@Override
			public void putBitmap(String key, Bitmap value) {
				oMap.put(key, value);
			}

			@Override
			public Bitmap getBitmap(String key) {
				return (Bitmap) oMap.get(key);
			}
		}));
	}

}
