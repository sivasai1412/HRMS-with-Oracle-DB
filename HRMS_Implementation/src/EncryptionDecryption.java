package bin;


import javax.crypto.KeyGenerator; 
import java.security.Key;
import java.security.InvalidKeyException;  
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;  
import javax.crypto.SecretKey;  
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.BadPaddingException;

public class EncryptionDecryption{
	private static final byte[] iv = {22, 33, 11, 44, 55, 99, 66, 77};
	static Key secretKey = new SecretKeySpec(iv, "DES");

	private static Cipher encryptCipher;
	private static Cipher decryptCipher;

	/**
	*This method is used to perform the encryption on the data present in the file.
	*@param data This parameter is used to take the data from the user and encrypt.
	*@return encryptedData The encrypted data will be returned and stored in the database
	*/
	public static String encrypt(String data){
		String encryptedData = null;
		try{
			encryptCipher=Cipher.getInstance("DES");
			encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] cipherText = encryptCipher.doFinal(data.getBytes());
			encryptedData = Base64.getEncoder().encodeToString(cipherText);
		}catch(NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | NumberFormatException e){
			e.printStackTrace();
		}
		return encryptedData;
	}


	/**
	*This method is used to perform the decryption on the data present in the file.
	*@param data This parameter is used to take the data from the file and decrypt.
	*@return decryptedData The decrypted data will be returned and stored in the database
	*/
	public static String decrypt(String data){
		String decryptedData = null;
		try{
	
			decryptCipher=Cipher.getInstance("DES");
			decryptCipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] plainText = decryptCipher.doFinal(Base64.getDecoder().decode(data));
			decryptedData = new String(plainText);
		}catch(NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | NumberFormatException e){
			e.printStackTrace();
		}
		return decryptedData;
	}


}