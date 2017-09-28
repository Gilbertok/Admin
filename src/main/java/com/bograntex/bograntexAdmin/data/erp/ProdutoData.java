package com.bograntex.bograntexAdmin.data.erp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.bograntex.bograntexAdmin.domain.erp.ProdutoDao;

public class ProdutoData {
	
	public ProdutoDao getProdutoFromEan13(String ean13) throws SQLException {
		Connection conn = new DBConnectERP().getInstance();
		String sql = "";
		return null;
	}
	
	public ProdutoDao getPrecoMedioProdutoFromEan13AndCnpjAteData(String cnpj, String ean13, Integer mes, Integer ano) throws SQLException {
		Connection conn = new DBConnectERP().getInstance();
		ProdutoDao dao = new ProdutoDao();
		String sql = "SELECT " + 
				"    AVG(B.VALOR_UNITARIO) " + 
				"FROM FATU_050 " + 
				"    INNER JOIN FATU_060 B ON B.CH_IT_NF_CD_EMPR = A.CODIGO_EMPRESA " + 
				"        AND B.CH_IT_NF_NUM_NFIS = A.NUM_NOTA_FISCAL " + 
				"        AND B.CH_IT_NF_SER_NFIS = A.SERIE_NOTA_FISC " + 
				"    INNER JOIN BASI_010 C ON C.NIVEL_ESTRUTURA = B.NIVEL_ESTRUTURA " + 
				"        AND C.GRUPO_ESTRUTURA = B.GRUPO_ESTRUTURA " + 
				"        AND C.SUBGRU_ESTRUTURA = B.SUBGRU_ESTRUTURA " + 
				"        AND C.ITEM_ESTRUTURA = B.ITEM_ESTRUTURA " + 
				"WHERE A.CGC_9 || A.CGC_4 || A.CGC_2 = ? " + 
				"        AND C.CODIGO_BARRAS = ? " + 
				"        AND TO_CHAR(A.DATA_EMISSAO,'MM/YYYY') <= ? ";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, cnpj);
		stmt.setString(2, ean13);
		stmt.setString(3, mes+"/"+ano);
		
		return null;
	}

}
