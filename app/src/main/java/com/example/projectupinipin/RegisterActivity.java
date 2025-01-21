package com.example.projectupinipin;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName, etRegisterEmail, etRegisterPassword;
    private Button btnRegister;
    private TextView tvLogin;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initializing the views
        etName = findViewById(R.id.etName);
        etRegisterEmail = findViewById(R.id.etRegisterEmail);
        etRegisterPassword = findViewById(R.id.etRegisterPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);

        // Initialize the DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Register button listener
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String email = etRegisterEmail.getText().toString();
                String password = etRegisterPassword.getText().toString();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show();
                } else {
                    if (registerUser(name, email, password)) {
                        Toast.makeText(RegisterActivity.this, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show();
                        // Navigate to login activity
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Pendaftaran gagal", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Login text click listener
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private boolean registerUser(String name, String email, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_EMAIL, email);
        values.put(DatabaseHelper.COLUMN_PASSWORD, password);

        long result = db.insert(DatabaseHelper.TABLE_USERS, null, values);
        return result != -1;  // if insert is successful, result will not be -1
    }
}
