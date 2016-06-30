package com.ljheee.socket;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

import com.ljh.password.EncryptContext;
import com.ljh.password.SimpleEncrypy;

/**
 * 客户端类
 * 
 * @author ljheee
 *
 */
public class Client {

	private JTextArea jta, jta2;
	private JTextField jtf, jtf2;
	private JPanel jPanel = null;
	BufferedReader in = null;
	PrintWriter out = null;
	String localHostName = null;
	String localIP = null;
	SimpleDateFormat sdf = null;

	JFrame jf = null;
	Socket s = null;// 客户端socket
	MulticastSocket ms = null;

	String name;
	public Client(String thisName) {
		
		name = thisName;
		sdf = new SimpleDateFormat("yyyy-MM-dd   hh:mm:ss");
		try {
			this.initNet();
			this.initGUI();
		 // this.receive();
		 // this.receive2();
			new ReceiveTCP().start();
			new ReceiveMuliticast().start();

			InetAddress inetAddress = InetAddress.getLocalHost();
			localHostName = inetAddress.getHostName(); // 获取该Client的主机名，作为“昵称”
			localIP = inetAddress.getHostAddress();
			// System.out.println(localHostName+" "+localIP);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 构造客户端UI界面
	 */
	public void initGUI() {

		jf = new JFrame("客户端Client");
		jf.setSize(400, 400);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setResizable(false);

		jta = new JTextArea();
		jta.setBackground(Color.gray);
		jta.setPreferredSize(new Dimension(170, 350));//!!
		jta2 = new JTextArea();
		jta.setEditable(false);
		jta2.setEditable(false);

		JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jta, jta2);
		jSplitPane.setPreferredSize(new Dimension(200, 200));
		jf.add(jSplitPane);

		jPanel = new JPanel(new GridLayout(2, 2));
		jtf = new JTextField(26);
		jtf2 = new JTextField(26);
		jf.add(jPanel, BorderLayout.SOUTH);
		jPanel.add(new JLabel("我要发言："));
		jPanel.add(jtf);
		jPanel.add(new JLabel("我要组播："));
		jPanel.add(jtf2);

		// 左边的TCP点-点通信
		jtf.addActionListener(new ActionListener() {//

			@Override
			public void actionPerformed(ActionEvent e) {
				String str = jtf.getText();
				EncryptContext encryptContext = new EncryptContext();
				encryptContext.setAbstrastEncrypt(new SimpleEncrypy());
				
				String data = encryptContext.encrypt(str);// 发送前--加密处理
				jtf.setText("");
				out.println(data);// 将 待发送数据--写到socket套接字
				out.flush();
				System.out.println(data);
			}
		});

		// 右边的 UDP组播通信
		jtf2.addActionListener(new ActionListener() {
			DatagramPacket dps = null;

			@Override
			public void actionPerformed(ActionEvent e) {
				String str = jtf2.getText();// 获取待发数据
				try {
					// 将数据打包，封装成 数据包，及“一个信封包装好数据”
					dps = new DatagramPacket(str.getBytes(), str.getBytes().length,
							InetAddress.getByName("224.255.0.1"), 9998);
					ms.send(dps);// 发送
					System.out.println("UDP发送的数据：" + str);
					jtf2.setText("");
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		// 客户端退出
		// jf.addWindowListener(new WindowAdapter() {
		//
		// @Override
		// public void windowClosed(WindowEvent e) {
		// try {
		// s.close(); //当前客户端关闭时----关闭当前用户socket，且从服务器列表中清除
		// Server.sockets.remove(s);
		// // System.exit(0);
		// } catch (IOException e1) {
		// }
		// }
		//
		// });
		jf.setVisible(true);
	}

	/**
	 * 初始化--网络（套接字、指定端口等）
	 */
	public void initNet() {

		try {
			// 新建客户端socket，连接指定IP的，指定端口
			s = new Socket("127.0.0.1", 8888);
			out = new PrintWriter(s.getOutputStream());
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			
			//................................................................
			ms = new MulticastSocket(9998);
			String host = "224.255.0.1";// 多播地址
			InetAddress group = InetAddress.getByName(host);
			ms.joinGroup(group);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(jf, "服务器异常...可能是未启动");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class ReceiveMuliticast extends Thread {
		@Override
		public void run() {
			System.out.println("run");
			int port = 9998;
			int length = 1024;
			byte[] buf = new byte[length];

			DatagramPacket dp = null;
			byte[] data = null;

			try {
				// ms = new MulticastSocket(port);
				dp = new DatagramPacket(buf, length);

				while (true) {
					ms.receive(dp);
					// data = dp.getData();

					jta2.append(name + "说:  " + new String(buf));
					jta2.append("\n");

					System.out.println("收到多播消息：" + new String(buf));
				}
			} catch (UnknownHostException e) {
				JOptionPane.showMessageDialog(null, "连接Serve失败..");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 接收--来自“多播组”的数据
	 */
	public void receive2() {

		int port = 9998;
		int length = 1024;
		byte[] buf = new byte[length];

		DatagramPacket dp = null;
		byte[] data = null;

		try {
			dp = new DatagramPacket(buf, length);
			while (true) {
				ms.receive(dp);
				data = dp.getData();
				System.out.println("UDP收到的数据：" + data);
				jta2.append(dp.getAddress() + "说:  " + new String(buf, 0, dp.getLength()));
				jta2.append("\n");

				buf = new byte[length];
				dp = new DatagramPacket(buf, length);
				System.out.println("收到多播消息：" + new String(buf));
			}
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "连接Serve失败..");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class ReceiveTCP extends Thread {
		@Override
		public void run() {
			receive();
		}
	}

	/**
	 * 从socket套接字中，读取数据 。此方法是死循环，且读取套接字可能是 堵塞的方法，建议写在 线程里，或在线程里调用
	 */
	public void receive() {

		while (true) {
			String str = null;
			String data = null;
			try {
				str = in.readLine();
				if (str == null)
					return;
				EncryptContext encryptContext = new EncryptContext();
				encryptContext.setAbstrastEncrypt(new SimpleEncrypy());
				data = encryptContext.deEncrypt(str);
				jta.append(localHostName + "：    " + sdf.format(new Date()) + "\n" + "       " + data + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
