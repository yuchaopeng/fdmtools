package com.yu.fdm.base.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtil {
	static {  
        try {  
            Class.forName(JdbcConfig.getDriverClassName());  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
        }  
    }  
  
    public static Connection getConnection() throws SQLException {  
        return DriverManager.getConnection(JdbcConfig.getUrl(), JdbcConfig.getUsername(), JdbcConfig.getPassword());  
    }  
    
    public static boolean testConnection() throws SQLException{
    	boolean flag = false;
    	Connection conn = getConnection();
		if(!conn.isClosed()){
			flag = true;
		}
    	return flag;
    }
  
    public static void close(ResultSet rs, Statement st, Connection conn) {  
        try {
			if(rs != null) rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        try {
			if(st != null) st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        try {
			if(conn != null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
  
    }  
    
    public static void close(Connection conn){
    	close(null, null, conn);
    }
}
