package com.ljheee.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库操作类
 * @author ljheee
 *
 */
public class DbUtil {

	private static Connection con = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	
	static{//静态代码块---类加载
		try {
			//加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "root");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取所有   已注册用户的  账号
	 * @return  一个List
	 */
	public static List getUserNames(){
		ArrayList<Integer> list =null;
		String sql = "select * from users";
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs!=null) list = new ArrayList<Integer>();
			
			while(rs.next()){
				list.add(rs.getInt(1));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 获取所有 用户<acount,pass>
	 * @return  返回一个map
	 */
	public static Map getUsers(){
		Map<Integer,String> map =null;
		String sql = "select * from users";
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs!=null) map = new HashMap<Integer, String>();
			
			while(rs.next()){
				map.put(rs.getInt(1), rs.getString(2));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 插入新用户
	 * @param acount
	 * @param pass
	 * @return
	 */
	public static boolean insertUser(int acount, String pass){
		boolean result = false;
		String sql = "insert into users values(?,?)";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, acount);
			ps.setString(2, pass);
			result = ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		Map list = DbUtil.getUsers();
		System.out.println(list.get(999));
		
//		Boolean b = DbUtil.insertUser(554, "123");
//		System.out.println(b);
		
	}
	
}
