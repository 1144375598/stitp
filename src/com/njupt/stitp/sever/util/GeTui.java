package com.njupt.stitp.sever.util;

import org.springframework.stereotype.Component;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;

public class GeTui {
	private static String appId = "aK6jeksP5C7CsjSSEqLAA3";
	private static String appKey = "tpDVam96sY8pxhwBupJ462";
	private static String masterSecret = "TBokfpttQJ6aHIhBE9y867";
	private static String url = "http://sdk.open.api.igexin.com/apiex.htm";
	public static String getAppId() {
		return appId;
	}
	public static void setAppId(String appId) {
		GeTui.appId = appId;
	}
	public static String getAppKey() {
		return appKey;
	}
	public static void setAppKey(String appKey) {
		GeTui.appKey = appKey;
	}
	public static String getMasterSecret() {
		return masterSecret;
	}
	public static void setMasterSecret(String masterSecret) {
		GeTui.masterSecret = masterSecret;
	}
	public static String getUrl() {
		return url;
	}
	public static void setUrl(String url) {
		GeTui.url = url;
	}
	public static String pushMessage(String cid,String message){
		IGtPush push = new IGtPush(url, appKey, masterSecret);
		SingleMessage singleMessage = new SingleMessage();
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		template.setTransmissionContent(message);
		template.setTransmissionType(1);
		singleMessage.setData(template);
		singleMessage.setOffline(true);
		singleMessage.setOfflineExpireTime(1800 * 1000);
		Target target = new Target();
		target.setAppId(appId);
		target.setClientId(cid);
		IPushResult result = push.pushMessageToSingle(singleMessage, target);
		String response = result.getResponse().toString();
		return response;
	}
}
