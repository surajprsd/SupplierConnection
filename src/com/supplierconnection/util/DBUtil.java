package com.supplierconnection.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;




public class DBUtil {

	Connection con = null;
	Statement stmt = null;
	String queryResult = null;
	//Logger log = Logger.getLogger("devpinoyLogger");
	
	public Connection getConnection() {
		keywords.log("In getconnection Method");
		try {
		if (con == null) {
			keywords.log("Checked whether con is null or not " +con);
			String url = "jdbc:db2://suppconndev.supconcognos.com:50010/SCDEVDB";
			String DB_USER = "db2inst2";
			String DB_PWD = "dbpass4suppl13r";

			
				Class.forName("com.ibm.db2.jcc.DB2Driver");
				keywords.log("DB Registered");
				
				con = DriverManager.getConnection(url, DB_USER, DB_PWD);
				keywords.log("Connected to DB");
			} 
		}catch (Exception e) {
			keywords.log("DB connection exception---"+ e);
			} 
		return con;
	}
	
	public ResultSet executeQuery(String query)
	{
		keywords.log("In executeQuery Method");
		ResultSet rs = null;
		try{
			keywords.log("Executing the query "+query);
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
		}catch(Exception e){
			
		}
		return rs;
	}
	
	public void closeConn(){
		keywords.log("In closeConn()");
		if(con != null){
			try {
				con.close();
				keywords.log("Connection is closed" +con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				keywords.log("Connection close exception---"+ e);
			}
		}
	}

}