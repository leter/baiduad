package com.baiduad.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * ���ܽ��ܹ�����
 */
public class EncryptionUtil {
	private static Logger logger = new Logger(EncryptionUtil.class);

	private static String key = "p@SsW0Rd";
	
	
	/**��������
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
	
	/**����˽Կ
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
	
	  
	/**���е��ַ���ת����16���Ƶ������
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
	
	/**��������
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
	 * md5���ܲ���������128λ��bit����mac ��128bit Macת����16���ƴ���
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
	 * Description ���ݼ�ֵ���м���
	 * @param data 
	 * @param key  ���ܼ�byte����
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data, String key) throws Exception {
		byte[] bt = encrypt(data.getBytes(), key.getBytes());
		String strs = Base64.encode(bt);
		return strs;
	}

	/**
	 * Description ���ݼ�ֵ���н���
	 * @param data
	 * @param key  ���ܼ�byte����
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
	 * Description ���ݼ�ֵ���м���
	 * @param data
	 * @param key  ���ܼ�byte����
	 * @return
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		// ����һ�������ε������Դ
		SecureRandom sr = new SecureRandom();

		// ��ԭʼ��Կ���ݴ���DESKeySpec����
		DESKeySpec dks = new DESKeySpec(key);

		// ����һ����Կ������Ȼ��������DESKeySpecת����SecretKey����
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher����ʵ����ɼ��ܲ���
		Cipher cipher = Cipher.getInstance(DES);

		// ����Կ��ʼ��Cipher����
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}
	
	
	/**
	 * Description ���ݼ�ֵ���н���
	 * @param data
	 * @param key  ���ܼ�byte����
	 * @return
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		// ����һ�������ε������Դ
		SecureRandom sr = new SecureRandom();

		// ��ԭʼ��Կ���ݴ���DESKeySpec����
		DESKeySpec dks = new DESKeySpec(key);

		// ����һ����Կ������Ȼ��������DESKeySpecת����SecretKey����
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher����ʵ����ɽ��ܲ���
		Cipher cipher = Cipher.getInstance(DES);

		// ����Կ��ʼ��Cipher����
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
	//	System.out.println("md5���ܽ��32 bit------------->:" + mac128byte);
		
/*		String data = "123 456";
		String key  = "wang!@#$%";
		System.err.println(encrypt(data, key));
		System.err.println(decrypt(encrypt(data, key), key));*/
		//System.out.println(aaa);
	//	System.out.println(passWordDecode("uGmNFhfG/VEsES5uL7OJ/g==","ccav"));
		System.out.println(passWordEncode("ad1234","admin"));
		System.out.println(passWordDecode("UtUkROmid7U=","admin"));
		//System.out.println(getSafeCodeByString("����1"));
		//System.out.println(buildLength8Str(key,"����"));
	}
}

