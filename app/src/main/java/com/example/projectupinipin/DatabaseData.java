package com.example.projectupinipin;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseData extends SQLiteOpenHelper {

    // Menggunakan nama file database yang diinginkan, yaitu DatabaseData.db
    public static final String DATABASE_NAME = "DatabaseData.db";  // Nama database adalah DatabaseData.db
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "biodata";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TGL_LAHIR = "tgl_lahir";
    public static final String COLUMN_KABUPATEN = "kabupaten";
    public static final String COLUMN_AGAMA = "agama";
    public static final String COLUMN_JENIS_KELAMIN = "jenis_kelamin";
    public static final String COLUMN_SKILL = "skill";

    public DatabaseData(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  // Nama file database tetap "DatabaseData.db"
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Membuat tabel jika belum ada
        String createTable = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_TGL_LAHIR + " TEXT, "
                + COLUMN_KABUPATEN + " TEXT, "
                + COLUMN_AGAMA + " TEXT, "
                + COLUMN_JENIS_KELAMIN + " TEXT, "
                + COLUMN_SKILL + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Jika versi database berubah, hapus tabel yang ada dan buat tabel baru
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
