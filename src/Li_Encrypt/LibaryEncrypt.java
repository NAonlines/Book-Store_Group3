package Li_Encrypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class LibaryEncrypt {
    private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128;
    private static final int IV_LENGTH_BYTE = 12;
    private static final int AES_KEY_BIT = 256;
    
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(AES_KEY_BIT);
        return keyGen.generateKey();
    }

    public static byte[] encrypt(String plaintext, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);

        byte[] iv = new byte[IV_LENGTH_BYTE];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_LENGTH_BIT, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);

        byte[] cipherText = cipher.doFinal(plaintext.getBytes());

        byte[] encryptedIvAndText = new byte[IV_LENGTH_BYTE + cipherText.length];
        System.arraycopy(iv, 0, encryptedIvAndText, 0, IV_LENGTH_BYTE);
        System.arraycopy(cipherText, 0, encryptedIvAndText, IV_LENGTH_BYTE, cipherText.length);
        
        return encryptedIvAndText;
    }

    public static String decrypt(byte[] encryptedIvTextBytes, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);

        byte[] iv = new byte[IV_LENGTH_BYTE];
        System.arraycopy(encryptedIvTextBytes, 0, iv, 0, iv.length);

        GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_LENGTH_BIT, iv);
        cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);

        byte[] cipherText = new byte[encryptedIvTextBytes.length - IV_LENGTH_BYTE];
        System.arraycopy(encryptedIvTextBytes, IV_LENGTH_BYTE, cipherText, 0, cipherText.length);

        byte[] decryptedText = cipher.doFinal(cipherText);

        return new String(decryptedText);
    }
}
