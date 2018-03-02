package com.bograntex.bograntexAdmin.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bograntex.bograntexAdmin.data.erp.LojaBalancoData;
import com.bograntex.bograntexAdmin.data.erp.ProdutoData;
import com.bograntex.bograntexAdmin.domain.erp.LojaBalancoDao;

public class BalancoLojaModel {
	
	private String cnpjLoja;
	private String item;
    private String ean13;
    private String mes;
    private String ano;
    private Integer qtdeEstoque;
    private Float precoMedio;
	
	public List<BalancoLojaModel> geraBalancoLojaMes() throws SQLException {
		ProdutoData data = new ProdutoData();
		List<BalancoLojaModel> retorno = new ArrayList<>();
		List<LojaBalancoDao> balancos = LojaBalancoData.getBalancoLojaMes(8, 2017);
		for (LojaBalancoDao dao : balancos) {
			BalancoLojaModel model = new BalancoLojaModel();
			model.setCnpjLoja(dao.getCnpj());
			model.setEan13(dao.getEan13());
			model.setMes(dao.getMes());
			model.setAno(dao.getAno());
			model.setQtdeEstoque(dao.getQtdeEstoque());
//			model.setPrecoMedio(data.getPrecoMedioProdutoFromEan13AndCnpjAteData(model.getCnpjLojaFormat(), model.getEan13(), model.getMesAno()));
			retorno.add(model);
		}
		return retorno;
	}
	
	public String getCnpjLojaFormat() {
		Pattern pattern = Pattern.compile("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})");
		Matcher matcher = pattern.matcher(cnpjLoja.substring(1, 15));
		if(matcher.find()){
			return matcher.replaceAll("$1.$2.$3/$4-$5");
		}
		return cnpjLoja;
	}

	public String getCnpjLoja() {
		return cnpjLoja;
	}

	public void setCnpjLoja(String cnpjLoja) {
		this.cnpjLoja = cnpjLoja;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getEan13() {
		return ean13;
	}

	public void setEan13(String ean13) {
		this.ean13 = ean13;
	}
	
	public String getMesAno() {
		return mes.concat("/").concat(ano);
	}


	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public Integer getQtdeEstoque() {
		return qtdeEstoque;
	}

	public void setQtdeEstoque(Integer qtdeEstoque) {
		this.qtdeEstoque = qtdeEstoque;
	}

	public Float getPrecoMedio() {
		return precoMedio;
	}

	public void setPrecoMedio(Float precoMedio) {
		this.precoMedio = precoMedio;
	}
	
}
