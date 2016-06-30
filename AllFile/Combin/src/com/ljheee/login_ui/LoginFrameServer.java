package com.ljheee.login_ui;
import javax.swing.*;
import com.ljheee.socket.Server;
import java.awt.*;
import java.awt.event.*;
/**
 * ��ΪQQ��¼���棬��ֱ������
 * @author ljheee
 *
 */
public class LoginFrameServer extends JFrame {
	
	private TextField textFieldAccount = null; //�˻�
	static TextField textFieldPass = null;	//����
	private ImageIcon dlGUI=new ImageIcon(LoginFrameServer.class.getResource("/dl.png")); //Ҫ��ʾ��  ����ͼ��
	//���ڴ����϶��¼�����ʾ��갴��ʱ�����꣬�����JFrame
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
		
		//��¼��ť
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
					
					//��½�ɹ�
					disposeFrame();  // �رյ�ǰLogin����
					new Server();	//----����������
				}
				else if(textFieldAccount.getText().equals("") || textFieldPass.getText().equals("")){
					JOptionPane.showMessageDialog(null, "�˻�Ϊ��");    //���ڣ���ʾ��Ϣ
				}else{ 
				    JOptionPane.showMessageDialog(null, "��½ʧ��");
				}
			}
		});
		
		 //����ͼƬ����ӵ�����Panel����
		 JLabel bgLabel = new JLabel(new ImageIcon(LoginFrameServer.class.getResource("/dl.png")));
		 getContentPane().add(bgLabel);
		 
		 //���Ͻǣ��رհ�ť
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
		//�����϶��¼�(ȥ���߿��ʵ��JFrame���϶�)
			this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
					xOld = e.getX();//��¼��갴��ʱ������
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
			  LoginFrameServer.this.setLocation(xx, yy);//������ק�󣬴��ڵ�λ��
			  }
			 });
			
			 String info1 = "QQ�ʺ�/�ֻ���/����";
			 String info2 = "����";
			 textFieldAccount.setText(info1);
			 textFieldPass.setText(info2);
			 textFieldAccount.addFocusListener(new MyFocusListener(textFieldAccount,info1));
			 textFieldPass.addFocusListener(new MyFocusListener(textFieldPass, info2));
		
		this.setVisible(true);
	}
	
	//�˷������ڣ�dispose��¼����[�൱��������]��Ȼ����ȥ��ʾ��ת�Ľ���
	private void disposeFrame() {
		LoginFrameServer.this.dispose();
	}

	public static void main(String[] args) {
		new LoginFrameServer();
	}
}
