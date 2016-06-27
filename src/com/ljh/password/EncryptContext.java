package com.ljh.password;

public class EncryptContext {

	AbstrastEncrypt abstrastEncrypt;

	

	public void setAbstrastEncrypt(AbstrastEncrypt abstrastEncrypt) {
		this.abstrastEncrypt = abstrastEncrypt;
	}
	
	 public String encrypt(String text){
		 return abstrastEncrypt.encrypt(text);
	 }
	
	 public  String deEncrypt(String text){
		 return abstrastEncrypt.deEncrypt(text);
	 }
	 
}
