package com.ljh.password;
/**
 * “∆Œªº”√‹
 * @author ljheee
 *
 */
public class MoveEncrypt extends AbstrastEncrypt {

	private final int miyao = 15;
	
	public MoveEncrypt() {
	}

	
	@Override
	public String encrypt(String text) {
		String result = null;
		char[] charArray = text.toCharArray();
		char[] newChar = new char[charArray.length];
		int i=0;
		for (char c : charArray) {
			newChar[i++] = (char) (c-miyao);
		}
		result = new String(newChar);
		return result;
		
	}

	@Override
	public String deEncrypt(String text) {
		String result = null;
		char[] charArray = text.toCharArray();
		char[] newChar = new char[charArray.length];
		int i=0;
		for (char c : charArray) {
			newChar[i++] = (char) (c+miyao);
		}
		result = new String(newChar);
		return result;
	}
	
	
	
}
