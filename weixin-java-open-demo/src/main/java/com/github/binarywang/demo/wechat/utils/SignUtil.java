package com.github.binarywang.demo.wechat.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 请求校验工具
 * @author renjinbao
 */

public class SignUtil {

	private static final Logger logger=Logger.getLogger(SignUtil.class);
	
	/**
	 * 计算签名
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static String checkSignature( String timestamp,String nonce,String token) {
		String[] arr = { token, timestamp, nonce };
		Arrays.sort(arr);
		StringBuffer content = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String signature = null; 
		try {
			md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(content.toString().getBytes());
			signature = byteToStr(digest).toLowerCase();
		} catch (NoSuchAlgorithmException e) {
			logger.error("计算签名时遇到错误", e);
		}
		return signature;
	}
	
	/**
	 * 计算jsapi签名
	 * @param timestamp
	 * @param nonce
	 * @param jsapi_ticket
	 * @param url
	 * @return
	 */
	public static String checkJsAPiSign(String timestamp,String nonce,String jsapi_ticket,String url ){
		logger.warn("timestamp:"+timestamp+" nonce:"+nonce+" jsapi_ticket:"+jsapi_ticket+" url:"+url);
		if(StringUtils.isBlank(timestamp)||StringUtils.isBlank(nonce)||StringUtils.isBlank(jsapi_ticket)||StringUtils.isBlank(url)){
			return "";
		}
		StringBuffer sb=new StringBuffer();
		SortedMap<String, String> sortMap=new TreeMap<String, String>();
		sortMap.put("timestamp", timestamp);
		sortMap.put("noncestr", nonce);
		sortMap.put("jsapi_ticket", jsapi_ticket);
		sortMap.put("url", url);
		
		for ( Entry<String, String> entry : sortMap.entrySet()) {
			String key=entry.getKey();
			String value=entry.getValue();
			sb.append(key).append("=").append(value).append("&");
		}
		System.out.println("str:"+sb.toString());
		String signature=sb.substring(0, sb.length()-1).toString();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(signature.toString().getBytes());
			signature = byteToStr(digest).toLowerCase();
		} catch (NoSuchAlgorithmException e) {
			logger.error("计算jsapi签名时遇到错误", e);
		}
		return signature;
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];
		String s = new String(tempArr);
		return s;
	}

	
	public static void main(String[] args){
		String timestamp="1450413581";
		String nonce="d8a7888238f06f0f031054b42de05466";
		String jsapi_ticket="sM4AOVdWfPE4DxkXGEs8VOP7JnaM1CyMje7NOrOafLiOTB2oST1m3SBEm_3acMPQcJn1TRdoHNdJEoPVN_1jGQ";
		String url="http://interface.wdgood.cn/wxLuck/showActive?openid=oZQYWt5YQZKJNkUapsJf-cLthuvE&sign=e2f40fbb23bbb09b6d2c514a4ecab760"; 
		String result=checkJsAPiSign(timestamp,nonce,jsapi_ticket,url);
		System.out.println("result:"+result);
	}
}
