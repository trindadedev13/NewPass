package com.gero.newpass.encryption;

import android.util.Base64;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class HashUtils {

    public static final int ITERATIONS = 10000;
    private static final int SALT_LENGTH = 16;   // in bytes (at least 16 bytes)
    public static final int HASH_LENGTH = 256; // in bits


    public static String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {

        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, HASH_LENGTH);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = keyFactory.generateSecret(spec).getEncoded();

        String saltBase64 = Base64.encodeToString(salt, Base64.NO_WRAP);
        String hashBase64 = Base64.encodeToString(hash, Base64.NO_WRAP);

        return saltBase64 + ":" + hashBase64;
    }

    public static byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);

        return salt;
    }

    public static boolean verifyPassword(String password, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {

        String[] parts = storedPassword.split(":");

        if (parts.length != 2) {
            throw new IllegalArgumentException("Stored password must have the format 'salt:hash'");
        }

        String saltBase64 = parts[0];
        String hashBase64 = parts[1];

        byte[] salt = Base64.decode(saltBase64, Base64.NO_WRAP);
        byte[] hash = Base64.decode(hashBase64, Base64.NO_WRAP);

        /*
        The password provided by the user during the login attempt is hashed using the same algorithm,
        the same number of iterations and the same salt retrieved from the database.
         */
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, hash.length * 8);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] testHash = keyFactory.generateSecret(spec).getEncoded();

        return java.util.Arrays.equals(hash, testHash);
    }
}
