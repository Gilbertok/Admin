package com.bograntex.bograntexAdmin.data.erp;

import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.pool.OracleDataSource;

public class DBConnectERP {
	
	String server = "192.168.1.7";
    String port = "1521";
    String database = "dbsystex";
//    String database = "TESTE";
    
    String user = "bg";
    String passwd = "oracle";
    Connection con = null;
	
	public Connection getInstance() throws SQLException {
		if(con == null) {
			OracleDataSource ods = new OracleDataSource();
			ods.setUser(user);
			ods.setPassword(passwd);
			ods.setServerName(server);
			ods.setPortNumber(1521);
			ods.setServiceName(database);
			ods.setDriverType("thin");
			con = ods.getConnection();
		}
		return con;
	}

}
