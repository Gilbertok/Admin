package com.bograntex.bograntexAdmin.data.erp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bograntex.bograntexAdmin.data.AppDataConnect;
import com.bograntex.bograntexAdmin.domain.User;

public class UserERP {

	public User getUsuarioERP(String userName, String password) throws SQLException {
		Connection conn = AppDataConnect.getInstanceERP();
		String SQL = "SELECT " + 
				"    USUARIO.EMPRESA, " + 
				"    USUARIO.USUARIO, " + 
				"    USUARIO.SENHA " + 
				"FROM HDOC_030 USUARIO " + 
				"WHERE USUARIO.USUARIO = ? " + 
				"    AND USUARIO.SENHA = ? ";
		PreparedStatement stmt = conn.prepareStatement(SQL);
		stmt.setString(1, userName);
		stmt.setString(2, password);
		ResultSet res = stmt.executeQuery();
		if(res.next()) {
			User usuario = new User();
			usuario.setEmpresa(res.getString("EMPRESA"));
			usuario.setFirstName(res.getString("USUARIO"));
			return usuario;
		} else {
			return null;
		}
	}

}
