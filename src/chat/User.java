package chat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9215472150552409030L;
	private String user;
	private String pass;
	private File file;
	public User(String userName, String password) throws IOException {
		user = userName;
		file = new File("hash.txt");
		byte[] salt = new byte[16];
		if(file.createNewFile()) {
			salt = generateRandomSalt(salt);
		}else {
			Scanner scan = new Scanner(file);
			if(!scan.hasNextLine()) {
				salt = generateRandomSalt(salt);
				FileOutputStream out = new FileOutputStream(file);
				out.write(salt);
				out.close();
			}else {
				String bytes = scan.nextLine();
				salt = bytes.getBytes();
			}
			scan.close();
		}
		pass = generateSHA512Password(password, salt);
	}
	
	public String getPassword() {
		return pass;
	}
	
	public boolean checkPassword(String check) throws FileNotFoundException {
		Scanner scan = new Scanner(file);
		String bytes = scan.nextLine();
		byte[] salt = bytes.getBytes();
		if(generateSHA512Password(check, salt).equals(pass)) {
			scan.close();
			return true;
		}
		scan.close();
		return false;
	}
	
	public String getUsername() {
		return user;
	}
	
	private static byte[] generateRandomSalt(byte[] salt) {
		SecureRandom random = new SecureRandom();
		random.nextBytes(salt);
		return salt;
	}
	
	private static String generateSHA512Password(String pass, byte[] salt) {
		String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] bytes = md.digest(pass.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
        return generatedPassword;
	}
}
