package com.gero.newpass.encryption;

import android.content.Context;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.security.KeyStore;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;


public class EncryptionHelper {

    private static EncryptedSharedPreferences encryptedSharedPreferences = null;
    private static final String MODE = "AES/CBC/PKCS7Padding";
    private static final String KEY_ALIAS = "MyAesKey";

    /**
     * Encrypts the given plaintext using AES encryption with CBC mode.
     *
     * @param plainText The plaintext string to be encrypted.
     * @return A Base64-encoded string containing the encrypted data concatenated with the initialization vector (IV),
     * or null if an error occurs during encryption.
     */
    public static String encrypt(String plainText) {

        try {
            SecretKey secretKey = getOrCreateAESKey();

            if (secretKey == null) {
                return null;
            }

            Cipher cipher = Cipher.getInstance(MODE);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] iv = cipher.getIV();

            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());

            byte[] ivAndEncryptedBytes = new byte[iv.length + encryptedBytes.length];
            System.arraycopy(iv, 0, ivAndEncryptedBytes, 0, iv.length);
            System.arraycopy(encryptedBytes, 0, ivAndEncryptedBytes, iv.length, encryptedBytes.length);

            return Base64.encodeToString(ivAndEncryptedBytes, Base64.DEFAULT);

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Decrypts an encrypted string using the AES algorithm with the key obtained from the keystore.
     *
     * @param cipherText It must be in the form IV + encrypted_text. It will be decrypted with the IV
     * @return The decrypted string, or null if an error occurs during the decryption process.
     */
    public static String decrypt(String cipherText) {
        try {

            SecretKey secretKey = getOrCreateAESKey();

            if (secretKey == null) {

                return null;
            }

            byte[] ivAndEncryptedBytes = Base64.decode(cipherText, Base64.DEFAULT);

            byte[] iv = Arrays.copyOfRange(ivAndEncryptedBytes, 0, 16);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            Cipher cipher = Cipher.getInstance(MODE);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

            byte[] decryptedBytes = cipher.doFinal(ivAndEncryptedBytes);


            return new String(Arrays.copyOfRange(decryptedBytes, 16, decryptedBytes.length));

        } catch (Exception e) {
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
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                        .setKeySize(128);
                keyGenerator.init(builder.build());

                return keyGenerator.generateKey();
            }
        } catch (Exception e) {
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
                e.printStackTrace();
                // Handle exception
            }
        }
        return encryptedSharedPreferences;
    }

}

