package com.example.administrator.myapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


import com.example.administrator.myapp.application.MyApplication;
import com.example.administrator.myapp.mod.EBmessage;
import com.example.administrator.myapp.mod.TokenMod;
import com.google.gson.Gson;

import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Random;

import io.rong.eventbus.EventBus;


public class HttpUtil {

	private static final String RY_APP_KEY = "p5tvi9dstirc4";
	private static final String RY_APP_SECRET = "zpDyVBmq7OA";

//	public static SharedPreferences getSharedPreferences(Context context) {
//		SharedPreferences sharedPreferences = SCApplication.getObjectContext()
//				.getSharedPreferences("user", Context.MODE_PRIVATE);
//		return sharedPreferences;
//	}
//
//	public static Editor getEditor() {
//		Editor editor = getSharedPreferences(SCApplication.getObjectContext())
//				.edit();
//		return editor;
//	}

//	public static String getUserName() {
//
//		return getSharedPreferences(SCApplication.getObjectContext())
//				.getString("username", "");
//	}
//
//	public static String getUserPass() {
//		return getSharedPreferences(SCApplication.getObjectContext())
//				.getString("userpass", "");
//	}
//
//	public static String getUserToken() {
//
//		return getSharedPreferences(SCApplication.getObjectContext())
//				.getString("token", "");
//	}

	public static RequestParams addHeader(RequestParams params) {
		Random r = new Random();
		String Nonce = (r.nextInt(10000) + 10000) + "";
		String Timestamp = (System.currentTimeMillis() / 1000) + "";
		params.addHeader("App-Key", RY_APP_KEY);
		params.addHeader("Nonce", Nonce);
		params.addHeader("Timestamp", Timestamp);
		params.addHeader("Signature",
				MD5.encryptToSHA(RY_APP_SECRET + Nonce + Timestamp));

		return params;
	}

	public static void getToken(final String id, final String username) {
		RequestParams params = new RequestParams(
				"https://api.cn.ronghub.com/user/getToken.json");
		addHeader(params);
		params.addBodyParameter("userId", id);
		params.addBodyParameter("name", username);
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				EBmessage eb = new EBmessage();
				eb.setStatus(false);
				eb.setMessage(arg0.toString());
				eb.setFrom("getToken");
				EventBus.getDefault().post(eb);
			}

			@Override
			public void onFinished() {
			}

			@Override
			public void onSuccess(String s) {
				TokenMod mod = new Gson().fromJson(s, TokenMod.class);
				SharedPreferences sharedPreferences = MyApplication.getObjxectContext()
				.getSharedPreferences("user", Context.MODE_PRIVATE);
				Editor editor=sharedPreferences.edit();
				editor.putString("username", username);
				editor.putString("userId", mod.getUserId());
				editor.putString("token", mod.getToken());
				editor.commit();

				EBmessage eb = new EBmessage();
				eb.setStatus(true);
				eb.setMessage(mod.getToken());
				eb.setFrom("getToken");
				EventBus.getDefault().post(eb);
			}
		});
	}

	/**
	 * 建立与融云服务器的连接
	 * @param token
	 */
//	public static void connect(String token, Context context) {
//
//		if (context.getApplicationInfo().packageName.equals(SCApplication
//				.getCurProcessName(context.getApplicationContext()))) {
//
//			/**
//			 * IMKit SDK调用第二步,建立与服务器的连接
//			 */
//			RongIM.connect(token, new RongIMClient.ConnectCallback() {
//
//				/**
//				 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的
//				 * Token
//				 */
//				@Override
//				public void onTokenIncorrect() {
//					Log.d("LoginActivity", "--onTokenIncorrect");
//				}
//
//				/**
//				 * 连接融云成功
//				 *
//				 * @param userid
//				 *            当前 token
//				 */
//				@Override
//				public void onSuccess(String userid) {
//					EBmessage eb = new EBmessage();
//					eb.setStatus(true);
//					eb.setMessage("success");
//					eb.setFrom("connect");
//					EventBus.getDefault().post(eb);
//					Log.d("LoginActivity", "--onSuccess" + userid);
//				}
//
//				/**
//				 * 连接融云失败
//				 *
//				 * @param errorCode
//				 *            错误码，可到官网 查看错误码对应的注释
//				 */
//				@Override
//				public void onError(RongIMClient.ErrorCode errorCode) {
//					Log.d("LoginActivity", "--onError" + errorCode);
//				}
//			});
//		}
//	}
}
