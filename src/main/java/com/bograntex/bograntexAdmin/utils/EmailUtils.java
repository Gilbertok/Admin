package com.bograntex.bograntexAdmin.utils;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class EmailUtils {
	
	private String fromEmail;
	private String fromName;
	private String hostName = "mail.bocagrande.com.br";
	private String autenticationEmail = "gilberto.k@bocagrande.com.br";
	private String autenticationSenha = "gilberto.k123$";
	private int smtpPort = 578;
	
	public EmailUtils(String fromEmail, String fromName) {
		this.fromEmail = fromEmail;
		this.fromName = fromName;
	}
	
	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getHost() {
		return hostName;
	}

	public String getAutenticationEmail() {
		return autenticationEmail;
	}

	public String getAutenticationSenha() {
		return autenticationSenha;
	}

	public int getSmtpPort() {
		return smtpPort;
	}

	public void Enviar(String assunto, String mensagem) throws EmailException {
		SimpleEmail mail = new SimpleEmail();
		mail.setSubject(assunto);
		mail.setMsg(mensagem);
		mail.setSSLOnConnect(true);
		mail.setAuthentication(autenticationEmail, autenticationSenha);
		mail.setHostName(hostName);
		mail.setSmtpPort(smtpPort);
		mail.setFrom("gilberto.k@bocagrande.com.br", "BG Admin");
		if(fromName.toUpperCase().equals("INTERSYS")) {
			mail.addTo("gilberto.k@bocagrande.com.br", fromName);
		} else {
			mail.addTo(this.getFromEmail(), fromName);
		}
		mail.send();
	}


}
