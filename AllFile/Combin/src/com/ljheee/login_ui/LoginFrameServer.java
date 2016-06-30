package com.ljheee.login_ui;
import javax.swing.*;
import com.ljheee.socket.Server;
import java.awt.*;
import java.awt.event.*;
/**
 * 此为QQ登录界面，可直接运行
 * @author ljheee
 *
 */
public class LoginFrameServer extends JFrame {
	
	private TextField textFieldAccount = null; //账户
	static TextField textFieldPass = null;	//密码
	private ImageIcon dlGUI=new ImageIcon(LoginFrameServer.class.getResource("/dl.png")); //要显示的  背景图层
	//用于处理拖动事件，表示鼠标按下时的坐标，相对于JFrame
	private	int xOld = 0;
	private	int yOld = 0;
	
	public LoginFrameServer() {
		this.setSize(430, 330);
		
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		textFieldAccount = new TextField();
		springLayout.putConstraint(SpringLayout.NORTH, textFieldAccount, 200, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, textFieldAccount, 134, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textFieldAccount, -109, SpringLayout.EAST, getContentPane());
		textFieldAccount.setEnabled(true);
		textFieldAccount.setSize(new Dimension(190, 28));
		getContentPane().add(textFieldAccount);
		textFieldAccount.setColumns(10);                
		
		textFieldPass = new TextField();
		springLayout.putConstraint(SpringLayout.NORTH, textFieldPass, 230, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, textFieldAccount, -2, SpringLayout.NORTH, textFieldPass);
		textFieldPass.setEnabled(true);
		springLayout.putConstraint(SpringLayout.WEST, textFieldPass, 134, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textFieldPass, -109, SpringLayout.EAST, getContentPane());
		textFieldPass.setSize(new Dimension(190, 28));
		getContentPane().add(textFieldPass);
		textFieldPass.setColumns(10);
		
		//登录按钮
		JButton button = new JButton("\u767B  \u5F55");
		springLayout.putConstraint(SpringLayout.SOUTH, textFieldPass, -33, SpringLayout.NORTH, button);
		springLayout.putConstraint(SpringLayout.NORTH, button, -39, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, button, -10, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, button, -232, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, button, -152, SpringLayout.EAST, getContentPane());
		button.setIcon(new ImageIcon(LoginFrameServer.class.getResource("/dlbtn.png")));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		getContentPane().add(button);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if((textFieldAccount.getText().equals("554278334")) &&( textFieldPass.getText().equals("ljheee"))){
					
					//登陆成功
					disposeFrame();  // 关闭当前Login界面
					new Server();	//----启动服务器
				}
				else if(textFieldAccount.getText().equals("") || textFieldPass.getText().equals("")){
					JOptionPane.showMessageDialog(null, "账户为空");    //窗口：提示消息
				}else{ 
				    JOptionPane.showMessageDialog(null, "登陆失败");
				}
			}
		});
		
		 //背景图片，添加到背景Panel里面
		 JLabel bgLabel = new JLabel(new ImageIcon(LoginFrameServer.class.getResource("/dl.png")));
		 getContentPane().add(bgLabel);
		 
		 //右上角：关闭按钮
		 JButton closeButton = new JButton("");
		 springLayout.putConstraint(SpringLayout.NORTH, closeButton, 0, SpringLayout.NORTH, getContentPane());
		 springLayout.putConstraint(SpringLayout.WEST, closeButton, -31, SpringLayout.EAST, getContentPane());
		 springLayout.putConstraint(SpringLayout.SOUTH, closeButton, 33, SpringLayout.NORTH, getContentPane());
		 springLayout.putConstraint(SpringLayout.EAST, closeButton, 0, SpringLayout.EAST, getContentPane());
		 closeButton.setIcon(new ImageIcon(LoginFrameServer.class.getResource("/closebtn.png")));
		 getContentPane().add(closeButton);
		
		 closeButton.setSize(30, 30);
		 closeButton.addActionListener(new ActionListener() {
		  @Override
		  public void actionPerformed(ActionEvent e) {
		  System.exit(0);
		  }
		 });
		 
		 //......................................................
		//处理拖动事件(去掉边框后，实现JFrame的拖动)
			this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
					xOld = e.getX();//记录鼠标按下时的坐标
					yOld = e.getY();
				}
			});
			
			  this.addMouseMotionListener(new MouseMotionAdapter() {
			  @Override
			  public void mouseDragged(MouseEvent e) {
			  int xOnScreen = e.getXOnScreen();
			  int yOnScreen = e.getYOnScreen();
			  int xx = xOnScreen - xOld;
			  int yy = yOnScreen - yOld;
			  LoginFrameServer.this.setLocation(xx, yy);//设置拖拽后，窗口的位置
			  }
			 });
			
			 String info1 = "QQ帐号/手机号/邮箱";
			 String info2 = "密码";
			 textFieldAccount.setText(info1);
			 textFieldPass.setText(info2);
			 textFieldAccount.addFocusListener(new MyFocusListener(textFieldAccount,info1));
			 textFieldPass.addFocusListener(new MyFocusListener(textFieldPass, info2));
		
		this.setVisible(true);
	}
	
	//此方法用于：dispose登录界面[相当于隐藏了]，然后再去显示跳转的界面
	private void disposeFrame() {
		LoginFrameServer.this.dispose();
	}

	public static void main(String[] args) {
		new LoginFrameServer();
	}
}
