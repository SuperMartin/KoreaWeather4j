package kr.ac.chungbuk.bigdata.weather.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import kr.ac.chungbuk.bigdata.weather.model.KoreaWeatherAWSModel;
import kr.ac.chungbuk.bigdata.weather.model.KoreaWeatherSimpleModel;

/**
 * 데이터베이스와 연동하는 클래스
 * 
 */
public class DatabaseConnectionPoolManager2 {

	// 1. properties for xml 로 변환
	// 로딩시 혹은 매니저 생성시에 동적데이터 로딩(예: koreaweather4j.xml )
	// 2.factory pattern 적용 및 (SINGLETONE PATTERN)Double Checking Lock 적용--> 생성을
	// 자기자신이 하도록 만듦(생성자의 접근제한자를 제한하며 특정메소드를 통해서 자기자신의 객체를 생성함 )
	private final String MySQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private final String MSSQL_JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	private final String MYSQL_DB_URL_FORMAT = "jdbc:mysql://%s:%s/%s"; // jdbc:mysql://000.000.000.000:3306/database
	private final String MSSQL_DB_URL_FORMAT = "jdbc:sqlserver://%s:%s;DatabaseName=%s"; // jdbc:mysql://000.000.000.000:3306/database

	private boolean isMysql = false;
	private String ipaddress;
	private String port;
	private String databaseName;
	private String user;
	private String passwd;
	private String tableName;
	static String JDBC_DRIVER = null;
	static String DB_URL = "jdbc:sqlserver://000.000.000.000:1433;DatabaseName=000";
	static String USER = "UserID";
	static String PASS = "Password";
	private final String TABLENAME = "[DatabaseName].[dbo].[TableName]";

	public DatabaseConnectionPoolManager2(boolean isMysq) {
		this.isMysql = isMysql;

		JDBC_DRIVER = isMysql ? MySQL_JDBC_DRIVER : MSSQL_JDBC_DRIVER;
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public DatabaseConnectionPoolManager2(boolean isMysq, String ipaddress, String port, String databaseName, String user, String passwd, String tableName) {
		this.isMysql = isMysql;
		this.ipaddress = ipaddress;
		this.port = port;
		this.databaseName = databaseName;
		this.user = user;
		this.passwd = passwd;
		this.tableName = tableName;
		JDBC_DRIVER = this.isMysql ? MySQL_JDBC_DRIVER : MSSQL_JDBC_DRIVER;
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * DB에 저장하는 메소드
	 */
	public void persistSimpleWeatherData(List<KoreaWeatherSimpleModel> list) {
		Connection conn = null;
		Statement stmt = null;
		try {
			System.out.println("Connecting to a selected database...");
			DB_URL =this. isMysql ? String.format(MYSQL_DB_URL_FORMAT, this.ipaddress, this.port, this.databaseName) : String.format(MSSQL_DB_URL_FORMAT, this.ipaddress, this.port, this.databaseName);
			conn = DriverManager.getConnection(DB_URL, USER, PASS); // conf에서 수정
																	// 가능하게 만들기.
			System.out.println("Connected database successfully...");
			System.out.println("Inserting records into the table...");
			stmt = conn.createStatement();

			for (KoreaWeatherSimpleModel mm : list) {
				String sql = "INSERT INTO " + TABLENAME + " VALUES ('" + mm.getDate() + "', '" + mm.getPointName() + "', '" + mm.getCondition() + "', '" + mm.getSight() + "', '" + mm.getCloud() + "', '" + mm.getCloudMid() + "', '" + mm.getTemperature() + "')";
				stmt.executeUpdate(sql);
//				System.out.println("ID : " + mm.getDate() + ", POINTNAME : " + mm.getPointName() + mm.getCondition() + " ... Done.");
			}

			System.out.println("Inserted records into the table...");

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		System.out.println("Goodbye!");

	}
}
