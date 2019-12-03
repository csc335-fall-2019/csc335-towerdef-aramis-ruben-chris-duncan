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
	private static byte[] salt;
	private static MessageDigest md;
	public User(String userName, String password) throws IOException, NoSuchAlgorithmException {
		user = userName;
		md = MessageDigest.getInstance("SHA-512");
		file = new File("hash.txt");
		salt = new byte[16];
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
		pass = generateSHA512Password(password);
	}
	
	public String getPassword() {
		return pass;
	}
	
	public boolean checkPassword(String check) throws FileNotFoundException {
		System.out.println(generateSHA512Password(check)+"\n"+pass);
		System.out.println(generateSHA512Password(check).equals(pass));
		if(generateSHA512Password(check).equals(pass)) {
			return true;
		}
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
	
	private String generateSHA512Password(String pass) {
		md.reset();
        byte[] bytes = md.digest(pass.getBytes());
        String sb = "";
        for(int i=0; i< bytes.length ;i++)
        {
            sb+= Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1);
        }
        return sb;
	}
}
