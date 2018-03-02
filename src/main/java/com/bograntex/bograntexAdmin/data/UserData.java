package com.bograntex.bograntexAdmin.data;

import java.sql.SQLException;

import com.bograntex.bograntexAdmin.data.erp.UserERP;
import com.bograntex.bograntexAdmin.domain.User;

public class UserData {

	public static User authenticate(String userName, String password) throws SQLException {
		return new UserERP().getUsuarioERP(userName, password);
	}
	
	
	

}
