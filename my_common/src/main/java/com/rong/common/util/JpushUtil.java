package com.rong.common.util;

import org.apache.log4j.Logger;

import com.rong.common.bean.MyConst;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class JpushUtil {
	private static final Logger logger = Logger.getLogger(JpushUtil.class);

	public static JPushClient jpushClient;

	@SuppressWarnings("deprecation")
	public static void getjpushClient() {
		if (jpushClient == null) {
			jpushClient = new JPushClient(MyConst.jpush_masterSecret, MyConst.jpush_appkey, 3);
		}
	}

	/**
	 * 快捷地构建推送对象：所有平台，所有设备，内容为 msg 的通知。 @param @param msg @param @return @return
	 * PushPayload @throws
	 */
	public static PushPayload buildPushObject_all_all_alert(String msg) {
		return PushPayload.alertAll(msg);
	}

	/**
	 * 构建推送对象：所有平台，推送目标是别名为 alias，通知内容为 msg @param @param alias @param @param
	 * msg @param @return @return PushPayload @throws
	 */
	public static PushPayload buildPushObject_all_alias_alert(String alias, String msg) {
		return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.alias(alias))
				.setNotification(Notification.alert(msg)).build();
	}
	
	public static PushPayload sendToOne_ios(String alias, String msg) {
        		return PushPayload.newBuilder()
                        .setPlatform(Platform.ios())
                        .setAudience(Audience.alias(alias))
                        .setNotification(Notification.newBuilder()
                                .addPlatformNotification(IosNotification.newBuilder()
                                        .setAlert(msg).autoBadge().setSound("happy")
                                        .addExtra("from", "JPush")
                                        .build())
                                .build())
                         .setOptions(Options.newBuilder()
                                 .setApnsProduction(MyConst.apnsProduction)
                                 .build())
                         .build();
    }

	public static void sendToAll(String msg) {
		getjpushClient();
		// For push, all you need do is to build PushPayload object.
		PushPayload payload = buildPushObject_all_all_alert(msg);

		try {
			PushResult result = jpushClient.sendPush(payload);
			logger.info("Got result - " + result);

		} catch (APIConnectionException e) {
			// Connection error, should retry later
			logger.error("Connection error, should retry later", e);

		} catch (APIRequestException e) {
			// Should review the error, and fix the request
			logger.error("Should review the error, and fix the request", e);
		}
	}

	/**
	 * alias别名，统一才有token即可
	 * @param alias
	 * @param msg
	 */
	public static void sendToOne(String alias, String msg) {
		try {
			getjpushClient();
			// For push, all you need do is to build PushPayload object.
			PushPayload payload = sendToOne_ios(alias, msg);
			PushResult result = jpushClient.sendPush(payload);
			logger.info("Got result - " + result);

		} catch (APIConnectionException e) {
			// Connection error, should retry later
			logger.error("Connection error, should retry later", e);

		} catch (APIRequestException e) {
			// Should review the error, and fix the request
			logger.error("Should review the error, and fix the request", e);
			try {
				Thread.sleep(2000);
				PushPayload payloadAll = buildPushObject_all_alias_alert(alias, msg);
				PushResult result = jpushClient.sendPush(payloadAll);
				logger.info("Got result - " + result);
			} catch (Exception e2) {
				logger.error(e2);
			}
		}catch (Exception e) {
			logger.error("极光推送失败："+e);
		}
	} 

	public static void main(String[] args) {
		MyConst.jpush_masterSecret = "a8031273019cdbc61a742874";
		MyConst.jpush_appkey="bb904b9cfeed43075b7f7514";
//		sendToAll("hello world【一份子】");
//		String token = "8f1371952923422a9db11e5a70fe811d";
		String token2 = "ecac6f99a14c425f8c6cda0d0aa0d57e";
//		String token3 = "d91ecd6db4ef4def9ac30a8ec2abdf45";
//		sendToOne(token, "hello world【一份子】");
		sendToOne(token2, "hello world2【一份子】");
//		sendToOne(token3, "hello world3【一份子】");
	}
}
