package com.baiduad.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 加密解密工具类
 */
public class EncryptionUtil {
	private static Logger logger = new Logger(EncryptionUtil.class);

	private static String key = "p@SsW0Rd";
	
	
	/**加密密码
	 * @param passWord
	 * @param userName
	 * @param accountId
	 * @return
	 */
	public static String passWordEncode(String passWord,String privateKey){
		String str = null;
		try {
		 str = encrypt(passWord, buildPrivateKey(privateKey));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("",e);
		}
		return str;
	}
	
	public static String passWordEncode(String passWord){
		return passWordEncode(passWord,null);
	}
	
	/**加密私钥
	 * @param key
	 * @return
	 */
	private static String buildPrivateKey(String key){
		if(key==null)return EncryptionUtil.key;
		char[] char1 = EncryptionUtil.key.toCharArray();
		char[] char2 = peculiarStr2HexStr(key).toCharArray();
		char[] char3 = new char[char1.length];
		for (int i = 0; i < char1.length; i++) {
			if(i%2==0){
				char3[i] = char1[i];
			}else if(char2.length > i){
				char3[i] = char2[i];
			}else{
				char3[i] = (char)(65-char3.length+i);
			}
		}
		System.out.println(new String(char3));
		return new String(char3);
	}
	
	  
	/**独有的字符串转换成16进制倒序输出
	 * @param str
	 * @return
	 */
	private static String peculiarStr2HexStr(String str) {  
	    char[] chars = "F0D41792A3C5E6B8".toCharArray();  
	    StringBuilder sb = new StringBuilder("");  
	    byte[] bs = str.getBytes();  
	    int bit;  
	    for (int i = 0; i < bs.length; i++) {  
	        bit = (bs[i] & 0x0f0) >> 4;  
	        sb.append(chars[bit]);  
	        bit = bs[i] & 0x0f;  
	        sb.append(chars[bit]);  
	    }  
	    return sb.reverse().toString();  
	}
	
	/**解密密码
	 * @param passWordDes
	 * @param userName
	 * @param accountId
	 * @return
	 */
	public static String passWordDecode(String passWordDes,String privateKey){
		String str = null;
		try {
		 str = decrypt(passWordDes, buildPrivateKey(privateKey));
		} catch (Exception e) {
			logger.error("",e);
		}
		return str;
	}
	
	public static String passWordDecode(String passWordDes){
		 return passWordDecode(passWordDes, null);
	}
	
	/**
	 * md5加密产生，产生128位（bit）的mac 将128bit Mac转换成16进制代码
	 * 
	 * @param strSrc
	 * @param key
	 * @return 14c338780391f84212325f99ad6314ab
	 */
	public static String MD5Encode(String strSrc, String key) {
		StringBuilder rs = new StringBuilder();
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(strSrc.getBytes("UTF8"));

			byte[] temp;
			temp = md5.digest(key.getBytes("UTF8"));

			for (int i = 0; i < temp.length; i++) {
				rs.append(
						Integer.toHexString((0x000000ff & temp[i]) | 0xffffff00)
								.substring(6)).toString();
			}

			return rs.toString();

		} catch (Exception e) {
			logger.error("",e);
		}
		return null;
	}
	
	public static String MD5Encode(String strSrc) {
		return MD5Encode(strSrc,"");
	}
	
	
	
	
	private final static String DES = "DES";

	/**
	 * Description 根据键值进行加密
	 * @param data 
	 * @param key  加密键byte数组
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data, String key) throws Exception {
		byte[] bt = encrypt(data.getBytes(), key.getBytes());
		String strs = Base64.encode(bt);
		return strs;
	}

	/**
	 * Description 根据键值进行解密
	 * @param data
	 * @param key  加密键byte数组
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static String decrypt(String data, String key) throws IOException,
			Exception {
		if (data == null)
			return null;
		byte[] buf = Base64.decode(data);
		byte[] bt = decrypt(buf,key.getBytes());
		return new String(bt);
	}

	/**
	 * Description 根据键值进行加密
	 * @param data
	 * @param key  加密键byte数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(DES);

		// 用密钥初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}
	
	
	/**
	 * Description 根据键值进行解密
	 * @param data
	 * @param key  加密键byte数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(DES);

		// 用密钥初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}
	
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		//String aaa = "123456";
		//String mac128byte = MD5Encode(aaa);
	//	System.out.println("md5加密结果32 bit------------->:" + mac128byte);
		
/*		String data = "123 456";
		String key  = "wang!@#$%";
		System.err.println(encrypt(data, key));
		System.err.println(decrypt(encrypt(data, key), key));*/
		//System.out.println(aaa);
	//	System.out.println(passWordDecode("uGmNFhfG/VEsES5uL7OJ/g==","ccav"));
		System.out.println(passWordEncode("ad1234","admin"));
		System.out.println(passWordDecode("UtUkROmid7U=","admin"));
		//System.out.println(getSafeCodeByString("汪宁1"));
		//System.out.println(buildLength8Str(key,"汪您"));
	}
}

