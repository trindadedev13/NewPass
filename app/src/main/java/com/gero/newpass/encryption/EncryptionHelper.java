package com.gero.newpass.encryption;

import android.content.Context;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;
import android.util.Log;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class EncryptionHelper {

    private static final String TAG = "EncryptionHelper";
    private static EncryptedSharedPreferences encryptedSharedPreferences = null;

    // Use AES-GCM mode for encryption to ensure data integrity.
    private static final String MODE = "AES/GCM/NoPadding";
    // The initialization vector (IV) length for GCM mode.
    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 16;
    // The key alias used to store the AES key in the Android Keystore.
    private static final String KEY_ALIAS = "MyAesKey";

    /**
     * Encrypts the given plaintext using AES-GCM algorithm with the key
     * obtained from the keystore.
     *
     * @param plainText The plaintext string to be encrypted.
     * @return A Base64-encoded string containing the encrypted data,
     * initialization vector (IV), and authentication tag, or null
     * if an error occurs during encryption.
     */
    public static String encrypt(String plainText) {
        try {
            SecretKey secretKey = getOrCreateAESKey();

            if (secretKey == null) {
                return null;
            }

            Cipher cipher = Cipher.getInstance(MODE);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            byte[] iv = cipher.getIV();

            byte[] ivAndEncryptedBytes = new byte[iv.length + encryptedBytes.length];
            System.arraycopy(iv, 0, ivAndEncryptedBytes, 0, iv.length);
            System.arraycopy(encryptedBytes, 0, ivAndEncryptedBytes, iv.length, encryptedBytes.length);

            return Base64.encodeToString(ivAndEncryptedBytes, Base64.DEFAULT);

        } catch (Exception e) {
            Log.e(TAG, "Error during encryption", e);
            return null;
        }
    }

    /**
     * Decrypts an encrypted string using the AES-GCM algorithm with the key
     * obtained from the keystore.
     *
     * @param cipherText The Base64-encoded string containing the encrypted data,
     * @return The decrypted string, or null if an error occurs during the
     * decryption process or if the authentication fails.
     */
    public static String decrypt(String cipherText) {
        try {
            SecretKey secretKey = getOrCreateAESKey();

            if (secretKey == null) {
                return null;
            }

            byte[] ivAndEncryptedBytes = Base64.decode(cipherText, Base64.DEFAULT);

            byte[] iv = Arrays.copyOfRange(ivAndEncryptedBytes, 0, GCM_IV_LENGTH);
            byte[] encryptedBytes = Arrays.copyOfRange(ivAndEncryptedBytes, GCM_IV_LENGTH, ivAndEncryptedBytes.length);

            Cipher cipher = Cipher.getInstance(MODE);
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);

            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);

        } catch (Exception e) {
            Log.e(TAG, "Error during decryption", e);
            return null;
        }
    }

    /**
     * Retrieves or generates an AES key from the keystore.
     *
     * @return The AES key retrieved from the keystore, or a newly generated key if it doesn't exist in the keystore.
     * Returns null if an error occurs during the retrieval or generation process.
     */
    private static SecretKey getOrCreateAESKey() {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);

            if (keyStore.containsAlias(KEY_ALIAS)) {
                KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry) keyStore.getEntry(KEY_ALIAS, null);
                return secretKeyEntry.getSecretKey();
            } else {
                KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
                KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(KEY_ALIAS, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                        .setKeySize(128);
                keyGenerator.init(builder.build());

                return keyGenerator.generateKey();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting or creating AES key", e);
            return null;
        }
    }

    public static synchronized EncryptedSharedPreferences getEncryptedSharedPreferences(Context context) {
        if (encryptedSharedPreferences == null) {
            try {
                MasterKey masterKey = new MasterKey.Builder(context)
                        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                        .build();

                encryptedSharedPreferences = (EncryptedSharedPreferences) EncryptedSharedPreferences.create(
                        context,
                        "loginkey",
                        masterKey,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                );
            } catch (Exception e) {
                Log.e(TAG, "Error during creation of EncryptedSharedPreferences", e);
            }
        }
        return encryptedSharedPreferences;
    }

    public static String encryptDatabase(String jsonString, String keyEncryption) {
        try {
            // Hash the user-provided key to derive a key for encryption
            String hashedPassword = HashUtils.hashPassword(keyEncryption);

            // Split the hashed password to get the salt and hash
            String[] parts = hashedPassword.split(":");
            String saltBase64 = parts[0];
            String hashBase64 = parts[1];

            // Decode the Base64-encoded hash
            byte[] hash = Base64.decode(hashBase64, Base64.NO_WRAP);

            // Use the hash as the AES key
            SecretKey secretKey = new SecretKeySpec(hash, "AES");

            // Generate a random IV (nonce)
            SecureRandom secureRandom = new SecureRandom();
            byte[] iv = new byte[GCM_IV_LENGTH];
            secureRandom.nextBytes(iv);

            // Create cipher instance and initialize it for encryption
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmSpec);

            // Encrypt the JSON string
            byte[] encryptedBytes = cipher.doFinal(jsonString.getBytes(StandardCharsets.UTF_8));

            // Encode the IV and encrypted bytes to Base64 strings
            String ivBase64 = Base64.encodeToString(iv, Base64.NO_WRAP);
            String encryptedString = Base64.encodeToString(encryptedBytes, Base64.NO_WRAP);

            // Combine IV, salt, and encrypted string for storage
            return ivBase64 + ":" + saltBase64 + ":" + encryptedString;
        } catch (Exception e) {
            Log.e("8953467", "error during encryption " , e);
        }
        return null; // return null if encryption fails
    }

    public static String decryptDatabase(String encryptedData, String keyEncryption) {
        try {
            // Split the input to get IV, salt, and encrypted data
            String[] parts = encryptedData.split(":");
            String ivBase64 = parts[0];
            String saltBase64 = parts[1];
            String encryptedString = parts[2];

            // Decode the Base64-encoded IV, salt, and encrypted data
            byte[] iv = Base64.decode(ivBase64, Base64.NO_WRAP);
            byte[] salt = Base64.decode(saltBase64, Base64.NO_WRAP);
            byte[] encryptedBytes = Base64.decode(encryptedString, Base64.NO_WRAP);

            // Derive the key from the user-provided key and salt
            PBEKeySpec spec = new PBEKeySpec(keyEncryption.toCharArray(), salt, HashUtils.ITERATIONS, HashUtils.HASH_LENGTH);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] keyBytes = keyFactory.generateSecret(spec).getEncoded();
            SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

            // Create cipher instance and initialize it for decryption
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmSpec);

            // Decrypt the encrypted data
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            // Convert decrypted bytes to string
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            Log.e("8953467", "error during decryption " , e);
        }
        return null; // return null if decryption fails
    }
}

