package com.njupt.stitp.server.util;

import java.util.Map;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;
import com.google.gson.Gson;

public class BaiduPush {
	/*
	 * 1. 创建PushKeyPair用于app的合法身份认证apikey和secretKey可在应用详情中获取
	 */
	private static String apiKey = "ebI4sTvwMxkiayc62NCO3NwR";
	private static String secretKey = "jX3XVhd6S3girG3UUL3rOPiScy8dpDRl";
	private static PushKeyPair pair = new PushKeyPair(apiKey, secretKey);

	// 2. 创建BaiduPushClient，访问SDK接口
	private static BaiduPushClient pushClient = new BaiduPushClient(pair,
			BaiduPushConstants.CHANNEL_REST_URL);

	public String getApiKey() {
		return apiKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public PushKeyPair getPair() {
		return pair;
	}

	public BaiduPushClient getPushClient() {
		return pushClient;
	}

	static public String pushMsgToSingle(String cid,
			Map<String, String> params, int msgExpire) {
		// 3. 注册YunLogHandler，获取本次请求的交互信息
		pushClient.setChannelLogHandler(new YunLogHandler() {
			@Override
			public void onHandle(YunLogEvent event) {
				System.out.println(event.getMessage());
			}
		});
		String msg = new Gson().toJson(params);
		System.out.println("json串" + msg);
		try {
			// 4. 设置请求参数，创建请求实例
			PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest()
					.addChannelId(cid).addMsgExpires(msgExpire)
					// 设置消息的有效时间,单位秒,默认3600*5.
					.addMessageType(0)
					// 设置消息类型,0表示透传消息,1表示通知,默认为0.
					.addMessage(msg).addDeviceType(3); // 设置设备类型，deviceType => 1
			// for web, 2 for pc,
			// 3 for android, 4 for
			// ios, 5 for wp.
			// 5. 执行Http请求
			PushMsgToSingleDeviceResponse response = pushClient
					.pushMsgToSingleDevice(request);
			// 6. Http请求返回值解析
			return "Success";
		} catch (PushClientException e) {
			e.printStackTrace();
			return "Failure";
		} catch (PushServerException e) {
			System.out.println(String.format(
					"requestId: %d, errorCode: %d, errorMsg: %s",
					e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
			return "Failure";
		}
	}

}
