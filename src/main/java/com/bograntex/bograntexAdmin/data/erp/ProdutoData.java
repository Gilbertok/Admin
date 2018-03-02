package com.bograntex.bograntexAdmin.data.erp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bograntex.bograntexAdmin.domain.erp.ProdutoDao;

public class ProdutoData {
	
	Connection conn = null;
	
	public ProdutoData() throws SQLException {
		conn = new DBConnectERP().getInstance();
	}
	
	public ProdutoDao getProdutoFromEan13(String ean13) throws SQLException {
		return null;
	}
	
	public Float getPrecoMedioProdutoFromEan13AndCnpjAteData(String cnpj, String ean13, String mesAno) throws SQLException {
		Float valorMedio = 0f;
		String sql = "SELECT " + 
				"    AVG(B.VALOR_UNITARIO) VALOR_MEDIO " + 
				"FROM FATU_050 A" + 
				"    INNER JOIN FATU_060 B ON B.CH_IT_NF_CD_EMPR = A.CODIGO_EMPRESA " + 
				"        AND B.CH_IT_NF_NUM_NFIS = A.NUM_NOTA_FISCAL " + 
				"        AND B.CH_IT_NF_SER_NFIS = A.SERIE_NOTA_FISC " + 
				"    INNER JOIN BASI_010 C ON C.NIVEL_ESTRUTURA = B.NIVEL_ESTRUTURA " + 
				"        AND C.GRUPO_ESTRUTURA = B.GRUPO_ESTRUTURA " + 
				"        AND C.SUBGRU_ESTRUTURA = B.SUBGRU_ESTRUTURA " + 
				"        AND C.ITEM_ESTRUTURA = B.ITEM_ESTRUTURA " +
				"	INNER JOIN PEDI_010 D ON D.CGC_9 = A.CGC_9 " + 
				"	        AND D.CGC_4 = A.CGC_4 " + 
				"	        AND D.CGC_2 = A.CGC_2 " +
				"WHERE BOCA_FORMATA_CPF_CNPJ(D.CGC_9,D.CGC_4,D.CGC_2,D.FISICA_JURIDICA) = ? " + 
				"        AND C.CODIGO_BARRAS = ? " + 
				"        AND TO_CHAR(A.DATA_EMISSAO,'MM/YYYY') <= ? ";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, cnpj);
		stmt.setString(2, ean13);
		stmt.setString(3, mesAno);
		ResultSet res = stmt.executeQuery();
		while (res.next()) {
			valorMedio = res.getFloat("VALOR_MEDIO");
		}
		res.close();
		stmt.close();
		return valorMedio;
	}

}
