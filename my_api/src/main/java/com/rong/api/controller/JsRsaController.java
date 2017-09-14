package com.rong.api.controller;

import java.security.interfaces.RSAPublicKey;

import org.apache.commons.codec.binary.Hex;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.common.util.RSAUtil;
import com.rong.common.util.RSAUtils;

/**
 * h5中js的加密
 * @author rongwq
 *
 */
public class JsRsaController extends Controller{
	
	/**
	 * 内部专有rsa加密
	* @param    传递经过js加密的字符串
	* @return void    
	* @throws
	 */
	public void encrypt(){
		String str = getPara("str");
		try {
			str = RSAUtils.decryptStringByJs(str);
			BaseRenderJson.returnObjectTmplate(this, MyErrorCodeConfig.REQUEST_SUCCESS,RSAUtil.bytesToHexString(RSAUtil.encryptByPublicKey(RSAUtil.PUB_KEY, str)),"加密成功");
		} catch (Exception e) {
			e.printStackTrace();
			BaseRenderJson.returnObjectTmplate(this, MyErrorCodeConfig.REQUEST_FAIL, null,"加密失败");
		}
	}
	
	/**
	 * 获取js加密参数
	 */
	public void getEncryptParam(){
		try {
			RSAPublicKey publicKey = RSAUtils.getDefaultPublicKey();
			String modulus = new String(Hex.encodeHex(publicKey.getModulus().toByteArray()));
			String exponent = new String(Hex.encodeHex(publicKey.getPublicExponent().toByteArray()));
			Record record = new Record();
			record.set("modulus", modulus);
			record.set("exponent", exponent);
			BaseRenderJson.returnObjectTmplate(this, MyErrorCodeConfig.REQUEST_SUCCESS,record,"获取加密参数成功");
		} catch (Exception e) {
			e.printStackTrace();
			BaseRenderJson.returnObjectTmplate(this, MyErrorCodeConfig.REQUEST_FAIL, null,"获取加密参数失败");
		}
	}
}
