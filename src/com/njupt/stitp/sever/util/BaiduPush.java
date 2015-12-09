package com.njupt.stitp.sever.util;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;

public class BaiduPush {
	/*
	 * 1. ����PushKeyPair����app�ĺϷ������֤apikey��secretKey����Ӧ�������л�ȡ
	 */
	private static String apiKey = " BncGKsUxUdukVDGGwGogcWbe";
	private static String secretKey = "vGpYviHFZPGMVyFYd9S3Y5GkEVSAy1Cf";
	private static PushKeyPair pair = new PushKeyPair(apiKey, secretKey);

	// 2. ����BaiduPushClient������SDK�ӿ�
	private static BaiduPushClient pushClient = new BaiduPushClient(pair,
			"api.push.baidu.com");

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

	static public String PushMsgToSingle(String cid, String msg) {
		// 3. ע��YunLogHandler����ȡ��������Ľ�����Ϣ
		pushClient.setChannelLogHandler(new YunLogHandler() {
			@Override
			public void onHandle(YunLogEvent event) {
				System.out.println(event.getMessage());
			}
		});

		try {
			// 4. ���������������������ʵ��
			PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest()
					.addChannelId(cid).addMsgExpires(new Integer(86400 * 7)). // ������Ϣ����Чʱ��,��λ��,Ĭ��3600*5.
					addMessageType(0). // ������Ϣ����,0��ʾ͸����Ϣ,1��ʾ֪ͨ,Ĭ��Ϊ0.
					addMessage(msg).addDeviceType(3); // �����豸���ͣ�deviceType => 1
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
