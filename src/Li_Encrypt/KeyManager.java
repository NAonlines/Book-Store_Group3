package Li_Encrypt;

import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;

public class KeyManager {

    private static final String KEY_FILE_PATH = "secret.key";

    // Generate a new AES SecretKey
    public static SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        return keyGen.generateKey();
    }

    // Save SecretKey to file
    public static void saveKey(SecretKey key) throws Exception {
        File keyFile = new File(KEY_FILE_PATH);
        try (FileOutputStream fos = new FileOutputStream(keyFile)) {
            fos.write(key.getEncoded());
        }
    }

    // Load SecretKey from file
    public static SecretKey loadKey() throws Exception {
        File keyFile = new File(KEY_FILE_PATH);
        byte[] keyBytes = new byte[(int) keyFile.length()];
        try (FileInputStream fis = new FileInputStream(keyFile)) {
            fis.read(keyBytes);
        }
        return new javax.crypto.spec.SecretKeySpec(keyBytes, "AES");
    }

    // Check if key file exists
    public static boolean isKeyExist() {
        File keyFile = new File(KEY_FILE_PATH);
        return keyFile.exists();
    }
}
