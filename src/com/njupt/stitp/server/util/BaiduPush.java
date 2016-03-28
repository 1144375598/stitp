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
	 * 1. ����PushKeyPair����app�ĺϷ������֤apikey��secretKey����Ӧ�������л�ȡ
	 */
	private static String apiKey = "ebI4sTvwMxkiayc62NCO3NwR";
	private static String secretKey = "jX3XVhd6S3girG3UUL3rOPiScy8dpDRl";
	private static PushKeyPair pair = new PushKeyPair(apiKey, secretKey);

	// 2. ����BaiduPushClient������SDK�ӿ�
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
		// 3. ע��YunLogHandler����ȡ��������Ľ�����Ϣ
		pushClient.setChannelLogHandler(new YunLogHandler() {
			@Override
			public void onHandle(YunLogEvent event) {
				System.out.println(event.getMessage());
			}
		});
		String msg = new Gson().toJson(params);
		System.out.println("json��" + msg);
		try {
			// 4. ���������������������ʵ��
			PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest()
					.addChannelId(cid).addMsgExpires(msgExpire)
					// ������Ϣ����Чʱ��,��λ��,Ĭ��3600*5.
					.addMessageType(0)
					// ������Ϣ����,0��ʾ͸����Ϣ,1��ʾ֪ͨ,Ĭ��Ϊ0.
					.addMessage(msg).addDeviceType(3); // �����豸���ͣ�deviceType => 1
			// for web, 2 for pc,
			// 3 for android, 4 for
			// ios, 5 for wp.
			// 5. ִ��Http����
			PushMsgToSingleDeviceResponse response = pushClient
					.pushMsgToSingleDevice(request);
			// 6. Http���󷵻�ֵ����
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
