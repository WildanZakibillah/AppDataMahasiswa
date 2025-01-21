package com.example.projectupinipin;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    EditText etNama, etTanggalLahir, etKabupaten, etAgama;
    RadioGroup rgJenisKelamin;
    CheckBox cbWeb, cbMobile, cbDesktop, cbNet;

    DatabaseData dbHelper;  // Menggunakan kelas DatabaseData
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        etNama = findViewById(R.id.et_nama);
        etTanggalLahir = findViewById(R.id.et_tanggal_lahir);
        etKabupaten = findViewById(R.id.et_kabupaten);
        etAgama = findViewById(R.id.et_agama);
        rgJenisKelamin = findViewById(R.id.rg_jenis_kelamin);
        cbWeb = findViewById(R.id.cb_web);
        cbMobile = findViewById(R.id.cb_mobile);
        cbDesktop = findViewById(R.id.cb_desktop);
        cbNet = findViewById(R.id.cb_net);

        dbHelper = new DatabaseData(this);  // Inisialisasi database menggunakan DatabaseData
        db = dbHelper.getWritableDatabase();  // Mengambil akses tulis ke database

        // Tombol "VIEW"
        findViewById(R.id.btn_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ambil data dari form input
                String nama = etNama.getText().toString();
                String tglLahir = etTanggalLahir.getText().toString();
                String kabupaten = etKabupaten.getText().toString();
                String agama = etAgama.getText().toString();

                // Menentukan jenis kelamin
                int selectedGenderId = rgJenisKelamin.getCheckedRadioButtonId();
                RadioButton rbGender = findViewById(selectedGenderId);
                String jenisKelamin = rbGender != null ? rbGender.getText().toString() : "";

                // Menentukan skill yang dipilih
                StringBuilder skillBuilder = new StringBuilder();
                if (cbWeb.isChecked()) skillBuilder.append("Web Programming ");
                if (cbMobile.isChecked()) skillBuilder.append("Mobile Programming ");
                if (cbDesktop.isChecked()) skillBuilder.append("Desktop Programming ");
                if (cbNet.isChecked()) skillBuilder.append("Networking");

                // Simpan data ke database
                ContentValues values = new ContentValues();
                values.put(DatabaseData.COLUMN_NAME, nama);
                values.put(DatabaseData.COLUMN_TGL_LAHIR, tglLahir);
                values.put(DatabaseData.COLUMN_KABUPATEN, kabupaten);
                values.put(DatabaseData.COLUMN_AGAMA, agama);
                values.put(DatabaseData.COLUMN_JENIS_KELAMIN, jenisKelamin);
                values.put(DatabaseData.COLUMN_SKILL, skillBuilder.toString());

                // Insert data ke tabel
                long rowId = db.insert(DatabaseData.TABLE_NAME, null, values);

                if (rowId != -1) {
                    Toast.makeText(DashboardActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DashboardActivity.this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
                }

                // Setelah menyimpan data, pindah ke activity untuk menampilkan data
                Intent intent = new Intent(DashboardActivity.this, DataActivity.class);
                startActivity(intent);
            }
        });

        // Tombol "CLEAR"
        findViewById(R.id.btn_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hapus semua data dari tabel
                int deletedRows = db.delete(DatabaseData.TABLE_NAME, null, null);
                if (deletedRows > 0) {
                    Toast.makeText(DashboardActivity.this, "Semua data berhasil dihapus", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DashboardActivity.this, "Tidak ada data untuk dihapus", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
