package com.bograntex.bograntexAdmin.data;

import java.sql.Connection;
import java.sql.SQLException;

import com.bograntex.bograntexAdmin.data.erp.DBConnectERP;

public class AppDataConnect {
	
	private static Connection con = null;
	
	public static Connection getInstanceERP() throws SQLException {
		if(con == null) {
			con = new DBConnectERP().getInstance();
		}
		return con;
	}

}
