package com.rong.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.jfinal.kit.PathKit;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


/****
 * @Project_Name: GWT
 * @Copyright: Copyright © gatewang.com
 * @Version: 1.0.0.1
 * @File_Name: RSAHelper.java
 * @CreateDate: 2013年10月21日 下午3:20:43
 * @Designer: Wiket.Zhou
 * @Desc
 ****/

public class RSAUtil {
	
	static{
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}
	/**
	 * 加密算法RSA
	 */
    public static final String KEY_ALGORITHM = "RSA";
    private static String CIPHERPADDING = "RSA/ECB/PKCS1Padding";
    private static String PUB_KEY_PATH = PathKit.getRootClassPath()+"/rsa_public_key.pem";
    private static String PRI_KEY_PATH = PathKit.getRootClassPath()+"/rsa_private_key.pem";
	private static final Logger logger = Logger.getLogger(RSAUtil.class);
	private static final String PROVIDER = BouncyCastleProvider.PROVIDER_NAME;
    
    /**
	 * 签名算法
	 */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
	 * 获取公钥的key
	 */
    private static final String PUBLIC_KEY = "RSAPublicKey";
    
    /**
	 * 获取私钥的key
	 */
    private static final String PRIVATE_KEY = "RSAPrivateKey";
	
    /**
	 * RSA最大加密明文大小
	 */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    
    /**
	 * RSA最大解密密文大小
	 */
    private static final int MAX_DECRYPT_BLOCK = 128;
    
	// 公钥
//	public static String Public_Key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCoQLpiN5LsDl/LKXhUb+TypepS"+"\n"+
//			"EGqBI3VHxi2aulXXN8m3Pe7t8/dpMykS9+HRhOlRlr4ZCEUa/RXtXCMnAd4OP4k4"+"\n"+
//			"s/v3Qyeb1sQh8OxTVY4R+aT+5ToYvJdq9m1pvWofWLhKL0XO3C5cHtxpkd+sdyDA"+"\n"+
//			"cvlfhQE/Jjwh0NyfBwIDAQAB"+"\n"; //base64//BCD//Hex
	
	public static String PUB_KEY = loadKey(PUB_KEY_PATH);
	public static String PRI_KEY = loadKey(PRI_KEY_PATH);
	
