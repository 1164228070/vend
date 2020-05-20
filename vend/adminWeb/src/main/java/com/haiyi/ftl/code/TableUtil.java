package com.haiyi.ftl.code;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.maizi.utils.StringUtil;

/**
 * 表工具类
 * @author Administrator
 *
 */
public class TableUtil {
	private static String url = "jdbc:mysql://116.62.67.49/vend";
	private static String user = "kelvin";
	private static String password = "kelvinKELVIN123456_;;";
	
	/**
	 * 获取表结构
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public static List<Object[]> getTableMetaData(String tableName) throws Exception{
		Connection conn = JdbcUtil.getConnection(url, user, password);
		DatabaseMetaData meta = conn.getMetaData();
		ResultSet resultSet = meta.getColumns("vend",null,tableName,null);
	   return  parseResultSetToCode(resultSet);
	}
	
	/**
	 * 获取表结构信息
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private static List<Object[]> parseResultSetToCode(ResultSet rs) throws SQLException {
		if (null == rs) {
			return null;
		}
		List<Object[]> codes = new ArrayList<Object[]>();
		Object[] pros = null;
		
		while (rs.next()) {
			pros = new Object[7];
			pros[0] = rs.getString("COLUMN_NAME");
			String typeName = rs.getString("TYPE_NAME");
			
			Integer length = Integer.parseInt(rs.getString("COLUMN_SIZE"));
			if("INT".equals(typeName)){
				pros[1] = "INTEGER";
				pros[2] = length+1;
			}else{
				pros[1] = typeName;
				pros[2] = length;
			}
			 
			pros[3] =rs.getString("DECIMAL_DIGITS")!=null?Integer.parseInt(rs.getString("DECIMAL_DIGITS")):null;
			
			pros[4] = "YES".equals(rs.getString("IS_NULLABLE"))?"true":"false";
			
			pros[5] = rs.getString("ORDINAL_POSITION").equals("1")?"true":"false";
			
			pros[6] = StringUtil.isEmpty(rs.getString("REMARKS"))?"XXXX":rs.getString("REMARKS");
			
			System.out.println(Arrays.toString(pros));
			codes.add(pros);
		}
		System.out.println(Arrays.toString(pros));
		return codes;
	}
}
