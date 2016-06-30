package com.ljh.password;
/**
 * 逆向加密、加密
 * 原理：字符串逆向返回
 * 此类--是具体装饰器
 * @author ljheee
 *
 */
public class ReverseEncrypt extends AbstrastEncrypt {
	
	//如果要单独使用此加密算法，new一个空的，调用reverse()即可
	public ReverseEncrypt() {
	}

	
	



	@Override
	public String deEncrypt(String text) {
		
		String result = null;
		char[] charArray = text.toCharArray();
		int length = charArray.length;
		char[] newChar = new char[length];
		int i = 0;
		for (int j = length-1; j >=0; j--) {
			newChar[i++] = charArray[j];
		}
		result = new String(newChar);
		return result;
	}

	@Override
	public String encrypt(String text) {
		String result = null;
		char[] charArray = text.toCharArray();
		int length = charArray.length;
		char[] newChar = new char[length];
		int i = 0;
		for (int j = length-1; j >=0; j--) {
			newChar[i++] = charArray[j];
		}
		result = new String(newChar);
		return result;
	}
	

}
