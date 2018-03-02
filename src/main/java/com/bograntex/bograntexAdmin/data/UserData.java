package com.bograntex.bograntexAdmin.data;

import com.bograntex.bograntexAdmin.domain.User;

public class UserData {

	public static User authenticate(String userName, String password) throws Exception {
		return new User().getUsuarioLogin(userName, password);
	}
	
	
	

}
