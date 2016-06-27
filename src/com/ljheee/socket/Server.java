package com.ljheee.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

/**
 * �ڲ��࣬�߳���--����ÿһ���û�TCP����
 * @author ljheee
 *
 */
class ChatThread extends Thread{   
	Socket s = null;
	List<Socket> sockets = null;//���пͻ���Socket�ļ���
	PrintWriter pw = null;
	public ChatThread(Socket s , List<Socket> sockets) {
		this.s=s;
		this.sockets = sockets;
	}
	
	@Override
	public void run() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			while(true){
				String str = br.readLine();
				for (int i = 0; i < sockets.size(); i++) {
					Socket si = sockets.get(i);
					pw=new PrintWriter(si.getOutputStream());
					pw.println(str);
					pw.flush();
				}
			}
		} catch (IOException e) {
			//Client��socket�رգ���ȡ��pw=new PrintWriterʧ�ܣ����쳣
//			JOptionPane.showMessageDialog(null, "��һ���ͻ����˳�");
			e.printStackTrace();//��һ��client�رգ��˴������쳣
			sockets.remove(s);
//			System.out.println("remove:"+sockets);
		}finally{	//�˴����ܽ����رգ���Ϊ���������һ���ͻ���socket�رգ����쳣��finally�йرպ󣬣���������ͻ��˲���ʹ����
//			try {
//				br.close();
//				pw.close();
//			} catch (IOException e) {
//			}
		}
	}
	
}
class ChatThread2 implements Runnable{

	Socket s = null;
	List<Socket> sockets = null;//���пͻ���Socket�ļ���
	PrintWriter pw = null;
	public ChatThread2(Socket s , List<Socket> sockets) {
		this.s=s;
		this.sockets = sockets;
	}
	@Override
	public void run() {

		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			while(true){
				String str = br.readLine();
				for (int i = 0; i < sockets.size(); i++) {
					Socket si = sockets.get(i);
					pw=new PrintWriter(si.getOutputStream());
					pw.println(str);
					pw.flush();
				}
			}
		} catch (IOException e) {
			sockets.remove(s);
		}
	}
}

public class Server {  //��������
	
	static List<Socket> sockets = null;
	Executor pool = null;
	public Server() {
		try {
			ServerSocket ss = new ServerSocket(8888);//������Socket��ָ�������˿�8888
			JOptionPane.showMessageDialog(null, "������������..�ȴ��ͻ�������...");
			System.out.println("������������..�ȴ��ͻ�������........");
			sockets = new ArrayList<Socket>();//���пͻ���Socket�ļ���
			pool = Executors.newCachedThreadPool();//����һ���ɸ�����Ҫ�������̵߳��̳߳أ������������ҿ����߳�
			
		while(true){
			Socket s = ss.accept();//Ϊÿһ�����յ��Ŀͻ��ˣ������׽���
			sockets.add(s);//����ӵ��б�����
//			System.out.println("Add:"+sockets);

//			Thread thread = new ChatThread(s, sockets);
//			thread.start();
			
			//�Ż�--�����̳߳�
			pool.execute(new ChatThread2(s, sockets));
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Server();
	}
}
