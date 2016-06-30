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
 * �ͻ�����
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
	Socket s = null;// �ͻ���socket
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
			localHostName = inetAddress.getHostName(); // ��ȡ��Client������������Ϊ���ǳơ�
			localIP = inetAddress.getHostAddress();
			// System.out.println(localHostName+" "+localIP);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����ͻ���UI����
	 */
	public void initGUI() {

		jf = new JFrame("�ͻ���Client");
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
		jPanel.add(new JLabel("��Ҫ���ԣ�"));
		jPanel.add(jtf);
		jPanel.add(new JLabel("��Ҫ�鲥��"));
		jPanel.add(jtf2);

		// ��ߵ�TCP��-��ͨ��
		jtf.addActionListener(new ActionListener() {//

			@Override
			public void actionPerformed(ActionEvent e) {
				String str = jtf.getText();
				EncryptContext encryptContext = new EncryptContext();
				encryptContext.setAbstrastEncrypt(new SimpleEncrypy());
				
				String data = encryptContext.encrypt(str);// ����ǰ--���ܴ���
				jtf.setText("");
				out.println(data);// �� ����������--д��socket�׽���
				out.flush();
				System.out.println(data);
			}
		});

		// �ұߵ� UDP�鲥ͨ��
		jtf2.addActionListener(new ActionListener() {
			DatagramPacket dps = null;

			@Override
			public void actionPerformed(ActionEvent e) {
				String str = jtf2.getText();// ��ȡ��������
				try {
					// �����ݴ������װ�� ���ݰ�������һ���ŷ��װ�����ݡ�
					dps = new DatagramPacket(str.getBytes(), str.getBytes().length,
							InetAddress.getByName("224.255.0.1"), 9998);
					ms.send(dps);// ����
					System.out.println("UDP���͵����ݣ�" + str);
					jtf2.setText("");
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		// �ͻ����˳�
		// jf.addWindowListener(new WindowAdapter() {
		//
		// @Override
		// public void windowClosed(WindowEvent e) {
		// try {
		// s.close(); //��ǰ�ͻ��˹ر�ʱ----�رյ�ǰ�û�socket���Ҵӷ������б������
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
	 * ��ʼ��--���磨�׽��֡�ָ���˿ڵȣ�
	 */
	public void initNet() {

		try {
			// �½��ͻ���socket������ָ��IP�ģ�ָ���˿�
			s = new Socket("127.0.0.1", 8888);
			out = new PrintWriter(s.getOutputStream());
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			
			//................................................................
			ms = new MulticastSocket(9998);
			String host = "224.255.0.1";// �ಥ��ַ
			InetAddress group = InetAddress.getByName(host);
			ms.joinGroup(group);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(jf, "�������쳣...������δ����");
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

					jta2.append(name + "˵:  " + new String(buf));
					jta2.append("\n");

					System.out.println("�յ��ಥ��Ϣ��" + new String(buf));
				}
			} catch (UnknownHostException e) {
				JOptionPane.showMessageDialog(null, "����Serveʧ��..");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ����--���ԡ��ಥ�顱������
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
				System.out.println("UDP�յ������ݣ�" + data);
				jta2.append(dp.getAddress() + "˵:  " + new String(buf, 0, dp.getLength()));
				jta2.append("\n");

				buf = new byte[length];
				dp = new DatagramPacket(buf, length);
				System.out.println("�յ��ಥ��Ϣ��" + new String(buf));
			}
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "����Serveʧ��..");
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
	 * ��socket�׽����У���ȡ���� ���˷�������ѭ�����Ҷ�ȡ�׽��ֿ����� �����ķ���������д�� �߳�������߳������
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
				jta.append(localHostName + "��    " + sdf.format(new Date()) + "\n" + "       " + data + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
