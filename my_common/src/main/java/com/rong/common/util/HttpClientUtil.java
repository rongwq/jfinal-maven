package com.rong.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {
	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accType", "01");
		map.put("accessType", "0");
		map.put("backUrl", "http://222.222.222.222:8080/ACPSample_AppServer/BackRcvResponse");
		map.put("bizType", "000201");
		map.put("certId", "68759663125");
		map.put("channelType", "08");
		map.put("currencyCode", "156");
		map.put("encoding", "UTF-8");
		map.put("merId", "777290058138373");
		map.put("orderId", "20160927152659");
		map.put("signMethod", "01");
		map.put("signature", "n/vXTaroIfLXVaa2H43vpK8iSWjZC7WhtCM6FQVs/8LUZ7Z+elGX0wUYSLF222/Srb1APaqV2zFwlXRnhjP8PKx24zoB"
				+ "JqHXdpUqVV3WmoK0o2rd5a1fJHu5Lfx0308LNVNJXKM3uc4X4ycTyyRnOy2UGVId57dQlQVpOTllXKHpKj6CfDG9cXcRo2GSgXLGhR8e"
				+ "hgl7ilJ7Tioc9LgdGzj62tBLlNHrwheRiQw7JzrV39a5ZGoXqSXi4K0BHQfo83mkqYdxuZ8w1JVvWehKzUItBfxP/dOSWwHkSLDzx1qq"
				+ "K7E0O9M3dk/zmI3ikOBfHrIBjyjVFicvCPHcHub0Xw==");
		map.put("txnAmt", "10000");
		map.put("txnSubType", "01");
		map.put("txnTime", "20160927152659");
		map.put("txnType", "01");
		map.put("version", "5.0.0");
		Map<String, String> reqReserved = new HashMap<String, String>();
		reqReserved.put("gwNumber", "123345");
		reqReserved.put("buyData", "[{amount:1,periodsId:3},{amount:3,peiodsId:5}]");
		map.put("reqReserved", reqReserved.toString());
		String url = "https://101.231.204.80:5000/gateway/api/appTransReq.do";
		String result = HttpClientUtil.doPost(url, map);
		System.out.println(result);
	}
	public static String doPost(String url, Map<String, Object> map) {
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, Object> entry = iterator.next();
				list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			}
			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "utf-8");
				httpPost.setEntity(entity);
			}
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
