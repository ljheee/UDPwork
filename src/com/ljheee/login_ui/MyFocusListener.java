package com.ljheee.login_ui;

import java.awt.TextField;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
 * 缂備焦鈹凾extField婵烇綀顕ф慨鐐烘嚔瀹勬壆绻侀柣鎺炲閸嬶絾绂嶇�ｂ晜顐�
 * @author ljheee
 *
 */
public class MyFocusListener implements FocusListener {

	 String info;
	 TextField jtf;
	    
	public MyFocusListener(TextField jtf,String info){
		this.jtf = jtf;
		this.info = info;
	}
	
	@Override
	public void focusGained(FocusEvent arg0) {
		//tf = LoginFrame.textFieldPass;
		if(jtf==LoginFrameServer.textFieldPass){
			LoginFrameServer.textFieldPass.setEchoChar('�');
		}
		String temp = jtf.getText().trim();
		if(temp.equals(info)){
			jtf.setText("");
		}
	}

	@Override
	public void focusLost(FocusEvent arg0) {
//		LoginFrame.textFieldPass = new TextField();
		String temp = jtf.getText().trim();
		if(temp.equals("")){
			jtf.setText(info);
		}
	}

}
