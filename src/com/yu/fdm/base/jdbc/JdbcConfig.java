package com.yu.fdm.base.jdbc;

import com.yu.fdm.base.util.PropertiesUtil;

public class JdbcConfig {
	private static final String JDBC_URL = "jdbc.url";
	private static final String JDBC_USERNAME = "jdbc.username";
	private static final String JDBC_PASSWORD = "jdbc.password";
	
	private static String driverClassName = "com.mysql.jdbc.Driver";
	private static String url = PropertiesUtil.get(JDBC_URL);
	private static String username = PropertiesUtil.get(JDBC_USERNAME);
	private static String password = PropertiesUtil.get(JDBC_PASSWORD);
	
	public static String getDriverClassName() {
		return driverClassName;
	}
	public static void setDriverClassName(String driverClassName) {
		JdbcConfig.driverClassName = driverClassName;
	}
	public static String getUrl() {
		return url;
	}
	public static void setUrl(String url) {
		PropertiesUtil.set(JDBC_URL, url);
		JdbcConfig.url = url;
	}
	public static String getUsername() {
		return username;
	}
	public static void setUsername(String username) {
		PropertiesUtil.set(JDBC_USERNAME, username);
		JdbcConfig.username = username;
	}
	public static String getPassword() {
		return password;
	}
	public static void setPassword(String password) {
		PropertiesUtil.set(JDBC_PASSWORD, password);
		JdbcConfig.password = password;
	}
}
