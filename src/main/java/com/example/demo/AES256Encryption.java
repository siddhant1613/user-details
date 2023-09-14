package com.example.demo;

import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AES256Encryption {
	private static final byte[] SALT = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56, (byte) 0x35,
			(byte) 0xE3, (byte) 0x03 };
	private static final int ITERATION_COUNT = 65556;
	private static final int KEY_LENGTH = 256;
	private static Cipher ecipher;
	private static SecretKeyFactory factory;
	static {
		try {
			factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}

	public static String encrypt(String inputValue, String key, String ivParam) {
		try {
			IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(ivParam));
			SecretKeySpec skeySpec = new SecretKeySpec(Base64.getDecoder().decode(key), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(inputValue.getBytes());
			return Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static String decrypt(String encryptedString, String key, String ivParam) {
		try {
			byte[] ivd = Base64.getDecoder().decode(ivParam);
			byte[] sc = Base64.getDecoder().decode(key);
			IvParameterSpec iv = new IvParameterSpec(ivd);
			SecretKeySpec skeySpec = new SecretKeySpec(sc, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedString));
			return new String(original);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static String getEncryptKeyIVParam(String esmeaddr) {
		String encryptKeyIVParam = null;
		String aSecureyKey = null;
		try {
			aSecureyKey = esmeaddr + "$t";
			KeySpec spec = new PBEKeySpec(aSecureyKey.toCharArray(), SALT, ITERATION_COUNT, KEY_LENGTH);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
			ecipher.init(Cipher.ENCRYPT_MODE, secret);
			byte[] iv = ecipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
			String ivBytes = Base64.getEncoder().encodeToString(iv);
			System.out.println("IV bytes " + ivBytes + " encryption key "
					+ Base64.getEncoder().encodeToString(aSecureyKey.getBytes()));
			encryptKeyIVParam = ivBytes + "~" + Base64.getEncoder().encodeToString(aSecureyKey.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptKeyIVParam;
	}

	public static void main(String[] args) throws Exception {
		
		String encrypt = encrypt("8052", "58VTYzd82o1jCWe5omJMYujngmb9QKiAZlS9nEcdsMk=", "9iSwMG4isu4MPoFp/a2nbg==");

		System.out.println("encrypt " + encrypt);
		String decrypt = decrypt(encrypt, "58VTYzd82o1jCWe5omJMYujngmb9QKiAZlS9nEcdsMk=", "9iSwMG4isu4MPoFp/a2nbg==");

		System.out.println("decrypt " + decrypt);
	}
}