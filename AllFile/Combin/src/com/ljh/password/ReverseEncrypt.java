package com.ljh.password;
/**
 * ������ܡ�����
 * ԭ���ַ������򷵻�
 * ����--�Ǿ���װ����
 * @author ljheee
 *
 */
public class ReverseEncrypt extends AbstrastEncrypt {
	
	//���Ҫ����ʹ�ô˼����㷨��newһ���յģ�����reverse()����
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
