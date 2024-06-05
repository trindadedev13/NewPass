package com.gero.newpass.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteException;
import net.sqlcipher.database.SQLiteOpenHelper;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.gero.newpass.R;
import com.gero.newpass.encryption.EncryptionHelper;
import com.gero.newpass.utilities.StringHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Calendar;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Password.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "my_password_record";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "record_name";
    private static final String COLUMN_EMAIL = "record_email";
    private static final String COLUMN_PASSWORD = "record_password";
    private static final String KEY_ENCRYPTION = StringHelper.getSharedString();

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        assert context != null;
        SQLiteDatabase.loadLibs(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_EMAIL + " TEXT, " +
                        COLUMN_PASSWORD + " TEXT);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }



    /**
     * Encrypts the password and adds a new entry with the given name, email, and (encrypted) password to the database.
     *
     * @param context  The context to get the database of the application
     * @param name     The name of the entry.
     * @param email    The email of the entry.
     * @param password The password of the entry (it will be encrypted before being inserted into the database)
     */
    public static void addEntry(Context context, String name, String email, String password) {
        //SQLiteDatabase db = this.getWritableDatabase(KEY_ENCRYPTION);
        SQLiteDatabase db = SQLiteDatabase.openDatabase(context.getDatabasePath(DATABASE_NAME).getAbsolutePath(), KEY_ENCRYPTION, null, SQLiteDatabase.OPEN_READWRITE);

        ContentValues cv = new ContentValues();

        String encryptedPassword = EncryptionHelper.encrypt(password);

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_PASSWORD, encryptedPassword);

        db.insert(TABLE_NAME, null, cv);
    }



    /**
     * Reads all data from the database table.
     *
     * @return A Cursor object containing all the data from the database table.
     * @throws SQLiteException If there's an error accessing the database.
     */
    public Cursor readAllData() {
        SQLiteDatabase db = this.getReadableDatabase(KEY_ENCRYPTION);
        String query = "SELECT * FROM " + TABLE_NAME;

        return db.rawQuery(query, null);
    }



    /**
     * Searches for items in the database based on the provided search query.
     *
     * @param itemToSearch The search query used to find matching items in the database.
     * @return A Cursor object containing the results of the search.
     * @throws SQLiteException If there's an error accessing the database.
     */
    public Cursor searchItem(String itemToSearch) {
        SQLiteDatabase db = this.getReadableDatabase(KEY_ENCRYPTION);

        String query = "SELECT * " +
                "FROM " + TABLE_NAME +
                " WHERE " + COLUMN_NAME + " LIKE '%" + itemToSearch.toLowerCase() + "%'";

        return db.rawQuery(query, null);
    }


    /**
     * Updates an existing row in the database table with the specified row ID.
     *
     * @param row_id   The ID of the row to be updated.
     * @param name     The new value for the name column.
     * @param email    The new value for the email column.
     * @param password The new value for the password column.
     */
    public void updateData(String row_id, String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase(KEY_ENCRYPTION);
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_PASSWORD, password);

        db.update(TABLE_NAME, cv, "id=?", new String[]{row_id});
    }



    /**
     * Deletes a row from the database with the specified row ID.
     *
     * @param rowId The ID of the row to be deleted.
     * @throws SQLiteException If there's an error accessing or updating the database.
     */
    public void deleteOneRow(String rowId) {
        SQLiteDatabase db = this.getWritableDatabase(KEY_ENCRYPTION);
        db.delete(TABLE_NAME, "id=?", new String[]{rowId});
    }



    /**
     * Checks if an account with the given name and email already exists in the database.
     *
     * @param name  The name of the account.
     * @param email The email of the account.
     * @return True if an account with the given name and email exists; otherwise, false.
     * @throws SQLiteException If there's an error accessing the database.
     */
    public static boolean checkIfAccountAlreadyExist(Context context, String name, String email) {
        //SQLiteDatabase db = context.getReadableDatabase(KEY_ENCRYPTION);
        SQLiteDatabase db = SQLiteDatabase.openDatabase(context.getDatabasePath(DATABASE_NAME).getAbsolutePath(), KEY_ENCRYPTION, null, SQLiteDatabase.OPEN_READWRITE);

        String selection = COLUMN_NAME + " = ? AND " + COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {name, email};

        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);

        boolean result = cursor != null && cursor.moveToFirst();

        if (cursor != null) {
            cursor.close();
        }

        return result;
    }



    /**
     * Changes the password used to encrypt the database.
     *
     * @param newPassword The new password for the database.
     * @param context     The application context.
     * @throws SQLiteException If there's an error accessing or updating the database.
     */
    public static void changeDBPassword(String newPassword, Context context) {
        SQLiteDatabase.loadLibs(context);
        String databasePath = context.getDatabasePath(DATABASE_NAME).getAbsolutePath();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(databasePath, KEY_ENCRYPTION, null, SQLiteDatabase.OPEN_READWRITE);
        db.rawExecSQL("PRAGMA rekey = '" + newPassword + "'");
        db.close();
        Toast.makeText(context, R.string.database_password_changed_successfully, Toast.LENGTH_SHORT).show();
    }



    @SuppressLint("Range")
    public static void exportDatabaseToJson(Context context, String passwordGotFromUser) {

        SQLiteDatabase db = SQLiteDatabase.openDatabase(context.getDatabasePath(DATABASE_NAME).getAbsolutePath(), KEY_ENCRYPTION, null, SQLiteDatabase.OPEN_READWRITE);

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        JSONArray jsonArray = new JSONArray();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(COLUMN_ID, cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                    jsonObject.put(COLUMN_NAME, cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                    jsonObject.put(COLUMN_EMAIL, cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                    jsonObject.put(COLUMN_PASSWORD, EncryptionHelper.decrypt(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD))));

                    jsonArray.put(jsonObject);
                } catch (JSONException e) {
                    Log.e("8953467", "Error converting database row to JSON", e);
                }
            } while (cursor.moveToNext());

            cursor.close();
        }

        try {
            File exportDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }
            Calendar calendar = Calendar.getInstance();

            File file = new File(exportDir, "Encrypted_NewPass_DB_" +
                    calendar.get(Calendar.YEAR) +
                    "_" + (calendar.get(Calendar.MONTH) + 1) +
                    "_" + calendar.get(Calendar.DAY_OF_MONTH) + ".json"
            );

            if (file.exists()) {
                Log.d("8953467", "file already exists");
            } else {
                Log.d("8953467", "file not exists");
            }

            String jsonString = jsonArray.toString();
            String jsonEncryptedString = EncryptionHelper.encryptDatabase(jsonString, passwordGotFromUser);

            FileWriter fileWriter = new FileWriter(file);

            fileWriter.write(jsonEncryptedString);
            fileWriter.flush();
            fileWriter.close();

            Log.d("8953467", "Database exported to JSON successfully");
            Toast.makeText(context, context.getString(R.string.database_successfully_exported_to) + " " + Environment.DIRECTORY_DOWNLOADS, Toast.LENGTH_LONG).show();


        } catch (IOException e) {
            Log.e("8953467", "Error: ", e);
            Toast.makeText(context, R.string.export_failed, Toast.LENGTH_LONG).show();

        } finally {
            db.close();
        }
    }


    public static void importJsonToDatabase(Context context, Uri fileUri, String passwordGotFromUser) throws NoSuchAlgorithmException, InvalidKeySpecException {

        String jsonEncryptedString = readJsonFromFile(context, fileUri);
        String jsonDecryptedString = EncryptionHelper.decryptDatabase(context, jsonEncryptedString, passwordGotFromUser);

        if (jsonDecryptedString == null) {
            Log.e("8953467", "Error reading JSON file");
            return;
        }

        try {
            JSONArray jsonArray = new JSONArray(jsonDecryptedString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String name = jsonObject.getString(COLUMN_NAME);
                String email = jsonObject.getString(COLUMN_EMAIL);
                String password = jsonObject.getString(COLUMN_PASSWORD);

                if (!checkIfAccountAlreadyExist(context, name, email)) {
                    addEntry(context, name, email, password);
                } else {
                    Log.w("8953467", "entry: " + name + " " + email + " already exists");
                }
            }

            Log.d("8953467", "Data imported from JSON to database successfully");
            Toast.makeText(context, R.string.database_imported_successfully, Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            Toast.makeText(context, R.string.error_importing_database, Toast.LENGTH_LONG).show();
            Log.e("8953467", "Error parsing JSON", e);
        }
    }

    private static String readJsonFromFile(Context context, Uri fileUri) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(fileUri);
            if (inputStream != null) {
                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                inputStream.close();
                return stringBuilder.toString();
            }
        } catch (IOException e) {
            Toast.makeText(context, R.string.error_importing_database, Toast.LENGTH_LONG).show();
            Log.e("8953467", "Error reading JSON file", e);
        }
        return null;
    }

}
