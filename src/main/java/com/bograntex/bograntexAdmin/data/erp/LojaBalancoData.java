package com.bograntex.bograntexAdmin.data.erp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bograntex.bograntexAdmin.domain.erp.LojaBalancoDao;

public class LojaBalancoData {
	
	public static List<LojaBalancoDao> getBalancoLojaMes(Integer mes, Integer ano) throws SQLException {
		List<LojaBalancoDao> balancos = new ArrayList<LojaBalancoDao>();
		Connection conn = new DBConnectERP().getInstance();
		String sql = "SELECT " + 
				"    LOJA.ID, " + 
				"    LOJA.CNPJ, " + 
				"    LOJA.CODIGO_BARRAS, " + 
				"    LOJA.MES, " + 
				"    LOJA.ANO, " + 
				"    LOJA.QTDE_ESTOQUE " + 
				"FROM BOCA_BALANCO_LOJA LOJA " + 
				"WHERE LOJA.MES = ?" +
				"	AND LOJA.ANO = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, mes);
		stmt.setInt(2, ano);
		ResultSet res = stmt.executeQuery();
		while (res.next()) {
            LojaBalancoDao balanco = new LojaBalancoDao();
            balanco.setId(res.getString("ID"));
            balanco.setCnpj(res.getString("CNPJ"));
            balanco.setEan13(res.getString("CODIGO_BARRAS"));
            balanco.setMes(res.getString("MES"));
            balanco.setAno(res.getString("ANO"));
            balanco.setQtdeEstoque(res.getString("QTDE_ESTOQUE"));
            balancos.add(balanco);
		}
		res.close();
		stmt.close();
		conn.close();
		return balancos;
	}

}
