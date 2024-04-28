package com.gero.newpass.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.gero.newpass.R;
import com.gero.newpass.encryption.EncryptionHelper;
import com.gero.newpass.utilities.StringHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

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
     * Adds a new entry with the given name, email, and password to the database.
     *
     * @param name      The name of the entry.
     * @param email     The email of the entry.
     * @param password  The password of the entry (it will be encrypted before being inserted into the database)
     */
    public void addEntry(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase(KEY_ENCRYPTION);
        ContentValues cv = new ContentValues();

        String encryptedPassword = EncryptionHelper.encrypt(password);

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_PASSWORD, encryptedPassword);

        db.insert(TABLE_NAME, null, cv);
    }

    public Cursor readAllData() {
        SQLiteDatabase db = this.getReadableDatabase(KEY_ENCRYPTION);
        String query = "SELECT * FROM " + TABLE_NAME;

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
    public void updateData(String row_id, String name, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase(KEY_ENCRYPTION);
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_PASSWORD, password);

        db.update(TABLE_NAME, cv, "id=?", new String[]{row_id});
    }


    public void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase(KEY_ENCRYPTION);
        db.delete(TABLE_NAME, "id=?", new String[]{row_id});
    }

    public boolean checkIfAccountAlreadyExist(String name, String email) {
        SQLiteDatabase db = this.getReadableDatabase(KEY_ENCRYPTION);

        String selection = COLUMN_NAME + " = ? AND " + COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {name, email};

        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);

        boolean result = cursor != null && cursor.moveToFirst();

        if (cursor != null) {
            cursor.close();
        }

        return result;
    }

    public static void changeDBPassword(String newPassword, Context context) {

        SQLiteDatabase.loadLibs(context);
        //Log.i("Database123", "loaded libs");


        String databasePath = context.getDatabasePath(DATABASE_NAME).getAbsolutePath();
        //Log.i("Database123", "got the db path: " + databasePath);


        SQLiteDatabase db = SQLiteDatabase.openDatabase(databasePath, KEY_ENCRYPTION, null, SQLiteDatabase.OPEN_READWRITE);
        //Log.i("Database123", "db opened with the old key");


        db.rawExecSQL("PRAGMA rekey = '" + newPassword + "'");
        //Log.i("Database123", "new key set");


        db.close();


        Toast.makeText(context, "Database password changed successfully!", Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static void exportDB(Context context) {
        try {
            // Get the path of the database
            String databasePath = context.getDatabasePath(DATABASE_NAME).getAbsolutePath();

            // Create a file output stream using MediaStore
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "backup.db");
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/x-sqlite3");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS);

            Uri uri = context.getContentResolver().insert(MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY), contentValues);


            OutputStream outputStream = context.getContentResolver().openOutputStream(uri);

            // Backup the database
            FileInputStream fis = new FileInputStream(databasePath);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            fis.close();

            Toast.makeText(context, "Database exported successfully!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("430675", "Error exporting database", e);
            Toast.makeText(context, "Error while exporting the database", Toast.LENGTH_SHORT).show();
        }
    }


}
