package com.example.projectupinipin;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegister;
    private ProgressBar progressBar;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initializing the views
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        progressBar = findViewById(R.id.progressBar);

        // Initialize the DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Login button listener
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    if (validateLogin(email, password)) {
                        Toast.makeText(LoginActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                        // Navigate to another activity after successful login
                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Email atau Password salah", Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        // Register text click listener
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to RegisterActivity
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    // Method to validate login credentials
    private boolean validateLogin(String email, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_EMAIL, DatabaseHelper.COLUMN_PASSWORD};
        String selection = DatabaseHelper.COLUMN_EMAIL + " = ? AND " + DatabaseHelper.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true; // User found, credentials are correct
        } else {
            return false; // Invalid credentials
        }
    }
}
