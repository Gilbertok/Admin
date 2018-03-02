package com.bograntex.bograntexAdmin.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CriptUtils {
	
	public String encripta(String password) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] mensagem = password.getBytes();

        // Usando chave de 128-bits (16 bytes)
        byte[] chave = "chave de 16bytes".getBytes();
        System.out.println("Tamanho da chave: " + chave.length);

        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(chave, "AES"));
        return cipher.doFinal(mensagem).toString();
	}
	
	public String dencripta(String encrypted) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

		// Usando chave de 128-bits (16 bytes)
        byte[] chave = "chave de 16bytes".getBytes();
        System.out.println("Tamanho da chave: " + chave.length);

        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(chave, "AES"));
        return cipher.doFinal(encrypted.getBytes()).toString();
	}
	
}
