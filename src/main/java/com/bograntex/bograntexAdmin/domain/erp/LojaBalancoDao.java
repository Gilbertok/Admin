package com.bograntex.bograntexAdmin.domain.erp;

public class LojaBalancoDao {
	
	private String id;
	private String cnpj;
    private String ean13;
    private String mes;
    private String ano;
    private Integer qtdeEstoque;
    
    public LojaBalancoDao() {}
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getEan13() {
		return ean13;
	}
	public void setEan13(String ean13) {
		this.ean13 = ean13;
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
	public void setQtdeEstoque(String qtdeEstoque) {
		if (qtdeEstoque.isEmpty() || qtdeEstoque == null) {
			qtdeEstoque = "0";
		}
		this.qtdeEstoque = Integer.parseInt(qtdeEstoque);
	}
}
