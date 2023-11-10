package bin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DES{

	/**
	*This method is used to perform the encryption and decryption operations using the DES algorithm.
	*@param key This is the 
	*@param cipherMode This parameter is to show the mode, so that we can show that this method will know if it should encrypt or decrypt data.
	*@param input This parameter contains the file that we want to decrypt or encrypt.
	*@param output This parameter will be the output file so this will have the contents of encrypted or decrypted file
	*@exception Exception This exception will be used on the Input error.
	*@exception InvalidKeyException This exception will constructs an InvalidKeyException with no detail message. A detail message is a String that describes this particular exception.
	*@exception NoSuchAlgorithmException This exception is thrown when a particular cryptographic algorithm is requested but is not available in the environment.
	*@exception InvalidKeySpecException This is the exception for invalid key specifications.
	*@exception NoSuchPaddingException This exception is thrown when a particular padding mechanism is requested but is not available in the environment.
	*@see Exception 
	*/
	public static void encryptionDecryption(String key, int cipherMode, File input, File output) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, Exception{
	try{
		FileInputStream fileInput = new FileInputStream(input);
		FileOutputStream fileOutput = new FileOutputStream(output);
		DESKeySpec deskeySpec = new DESKeySpec(key.getBytes());

		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = secretKeyFactory.generateSecret(deskeySpec);

		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

		byte[] ivBytes = new byte[8];
		IvParameterSpec iv = new IvParameterSpec(ivBytes);		
	
		if(cipherMode == Cipher.ENCRYPT_MODE){
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, SecureRandom.getInstance("SHA1PRNG"));
			CipherInputStream cipherInput = new CipherInputStream(fileInput, cipher);
			writing(cipherInput, fileOutput);
		}
		else if(cipherMode == Cipher.DECRYPT_MODE){
			cipher.init(Cipher.DECRYPT_MODE, secretKey, SecureRandom.getInstance("SHA1PRNG"));
			CipherOutputStream cipherOutput = new CipherOutputStream(fileOutput, cipher);
			writing(fileInput, cipherOutput);
		}
	}catch(FileNotFoundException e){
		System.out.println("");
	}	
	}

	/**
	*This method is used to write the encrypted or decrypted data into the file.
	*@param input This parameter contains the file that we want to decrypt or encrypt.
	*@param output This parameter will be the output file so this will have the contents of encrypted or decrypted file
	*@exception IOException This exception will be used on the Input error.
	*@see Exception 
	*/	
	private static void writing(InputStream input, OutputStream output) throws IOException{
		byte[] byteInput = new byte[64];
		int numberOfBytesRead;
		while((numberOfBytesRead = input.read(byteInput)) != -1){
			output.write(byteInput, 0, numberOfBytesRead);
		}
		output.close();
		input.close();
	}
	/**
	*This method is used to perform the encryption on the employee file.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static void employeeFileEncryption() throws Exception{
		try{
		String filePath = "../Database/employee_data.xml";
		String filePathOne = "../Encrypted_Database/employees_data.xml";
		File employeeDatabase = new File(filePath);
		
		File encryptedDatabase = new File(filePathOne);

			encryptionDecryption("12345678", Cipher.ENCRYPT_MODE, employeeDatabase, encryptedDatabase);
			//System.out.println("Data is encrypted successfully");
			employeeDatabase.delete();
		}catch(InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | IOException e){
			e.printStackTrace();
		}

	}

	/**
	*This method is used to perform the decryption on the employee file.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static void employeeFileDecryption() throws Exception{
		try{
		String filePath = "../Database/employee_data.xml";
		String filePathOne = "../Encrypted_Database/employees_data.xml";
		File encryptedDatabase = new File(filePathOne);
		File decryptedDatabase = new File(filePath);
		
			encryptionDecryption("12345678", Cipher.DECRYPT_MODE, encryptedDatabase, decryptedDatabase);
			//System.out.println("decrypted successfully");

		}catch(InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | IOException e){
			e.printStackTrace();
		}

	}
	/**
	*This method is used to perform the encryption on the contractor file.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static void contractorFileEncryption() throws Exception{
		try{
		String filePath = "../Database/contractor_data.xml";
		String filePathOne = "../Encrypted_Database/contractors_data.xml";
		File employeeDatabase = new File(filePath);
		
		File encryptedDatabase = new File(filePathOne);
		
			encryptionDecryption("12345678", Cipher.ENCRYPT_MODE, employeeDatabase, encryptedDatabase);
			//System.out.println("Data is encrypted successfully");
			employeeDatabase.delete();
		}catch(InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | IOException e){
			e.printStackTrace();
		}

		
	}

	/**
	*This method is used to perform the decryption on the employee file.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static void contractorFileDecryption() throws Exception{
		try{
		String filePath = "../Database/contractor_data.xml";
		String filePathOne = "../Encrypted_Database/contractors_data.xml";
		File encryptedDatabase = new File(filePathOne);
		File decryptedDatabase = new File(filePath);
		
			encryptionDecryption("12345678", Cipher.DECRYPT_MODE, encryptedDatabase, decryptedDatabase);
			//System.out.println("decrypted successfully");

		}catch(InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | IOException e){
			e.printStackTrace();
		}

		
	}

}