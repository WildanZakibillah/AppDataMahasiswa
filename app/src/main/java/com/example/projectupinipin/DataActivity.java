package com.example.projectupinipin;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DataActivity extends AppCompatActivity {

    LinearLayout llData;
    DatabaseData dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        // Inisialisasi tampilan
        llData = findViewById(R.id.ll_data);

        // Inisialisasi Database
        dbHelper = new DatabaseData(this);

        // Menampilkan data
        displayData();

        // Tombol Back
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kembali ke halaman DashboardActivity
                Intent intent = new Intent(DataActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();  // Menutup DataActivity agar tidak ada di stack back
            }
        });
    }

    private void displayData() {
        // Membaca seluruh data dari database
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("SELECT * FROM " + DatabaseData.TABLE_NAME, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Ambil data dari setiap kolom
                String nama = cursor.getString(cursor.getColumnIndex(DatabaseData.COLUMN_NAME));
                String tglLahir = cursor.getString(cursor.getColumnIndex(DatabaseData.COLUMN_TGL_LAHIR));
                String kabupaten = cursor.getString(cursor.getColumnIndex(DatabaseData.COLUMN_KABUPATEN));
                String agama = cursor.getString(cursor.getColumnIndex(DatabaseData.COLUMN_AGAMA));
                String jenisKelamin = cursor.getString(cursor.getColumnIndex(DatabaseData.COLUMN_JENIS_KELAMIN));
                String skill = cursor.getString(cursor.getColumnIndex(DatabaseData.COLUMN_SKILL));

                // Inflate layout untuk setiap data
                View dataView = LayoutInflater.from(this).inflate(R.layout.item_biodata, llData, false);

                // Menampilkan data pada TextView
                TextView tvNama = dataView.findViewById(R.id.tv_nama);
                TextView tvTglLahir = dataView.findViewById(R.id.tv_tgl_lahir);
                TextView tvKabupaten = dataView.findViewById(R.id.tv_kabupaten);
                TextView tvAgama = dataView.findViewById(R.id.tv_agama);
                TextView tvJenisKelamin = dataView.findViewById(R.id.tv_jenis_kelamin);
                TextView tvSkill = dataView.findViewById(R.id.tv_skill);

                tvNama.setText("Nama: " + nama);
                tvTglLahir.setText("Tanggal Lahir: " + tglLahir);
                tvKabupaten.setText("Jurusan: " + kabupaten);
                tvAgama.setText("NPM: " + agama);
                tvJenisKelamin.setText("Jenis Kelamin: " + jenisKelamin);
                tvSkill.setText("Skill: " + skill);

                // Tambahkan tampilan data ke LinearLayout
                llData.addView(dataView);

            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
    }
}
