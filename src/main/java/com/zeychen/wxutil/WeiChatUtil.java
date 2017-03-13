package com.zeychen.wxutil;

import java.security.MessageDigest;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;

public class WeiChatUtil {
	
	public static final String TOKEN = "12345678";
	
	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}
	
	public static String getSha1(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));
			byte[] md = mdTemp.digest();
			int j = md.length;
			char buf[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 验证微信接口
	 * @param req
	 * @return
	 */
	public static boolean validateWeichatCode(HttpServletRequest req){
		boolean result = false;
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String[] arrs = new String[]{TOKEN,timestamp,nonce};
		Arrays.sort(arrs);
		StringBuffer sb = new StringBuffer();
		for(String arr:arrs){
			sb.append(arr);
		}
		if(signature.equals(getSha1(sb.toString()))){
			result = true;
		}
		return result;
	}
	
}
