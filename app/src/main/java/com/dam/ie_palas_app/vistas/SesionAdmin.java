package com.dam.ie_palas_app.vistas;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dam.ie_palas_app.R;
import com.dam.ie_palas_app.dao.BasedeDatos;

public class SesionAdmin extends AppCompatActivity {

    private EditText etUsuario;
    private EditText etContraseña;
    private Button btnIniciarSesion;
    private TextView tvOlvidasteContraseña;

    // Variable para acceder a la base de datos
    private BasedeDatos basedeDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sesion_admin);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar la base de datos
        basedeDatos = new BasedeDatos(this);

        etUsuario = findViewById(R.id.txtUsuario);
        etContraseña = findViewById(R.id.txtContraseña);
        btnIniciarSesion = findViewById(R.id.btnInicioSesionAdmin);
        tvOlvidasteContraseña = findViewById(R.id.txtolvidecontraseña);

        btnIniciarSesion.setOnClickListener(v -> {
            String usuario = etUsuario.getText().toString();
            String contraseña = etContraseña.getText().toString();

            if (usuario.isEmpty() || contraseña.isEmpty()) {
                Toast.makeText(SesionAdmin.this, "Por favor, ingresa usuario y contraseña.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validar inicio de sesión con la base de datos
            validarInicioSesionAdmin(usuario, contraseña);
        });

        tvOlvidasteContraseña.setOnClickListener(v -> {
            Intent intent = new Intent(SesionAdmin.this, CambiarContrasenaActivity.class);
            intent.putExtra("isAdmin", false);
            startActivity(intent);
        });
    }

    public void validarInicioSesionAdmin(String usuario, String contraseña) {
        SQLiteDatabase db = basedeDatos.getReadableDatabase();
        String query = "SELECT IdAdmin FROM " + BasedeDatos.TABLE_ADMINISTRADOR + " WHERE Usuario = ? AND Contraseña = ?";
        Cursor cursor = db.rawQuery(query, new String[]{usuario, contraseña});

        if (cursor.moveToFirst()) {
            int idAdmin = cursor.getInt(0); // Obtener el ID del administrador

            Log.d("Debug", "Administrador ID desde la base de datos: " + idAdmin);

            // Pasar el ID del estudiante a la siguiente actividad
            Intent intent = new Intent(SesionAdmin.this, CrudsEsCuDoPf.class);
            intent.putExtra("usuario", usuario);
            intent.putExtra("adminId", idAdmin); // Pasar el ID del administrador
            startActivity(intent);
            finish(); // Cerrar la actividad actual
        } else {
            Toast.makeText(SesionAdmin.this, "Usuario o contraseña incorrectos.", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();
    }


    public void RegresarPaginaAnterior(View view) {
        finish();  // Cierra la actividad actual y regresa a la anterior.
    }
}