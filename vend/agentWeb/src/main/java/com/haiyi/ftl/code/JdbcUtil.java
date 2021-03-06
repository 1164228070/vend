package com.haiyi.ftl.code;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

//JDBC工具类

/**
 * JDBC 工具类
   * @ClassName: JdbcUtil
   * @Company: 麦子科技
   * @Description: TODO
   * @author 技术部-谢维乐
   * @date 2016年7月24日上午10:36:42
   *
 */
public final class JdbcUtil {
 
	private static Connection conn = null;
	private static Statement stmt = null;
	private static ResultSet rs = null;
	
	//只加载一次驱动
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rs){
		if(rs!=null){
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(Statement stmt){
		if(stmt!=null){
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(Connection conn){
		if(conn!=null){
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//取得数据库连接
	public static Connection getConnection(String url,String user,String password){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url,user,password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}