	/**
	 * 生成密钥对(公钥和私钥)
	 * 
	 * @return
	 * @throws Exception
	 */
    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }
    
    /**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param data
	 *            已加密数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * 
	 * @return
	 * @throws Exception
	 */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = new BASE64Decoder().decodeBuffer(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return new BASE64Encoder().encode(signature.sign());
    }

    /**
	 * 校验数字签名
	 * 
	 * @param data
	 *            已加密数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @param sign
	 *            数字签名
	 * 
	 * @return
	 * @throws Exception
	 */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {
        byte[] keyBytes = new BASE64Decoder().decodeBuffer(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(new BASE64Decoder().decodeBuffer(sign));
    }
    
    /**
	 * 获取私钥
	 * 
	 * @param keyMap
	 *            密钥对
	 * @return
	 * @throws Exception
	 */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return new BASE64Encoder().encode(key.getEncoded());
    }

    /**
	 * 获取公钥
	 * 
	 * @param keyMap
	 *            密钥对
	 * @return
	 * @throws Exception
	 */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return new BASE64Encoder().encode(key.getEncoded());
    }
	
    /**
	 * 私钥加密
	 * 
	 * @param data
	 *            源数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * 
	 * @return
	 * @throws Exception
	 */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey)
            throws Exception {
        byte[] keyBytes =new BASE64Decoder().decodeBuffer(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
		// 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }
    
    /**
	 * 私钥解密
	 * 
	 * @param encryptedData
	 *            已加密数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * 
	 * @return
	 * @throws Exception
	 */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)
            throws Exception {
        byte[] keyBytes =new BASE64Decoder().decodeBuffer(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM,PROVIDER);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        //Cipher cipher = Cipher.getInstance(CIPHERNOPADDING);
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
		// 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }
    
	public static String encrypt(String data) {
		try {
			return bytesToHexString(encryptByPublicKey(PUB_KEY, data));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// 公钥加密
	public static byte[] encryptByPublicKey(String strKey, String strData) throws Exception{
		byte data[] = strData.getBytes("utf-8");
		byte key[];
		BASE64Decoder decoder=new BASE64Decoder();
		key = decoder.decodeBuffer(strKey);    
	
		// 实例化公钥材料
		X509EncodedKeySpec x509spec = new X509EncodedKeySpec(key);
		// 实例化公钥工厂
		KeyFactory keyfactory=KeyFactory.getInstance(KEY_ALGORITHM,PROVIDER);
		// 生成公钥
		PublicKey pubKey = keyfactory.generatePublic(x509spec);
		// 公钥加密
		//Cipher cipher = Cipher.getInstance(CIPHERNOPADDING);
		Cipher cipher = Cipher.getInstance(keyfactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		
		int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
		// 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
		
		return encryptedData;
	}
	
	// 公钥解密
	public static byte[] decryptByPublicKey(String strKey, String strData) throws Exception{
		byte data[] = strData.getBytes("utf-8");
		byte key[];
		BASE64Decoder decoder=new BASE64Decoder();
		key = decoder.decodeBuffer(strKey);    
		// 实例化公钥材料
		X509EncodedKeySpec x509spec=new X509EncodedKeySpec(key);
		// 实例化公钥工厂
		KeyFactory keyfactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 获得公钥
		PublicKey pubkey = keyfactory.generatePublic(x509spec);
		// 对数据进行解密
		Cipher cipher = Cipher.getInstance(CIPHERPADDING);
		cipher.init(Cipher.DECRYPT_MODE, pubkey);
		
		int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
		// 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
		
		return decryptedData;
	}
	
	/**
	 * Convert byte[] to hex
	 * string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
	 * 
	 * @param src
	 *            byte[] data
	 * @return hex string
	 */   
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    /**
     * Convert hex string to byte[]
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    /**
     * Convert char to byte
     * @param c char
     * @return byte
     */
     private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
     
	public static String loadKey(String keyPath){
		try{
			File keyFile = new File(keyPath);   
	        InputStream is = new FileInputStream(keyFile); 
			byte[] buffer = new byte[is.available()];
			is.read(buffer);
			is.close();
			return new String(buffer, "UTF-8");
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static String decrypt(String encryptedData, String privateKey){
		try{
			byte[] b = RSAUtil.hexStringToBytes(encryptedData);
			final byte[] decrypt = RSAUtil.decryptByPrivateKey(b, RSAUtil.PRI_KEY);
			String msg = new String(decrypt,"utf-8");
			return msg.trim();
		}catch(Exception e){
			logger.error("[RSAUtil decrypt] error : "+e.getMessage(), e);
			logger.error("[RSAUtil decrypt] encode string : "+encryptedData);
			return null;
		}
	}
	
	public static String decrypt(String encryptedData){
		// return encryptedData;
		if (StringUtils.isNullOrEmpty(encryptedData)) {
			return null;
		}
		return decrypt(encryptedData, RSAUtil.PRI_KEY);
	}
	
	public static void main(String[] args) {
		try{
			System.out.println(decrypt(encrypt("gw")));
		String d = decrypt("4b3c59587e6b2534eb01c43ffaa0a55c96e739667852d1db858db187539be2fc36567d93827d27b14228b268ad0d6374a2ebace5a2a98e673b64d51f73f433075421d710edcf2423a2cbf01644bc5202377269f1a15990949e03b7f99c049634278fbd82efb51dbc44e95ff3ca8a2d63081a7d5a3a023d19dccfd2f031f6a735");
		System.out.println(d);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

}
